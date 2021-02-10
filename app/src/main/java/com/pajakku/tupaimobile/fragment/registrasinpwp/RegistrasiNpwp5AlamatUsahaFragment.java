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
import com.pajakku.tupaimobile.activity.KodeWilayahActivity;
import com.pajakku.tupaimobile.activity.RegistrasiNpwpActivity;
import com.pajakku.tupaimobile.api.ApiReqEreg;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpPicker;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RegistrasiNpwp5AlamatUsahaFragment extends BaseFragment {

    private InpTextX inpJalan;
    private InpTextX inpRt;
    private InpTextX inpRw;
    private InpPicker inpWilayah;
    private InpTextX inpTelp;

    private ActDataRegistrasiNpwp getActData(){
        return ((RegistrasiNpwpActivity)activity).actData;
    }

    public RegistrasiNpwp5AlamatUsahaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity context = activity;
        final ActDataRegistrasiNpwp actData = getActData();
        final ReqValidasi1 saving = actData.savingValidasi1;
        View v = onCreateView(R.layout.fragment_registrasinpwp5_alamatusaha, inflater, container, savedInstanceState);
//
        inpJalan = setCompInpTextX(R.id.registrasinpwp5alamatusaha_inp_jalan);
        inpJalan.constraintMandatory = true;
        inpJalan.setWarn(R.id.registrasinpwp5alamatusaha_lblwarn_jalan);

        inpRt = setCompInpTextX(R.id.registrasinpwp5alamatusaha_inp_rt);
        inpRt.setWarn(R.id.registrasinpwp5alamatusaha_lblwarn_rt);

        inpRw = setCompInpTextX(R.id.registrasinpwp5alamatusaha_inp_rw);
        inpRw.setWarn(R.id.registrasinpwp5alamatusaha_lblwarn_rw);

        inpWilayah = setCompInpPicker(R.id.registrasinpwp5alamatusaha_inp_wilayah, AppConstant.ACTRES_COMMON, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                KodeWilayahActivity.startAct(context, ((InpPicker)data).actRes);
            }
        }, new CommonCallback<PickedDTO>() {
            @Override
            public void onSuccess(PickedDTO data) {
                actData.tmpEregDataByEmailUsahaAlmUsahaKdWilayah = ((WilayahDTO)data.object);
                saving.almKTPKdWilayah = actData.tmpEregDataByEmailUsahaAlmUsahaKdWilayah.id;
            }
        });
        inpWilayah.constraintMandatory = true;
        inpWilayah.setWarn(R.id.registrasinpwp5alamatusaha_lblwarn_wilayah);

        inpTelp = setCompInpTextX(R.id.registrasinpwp5alamatusaha_inp_telp);
        inpTelp.constraintMandatory = true;
        inpTelp.setWarn(R.id.registrasinpwp5alamatusaha_lblwarn_telp);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        ActDataRegistrasiNpwp actData = getActData();
        EregDataByEmail saving = actData.savingEregDataByEmail;

        inpJalan.setValueUnchange(saving.usaha.almUsahaJalan);
        inpRt.setValueUnchange(saving.usaha.almUsahaRT);
        inpRw.setValueUnchange(saving.usaha.almUsahaRW);
        if(actData.tmpEregDataByEmailUsahaAlmUsahaKdWilayah !=null) inpWilayah.setValueUnchange( actData.tmpEregDataByEmailUsahaAlmUsahaKdWilayah.toPickedDTO() );
        inpTelp.setValueUnchange(saving.usaha.almUsahaHP);

        checkInput(null);
    }

    @Override
    public void onPause(){
        super.onPause();

        saveObj();
    }

    private void saveObj(){
        ActDataRegistrasiNpwp actData = getActData();
        EregDataByEmail saving = actData.savingEregDataByEmail;

        saving.usaha.almUsahaJalan = inpJalan.getValue(false);
        saving.usaha.almUsahaRT = inpRt.getValue(false);
        saving.usaha.almUsahaRW = inpRw.getValue(false);
        saving.usaha.almUsahaKdWilayah = (inpWilayah.getValue() != null ? ((WilayahDTO)inpWilayah.getValue().object).id : "");
        saving.usaha.almUsahaHP = saving.usaha.almUsahaNoTelp = inpTelp.getValue(false);
    }

    @Override
    public void save(){
        final BaseActivity context = (BaseActivity) activity;
        saveObj();

        final ActDataRegistrasiNpwp actData = getActData();

            ApiReqEreg.updateKelengkapan(context, new RequestParamConfig(), actData.savingEregDataByEmail, new CommonCallback<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody data) {
                    context.nextFrag();
                }
            });

    }

}
