package com.pajakku.tupaimobile.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiReqEreg;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.component.RecyclerViewX;
import com.pajakku.tupaimobile.model.actdata.ActDataFileBrowser;
import com.pajakku.tupaimobile.model.actdata.ActDataKodeWilayah;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.PageDTO;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;


public class KodeWilayahActivity extends BaseActivity {
    private static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_KODE_WILAYAH;
    public ActDataKodeWilayah actData;

    private InpTextX inpFind;
    private RecyclerViewX recyclerViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list_find);

        initData(savedInstanceState);

        findViewById(R.id.commonlistfind_btn_actionbar_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((AppCompatTextView)findViewById(R.id.commonlistfind_lbl_actionbar_title)).setText("Pilih Wilayah");

        findViewById(R.id.commonlistfind_btn_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubmitFind();
            }
        });

        inpFind = setCompInpTextX(R.id.commonlistfind_lay_find);
        inpFind.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    clickSubmitFind();
                    return true;
                }
                return false;
            }
        });

        recyclerViewX = setRecyclerViewX(R.id.commonlistfind_swiperefreshlayout, new CommonCallback<ClickItemListParam>() {
            @Override
            public void onSuccess(ClickItemListParam data) {
                WilayahDTO wilayah = (WilayahDTO) ((HolderCommon2Val)data.object).object;
                Intent itn = new Intent();
                itn.putExtra(AppConstant.SP_ACTIVITYRESULT, wilayah.toPickedDTO() );
                setResult(RESULT_OK, itn);
                finish();
            }
        }, null);

        findViewById(R.id.commonlistfind_fab).setVisibility(View.GONE);

    }

    @Override
    protected void onResume(){
        super.onResume();

//        setAppRecyclerViewStart();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ACTDATA_KEY, actData);
    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
//            Intent itn = getIntent();
//            ActParamDropdownActivity ap = (ActParamDropdownActivity)itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataKodeWilayah();
//            actData.siList = newList();
        }else{
            actData = (ActDataKodeWilayah)savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    public void fetch(RequestParamConfig rpc) {
        rpc.recyclerViewX = recyclerViewX;
        ApiReqEreg.kodeWilayah(this, rpc, actData.findKey, new CommonCallback<PageDTO<WilayahDTO>>() {
            @Override
            public void onSuccess(PageDTO<WilayahDTO> data) {
                recyclerViewX.setList( WilayahDTO.toHolderCommon2Val(data.data) );
            }
        });
    }

    private void clickSubmitFind(){
        String str = inpFind.getValue(false);
        if(str.isEmpty()) return;
        actData.findKey = str;
        recyclerRefresh();
    }


    public static void startAct(Activity act, int actRes){

        Intent itn = new Intent(act, KodeWilayahActivity.class);
        act.startActivityForResult(itn, actRes);

    }

}
