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
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RegistrasiNpwp2AddressFragment extends BaseFragment {

    private InpTextX inpJalan;
    private InpTextX inpRt;
    private InpTextX inpRw;
    private InpPicker inpWilayah;

    private ActDataRegistrasiNpwp getActData(){
        return ((RegistrasiNpwpActivity)activity).actData;
    }

    public RegistrasiNpwp2AddressFragment() {
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
        View v = onCreateView(R.layout.fragment_registrasinpwp2_address, inflater, container, savedInstanceState);
//
        inpJalan = setCompInpTextX(R.id.registrasinpwp2address_inp_jalan);
        inpJalan.constraintMandatory = true;
        inpJalan.setWarn(R.id.registrasinpwp2address_lblwarn_jalan);

        inpRt = setCompInpTextX(R.id.registrasinpwp2address_inp_rt);
        inpRt.setWarn(R.id.registrasinpwp2address_lblwarn_rt);

        inpRw = setCompInpTextX(R.id.registrasinpwp2address_inp_rw);
        inpRw.setWarn(R.id.registrasinpwp2address_lblwarn_rw);

        inpWilayah = setCompInpPicker(R.id.registrasinpwp2address_inp_wilayah, AppConstant.ACTRES_COMMON, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                saveObj();
                KodeWilayahActivity.startAct(context, ((InpPicker)data).actRes);
            }
        }, new CommonCallback<PickedDTO>() {
            @Override
            public void onSuccess(PickedDTO data) {
                actData.tmpValidasi1AlmKTPKdWilayah = ((WilayahDTO)data.object);
                saving.almKTPKdWilayah = actData.tmpValidasi1AlmKTPKdWilayah.id;
            }
        });
        inpWilayah.constraintMandatory = true;
        inpWilayah.setWarn(R.id.registrasinpwp2address_lblwarn_wilayah);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;

        inpJalan.setValueUnchange(saving.almKTPJalan);
        inpRt.setValueUnchange(saving.domisili.almDomisiliRT);
        inpRw.setValueUnchange(saving.domisili.almDomisiliRW);
        if(actData.tmpValidasi1AlmKTPKdWilayah !=null) inpWilayah.setValueUnchange( actData.tmpValidasi1AlmKTPKdWilayah.toPickedDTO() );

        checkInput(null);
    }

    private void saveObj(){
        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;

        saving.domisili.almDomisiliJalan = saving.almKTPJalan = inpJalan.getValue(false);
        saving.domisili.almDomisiliRT = inpRt.getValue(false);
        saving.domisili.almDomisiliRW = inpRw.getValue(false);
        saving.almKTPKdWilayah = (inpWilayah.getValue() != null ? ((WilayahDTO)inpWilayah.getValue().object).id : "");
    }

    @Override
    public void save(){
        final BaseActivity context = (BaseActivity) activity;
        saveObj();

        final ActDataRegistrasiNpwp actData = getActData();

            ReqValidasi1 saving = actData.savingValidasi1;
            saving.nikExist = true;
            saving.nikValid = true;
            saving.jenisWp = ReqValidasi1.JENISWP_USAHA;  // USAHAWAN
            saving.kategoriWp = "1";  // KATEGORIWP_OP
//                saving.kdNegaraWp = "ID";
            saving.stsPusat = "PUSAT";

//                saving.noIDWp = "3203122910860003";
//                saving.noKKWp = "3271040301180006";
//                saving.tmpLahirWp = "Cianjur";
//                saving.tglLahirWp = "29-10-1986";
//                saving.almKTPJalan = "Loji";
            saving.almKTPNomor = "";
//                saving.almKTPKdWilayah = "3271041016";
//                saving.nomorHPWp = "08987020869";
            saving.domisili.almDomisiliBlok = "";
//                saving.domisili.almDomisiliJalan = "Loji";
//                saving.domisili.almDomisiliRT = "2";
//                saving.domisili.almDomisiliRW = "1";
            ApiReqEreg.validasi1(context, new RequestParamConfig(), saving, new CommonCallback<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody data) {
                    nextFrag();
                }
            });
    }

    private void nextFrag(){
        BaseActivity context = (BaseActivity) activity;
        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;
        if( saving.isWNI(false) ){
            context.nextFrag();
        }else{
            context.gotoFrag(RegistrasiNpwpActivity.FRAG_KLU);
        }
    }


}
