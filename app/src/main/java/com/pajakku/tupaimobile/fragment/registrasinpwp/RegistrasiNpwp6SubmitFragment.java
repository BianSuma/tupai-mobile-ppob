package com.pajakku.tupaimobile.fragment.registrasinpwp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.activity.CameraKitActivity;
import com.pajakku.tupaimobile.activity.CommonWebActivity;
import com.pajakku.tupaimobile.activity.RegistrasiNpwpActivity;
import com.pajakku.tupaimobile.api.ApiReqEreg;
import com.pajakku.tupaimobile.api.ApiReqUpload;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpFile;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.io.Serializable;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RegistrasiNpwp6SubmitFragment extends BaseFragment {

    private AppCompatTextView lblNik;

    private AppCompatTextView valName;
    private AppCompatTextView valNik;
    private AppCompatTextView valEmail;
    private AppCompatTextView valTelp;
    private AppCompatTextView valStatus;

    private AppCompatCheckBox cbCheck;

    private AppCompatButton btnReload;

    private boolean isViewDataLoaded;

    private ActDataRegistrasiNpwp getActData(){
        return ((RegistrasiNpwpActivity)activity).actData;
    }

    public RegistrasiNpwp6SubmitFragment() {
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
        View v = onCreateView(R.layout.fragment_registrasinpwp6_submit, inflater, container, savedInstanceState);

        lblNik = v.findViewById(R.id.registrasinpwp6submit_lbl_nik);

        valName = v.findViewById(R.id.registrasinpwp6submit_val_name);
        valNik = v.findViewById(R.id.registrasinpwp6submit_val_nik);
        valEmail = v.findViewById(R.id.registrasinpwp6submit_val_email);
        valTelp = v.findViewById(R.id.registrasinpwp6submit_val_telp);
        valStatus = v.findViewById(R.id.registrasinpwp6submit_val_status);

        cbCheck = v.findViewById(R.id.registrasinpwp6submit_inp_check);
        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkInput(null);
            }
        });

        setBotBtn(R.id.registrasinpwp6submit_btn_ok);

        v.findViewById(R.id.registrasinpwp6submit_btn_syarat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonWebActivity.startAct(context, AppConstant.ACTRES_COMMON, "ereg_submitwp_syaratketentuan.html");
            }
        });

        btnReload = v.findViewById(R.id.registrasinpwp6submit_btn_reload);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewData();
            }
        });

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

//        ActDataRegistrasiNpwp actData = getActData();

        ((RegistrasiNpwpActivity)activity).setActionbarBtnRightVisible(false);

        setViewData();
    }



    private void setViewData(){
        final ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 reqValidasi1 = actData.savingValidasi1;

        ApiReqEreg.getDataByEmail(activity, new RequestParamConfig(), reqValidasi1.emailWp, new SuccessFailCallback<EregDataByEmail, ResponseDTO>() {
            @Override
            public void onSuccess(EregDataByEmail data) {
                data.setNulled(actData);
                actData.savingEregDataByEmail = data;

                actData.setNulled();

                setViewData(actData.savingEregDataByEmail);
            }

            @Override
            public void onFail(ResponseDTO data) {
                btnReload.setVisibility(View.VISIBLE);
                isViewDataLoaded = false;
            }
        });
    }

    private void setViewData(EregDataByEmail saving){
        valName.setText( saving.wp.namaWpNotNull() );
        valNik.setText( saving.wp.noIDWpNotNull() );
        valEmail.setText( saving.wp.emailWpNotNull() );
        valTelp.setText( saving.wp.nomorHPWpNotNull() );
        valStatus.setText( saving.statusNotNull() );

        btnReload.setVisibility(View.GONE);

        checkInput(null);

        isViewDataLoaded = true;
    }

    @Override
    public void onPause(){
        super.onPause();

        ((RegistrasiNpwpActivity)activity).setActionbarBtnRightVisible(true);
    }

    @Override
    public void save(){
        final BaseActivity context = (BaseActivity) activity;

        Utility.showConfirmationDialog(context, "Kirim data permohonan NPWP?", new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {

                final ActDataRegistrasiNpwp actData = getActData();
                ApiReqEreg.submitWp(activity, new RequestParamConfig(), actData.savingValidasi1.emailWp, new CommonCallback<ResponseDTO>() {
                    @Override
                    public void onSuccess(ResponseDTO data) {
                        suksesRegistrasiNpwp();
                    }
                });

            }
        });
    }

    @Override
    public boolean onSubmitValidate(BaseActivity act, BaseInput bi){
        return isViewDataLoaded && cbCheck.isChecked();
    }

    @Override
    public void onChangeInputComponent(BaseActivity act, BaseInput bi, boolean isValid){
        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;

        lblNik.setText( saving.isWNI(false) ? "NIK" : "No. Paspor");
    }

    private void suksesRegistrasiNpwp(){
        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;

        Utility.toast(activity, "Registrasi NPWP telah selesai. Silahkan cek di email "+saving.emailWp);
        activity.finish();
    }


}
