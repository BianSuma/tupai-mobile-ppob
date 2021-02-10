package com.pajakku.tupaimobile.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.RecyclerViewX;
import com.pajakku.tupaimobile.model.actdata.ActDataFileBrowser;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileBrowserActivity extends BaseActivity {
    private static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_FILE_BROWSER;
    public ActDataFileBrowser actData;

    private RecyclerViewX recyclerViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filebrowser);

        initData(savedInstanceState);

        findViewById(R.id.filebrowser_btn_actionbar_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerViewX = setRecyclerViewX(R.id.filebrowser_swiperefreshlayout, new CommonCallback<ClickItemListParam>() {
            @Override
            public void onSuccess(ClickItemListParam data) {
                FileBrowserItem si = (FileBrowserItem) ((HolderCommon1Val)data.object).object;
                File file = new File(si.path);
                if(file.isDirectory()){
                    File[] subs = file.listFiles();
                    if(subs != null){
                        if( isExistFileNonDot(subs) ) {
                            actData.siList.clear();
                            for (File sub : subs) {
                                if(sub.getName().startsWith(".")) continue;
                                si = new FileBrowserItem();
                                si.path = sub.getAbsolutePath();
                                si.isDir = sub.isDirectory();
                                si.name = sub.getName();
                                actData.siList.add(si);
                            }
                            actData.dirDeep++;
                            recyclerRefresh();
                        }else{
                            Utility.toast(FileBrowserActivity.this, "Folder kosong.");
                        }
                    }else{
                        Utility.toast(FileBrowserActivity.this, "Folder tidak bisa dibuka.");
                    }

                }else{
                    Utility.log("path "+si.path);

                    Intent itn = new Intent();
                    itn.putExtra(AppConstant.SP_ACTIVITYRESULT, si);
                    setResult(Activity.RESULT_OK, itn);
                    finish();
                }
            }
        }, null);

    }

    @Override
    protected void onResume(){
        super.onResume();

        setAppRecyclerViewStart();
    }


    @Override
    public void onBackPressed() {
        if(actData.dirDeep > 0){
            if(actData.siList.size() <= 0){
                super.onBackPressed();
                return;
            }
            if( --actData.dirDeep > 0 ){
                File file = new File(actData.siList.get(0).path);
                file = file.getParentFile();
                if(file == null){
                    super.onBackPressed();
                    return;
                }
                file = file.getParentFile();
                if(file == null){
                    super.onBackPressed();
                    return;
                }
                File[] subs = file.listFiles();
                if(subs != null) {
                    FileBrowserItem si;
                    actData.siList.clear();
                    for (File sub : subs) {
                        if(sub.getName().startsWith(".")) continue;
                        si = new FileBrowserItem();
                        si.path = sub.getAbsolutePath();
                        si.isDir = sub.isDirectory();
                        si.name = sub.getName();
                        actData.siList.add(si);
                    }
                }else{
                    super.onBackPressed();
                    return;
                }
            }else{
                actData.siList = newList();
            }
            recyclerRefresh();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ACTDATA_KEY, actData);
    }

    private boolean isExistFileNonDot(File[] files){
        for(File f : files){
            if(f.getName().startsWith(".")) continue;
            return true;
        }
        return false;
    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
//            Intent itn = getIntent();
//            ActParamDropdownActivity ap = (ActParamDropdownActivity)itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataFileBrowser();
            actData.siList = newList();
        }else{
            actData = (ActDataFileBrowser)savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    public void fetch(RequestParamConfig rpc) {
        recyclerViewX.setList( FileBrowserItem.toHolderCommon1Val(actData.siList) );
    }

    private List<FileBrowserItem> newList(){
        FileBrowserItem si;
        List<FileBrowserItem> l = new ArrayList<>();

        si = new FileBrowserItem();
        si.path = Environment.getExternalStorageDirectory().getAbsolutePath();
        si.name = "File Internal";
        si.isDir = true;
        l.add(si);

        File file;
        for(String dir : Utility.getExtDirPaths()){
            file = new File(dir);
            si = new FileBrowserItem();
            si.path = dir;
            si.name = file.getName();
            si.isDir = true;
            l.add(si);
        }
        return l;
    }

    public static void startAct(Activity act, int actRes){

        if( ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ){
            Intent itn = new Intent(act, FileBrowserActivity.class);
            act.startActivityForResult(itn, actRes);
        }else{
            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.ACTRES_IGNORE);
        }

    }
}
