package com.pajakku.tupaimobile.fragment.registrasinpwp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.activity.CameraKitActivity;
import com.pajakku.tupaimobile.activity.RegistrasiNpwpActivity;
import com.pajakku.tupaimobile.api.ApiReqUpload;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpFile;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.io.Serializable;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RegistrasiNpwp3WajahFragment extends BaseFragment {
    private static final int ACTRES_CAMERA = AppConstant.ACTRES_COMMON;

    private InpFile inpFile;

    private ActDataRegistrasiNpwp getActData(){
        return ((RegistrasiNpwpActivity)activity).actData;
    }

    public RegistrasiNpwp3WajahFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity context = activity;
        final ActDataRegistrasiNpwp actData = getActData();
        View v = onCreateView(R.layout.fragment_registrasinpwp3_wajah, inflater, container, savedInstanceState);

        inpFile = setCompInpFile(R.id.registrasinpwp3wajah_inp_foto, AppConstant.ACTRES_FILE_BROWSER, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                saveObj();
            }
        }, new CommonCallback<FileBrowserItem>() {
            @Override
            public void onSuccess(FileBrowserItem data) {
                actData.savingVideoPath = data.path;
            }
        });
        inpFile.constraintMandatory = true;
        inpFile.setWarn(R.id.registrasinpwp3wajah_lblwarn_foto);
        inpFile.setThumbnail(R.id.registrasinpwp3wajah_img_preview);

        v.findViewById(R.id.registrasinpwp3wajah_btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraKitActivity.startAct(context, ACTRES_CAMERA);
            }
        });

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        ActDataRegistrasiNpwp actData = getActData();

        inpFile.setValueUnchange(actData.savingVideoPath);

        checkInput(null);
    }

    private void saveObj(){
        ActDataRegistrasiNpwp actData = getActData();

        actData.savingVideoPath = inpFile.getValuePath(false);
    }

    @Override
    public void save(){
        final BaseActivity context = (BaseActivity) activity;
        saveObj();

        final ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 reqValidasi1 = actData.savingValidasi1;

        ApiReqUpload.eregValidasi2(context, new RequestParamConfig(), actData.savingVideoPath, reqValidasi1.emailWp, new CommonCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {
                        context.nextFrag();
            }
            });

    }

    @Override
    protected void onActResultFragChild(Activity act, int reqCode, Serializable data){
        switch (reqCode){
            case ACTRES_CAMERA:
                ActDataRegistrasiNpwp actData = getActData();
                actData.savingVideoPath = ((HolderCommon1Val)data).str;
                inpFile.setValueUnchange( actData.savingVideoPath );
                checkInput(inpFile);
                break;
        }
    }


}
