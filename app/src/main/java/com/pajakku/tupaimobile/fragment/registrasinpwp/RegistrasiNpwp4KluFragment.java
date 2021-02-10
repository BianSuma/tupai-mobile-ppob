package com.pajakku.tupaimobile.fragment.registrasinpwp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.activity.KluListActivity;
import com.pajakku.tupaimobile.activity.PickKodeNegaraActivity;
import com.pajakku.tupaimobile.activity.RegistrasiNpwpActivity;
import com.pajakku.tupaimobile.api.ApiReqEreg;
import com.pajakku.tupaimobile.component.AppRadio;
import com.pajakku.tupaimobile.component.AppSpinner;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpPicker;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.model.spinitem.SpinItem;
import com.pajakku.tupaimobile.model.spinitem.SpinItem2StrCustom;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RegistrasiNpwp4KluFragment extends BaseFragment {

    private TextInputEditText lblEmail;

    private AppSpinner inpJnsPegawai;
    private InpPicker inpKluCode;
    private InpTextX inpUraian;

    private InpPicker inpKlu2Code;
    private InpTextX inpMerkUsaha;
    private AppRadio inpMetodePembukuan;
    private AppSpinner inpTahunBuku0;
    private AppSpinner inpTahunBuku1;
    private AppRadio inpPunyaKaryawan;
    private InpTextX inpKlu2Uraian;

    private AppCompatButton btnReload;

    private ActDataRegistrasiNpwp getActData(){
        return ((RegistrasiNpwpActivity)activity).actData;
    }

    public RegistrasiNpwp4KluFragment() {
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
        View v = onCreateView(R.layout.fragment_registrasinpwp4_klu, inflater, container, savedInstanceState);

        lblEmail = v.findViewById(R.id.registrasinpwp4klu_lbl_email_edittext);

        inpJnsPegawai = setCompAppSpinner(R.id.registrasinpwp4klu_inp_jnspegawai, pegawaiList(), null);
        inpJnsPegawai.constraintMandatory = true;
        inpJnsPegawai.setWarn(R.id.registrasinpwp4klu_lblwarn_jnspegawai);

        inpKluCode = setCompInpPicker(R.id.registrasinpwp4klu_inp_klucode, AppConstant.ACTRES_COMMON, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                KluListActivity.startAct(context, ((InpPicker)data).actRes);
            }
        }, new CommonCallback<PickedDTO>() {
            @Override
            public void onSuccess(PickedDTO data) {
                actData.tmpEregDataByEmailKluklu1KdKlu = data;
                actData.savingEregDataByEmail.klu.klu1KdKlu = ((HolderCommon2Val)actData.tmpEregDataByEmailKluklu1KdKlu.object).bold;
            }
        });
        inpKluCode.constraintMandatory = true;
        inpKluCode.setWarn(R.id.registrasinpwp4klu_lblwarn_klucode);

        inpUraian = setCompInpTextX(R.id.registrasinpwp4klu_inp_uraian);
        inpUraian.constraintMandatory = true;
        inpUraian.setWarn(R.id.registrasinpwp4klu_lblwarn_uraian);

        inpKlu2Code = setCompInpPicker(R.id.registrasinpwp4klu_inp_klu2_code, AppConstant.ACTRES_KLU, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                KluListActivity.startAct(context, ((InpPicker)data).actRes);
            }
        }, new CommonCallback<PickedDTO>() {
            @Override
            public void onSuccess(PickedDTO data) {
                actData.tmpEregDataByEmailklu2klu2KdKlu = data;
                actData.savingEregDataByEmail.klu2.klu2KdKlu = ((HolderCommon2Val)actData.tmpEregDataByEmailklu2klu2KdKlu.object).bold;
            }
        });
        inpKlu2Code.constraintMandatory = true;
        inpKlu2Code.setWarn(R.id.registrasinpwp4klu_lblwarn_klu2_code);

        inpMerkUsaha = setCompInpTextX(R.id.registrasinpwp4klu_inp_merk);
        inpMerkUsaha.constraintMandatory = true;
        inpMerkUsaha.setWarn(R.id.registrasinpwp4klu_lblwarn_merk);

        inpMetodePembukuan = setCompAppRadio(R.id.registrasinpwp4klu_inp_metodebuku);
        inpMetodePembukuan.constraintMandatory = true;
        inpMetodePembukuan.setHeadView(R.id.registrasinpwp4klu_lbl_metodebuku);
        inpMetodePembukuan.setWarn(R.id.registrasinpwp4klu_lblwarn_metodebuku);

        inpTahunBuku0 = setCompAppSpinner(R.id.registrasinpwp4klu_inp_tahunbuku0, tahunBukuList(), null);
        inpTahunBuku0.constraintMandatory = true;
        inpTahunBuku0.setWarn(R.id.registrasinpwp4klu_lblwarn_tahunbuku0);

        inpTahunBuku1 = setCompAppSpinner(R.id.registrasinpwp4klu_inp_tahunbuku1, tahunBukuList(), null);
        inpTahunBuku1.constraintMandatory = true;
        inpTahunBuku1.setWarn(R.id.registrasinpwp4klu_lblwarn_tahunbuku1);

        inpPunyaKaryawan = setCompAppRadio(R.id.registrasinpwp4klu_inp_punyakaryawan);
        inpPunyaKaryawan.constraintMandatory = true;
        inpPunyaKaryawan.setHeadView(R.id.registrasinpwp4klu_lbl_punyakaryawan);
        inpPunyaKaryawan.setWarn(R.id.registrasinpwp4klu_lblwarn_punyakaryawan);

        inpKlu2Uraian = setCompInpTextX(R.id.registrasinpwp4klu_inp_klu2uraian);
        inpKlu2Uraian.constraintMandatory = true;
        inpKlu2Uraian.setWarn(R.id.registrasinpwp4klu_lblwarn_klu2uraian);

        btnReload = v.findViewById(R.id.registrasinpwp4klu_btn_reload);
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

        setViewData();
    }

    @Override
    public void onPause(){
        super.onPause();

        saveObj();
    }

    private void setViewData(){
        final ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 reqValidasi1 = actData.savingValidasi1;

        if(actData.savingEregDataByEmail != null) {
            setViewData(actData);
        }else{
            ApiReqEreg.getDataByEmail(activity, new RequestParamConfig(), reqValidasi1.emailWp, new SuccessFailCallback<EregDataByEmail, ResponseDTO>() {
                @Override
                public void onSuccess(EregDataByEmail data) {
                    data.setNulled(actData);
                    actData.savingEregDataByEmail = data;

                    actData.setNulled();

                    setViewData(actData);
                }

                @Override
                public void onFail(ResponseDTO data) {
                    actData.savingEregDataByEmail = null;
                    btnReload.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setViewData(ActDataRegistrasiNpwp actData){
        EregDataByEmail saving = actData.savingEregDataByEmail;
//        ActDataRegistrasiNpwp actData = getActData();

        lblEmail.setText( saving.wp.emailWpNotNull() );

        inpJnsPegawai.setValueUnchange(actData.tmpEregDataByEmailKluklu1JnsPegawai);
        inpKluCode.setValueUnchange(actData.tmpEregDataByEmailKluklu1KdKlu);
        inpUraian.setValueUnchange(saving.klu.klu1Uraian);
        inpKlu2Code.setValueUnchange(actData.tmpEregDataByEmailklu2klu2KdKlu);
        inpMerkUsaha.setValueUnchange(saving.klu2.klu2Merk);
        inpMetodePembukuan.setValueUnchange(saving.klu2.klu2Pembukuan);
        inpTahunBuku0.setValueUnchange(actData.tmpEregDataByEmailklu2klu2ThBukuAwal);
        inpTahunBuku1.setValueUnchange(actData.tmpEregDataByEmailklu2klu2ThBukuAkhir);
        inpPunyaKaryawan.setValueUnchange(saving.klu2.klu2HavePegawai);
        inpKlu2Uraian.setValueUnchange(saving.klu2.klu2Uraian);

        btnReload.setVisibility(View.GONE);

        checkInput(null);
    }

    public void onFragBack(){
        ActDataRegistrasiNpwp actData = getActData();
        actData.savingEregDataByEmail = null;
    }

    private void saveObj(){
        ActDataRegistrasiNpwp actData = getActData();
        EregDataByEmail saving = actData.savingEregDataByEmail;

        actData.tmpEregDataByEmailKluklu1JnsPegawai = inpJnsPegawai.getValue();
        actData.tmpEregDataByEmailklu2klu2ThBukuAwal = inpTahunBuku0.getValue();
        actData.tmpEregDataByEmailklu2klu2ThBukuAkhir = inpTahunBuku1.getValue();

        saving.klu.klu1JnsPegawai = actData.tmpEregDataByEmailKluklu1JnsPegawai != null ? ((SpinItem2StrCustom)actData.tmpEregDataByEmailKluklu1JnsPegawai).idStr : "";
        saving.klu.klu1KdKlu = actData.tmpEregDataByEmailKluklu1KdKlu != null ? ((HolderCommon2Val)actData.tmpEregDataByEmailKluklu1KdKlu.object).bold : "";
        saving.klu.klu1Uraian = inpUraian.getValue(false);
        saving.klu2.klu2KdKlu = actData.tmpEregDataByEmailklu2klu2KdKlu != null ? ((HolderCommon2Val)actData.tmpEregDataByEmailklu2klu2KdKlu.object).bold : "";
        saving.klu2.klu2Merk = inpMerkUsaha.getValue(false);
        saving.klu2.klu2Pembukuan = inpMetodePembukuan.getValueStr(false);
        saving.klu2.klu2ThBukuAwal = actData.tmpEregDataByEmailklu2klu2ThBukuAwal != null ? ((SpinItem2StrCustom)actData.tmpEregDataByEmailklu2klu2ThBukuAwal).idStr : "";
        saving.klu2.klu2ThBukuAkhir = actData.tmpEregDataByEmailklu2klu2ThBukuAkhir != null ? ((SpinItem2StrCustom)actData.tmpEregDataByEmailklu2klu2ThBukuAkhir).idStr : "";
        saving.klu2.klu2HavePegawai = inpPunyaKaryawan.getValueStr(false);
        saving.klu2.klu2Uraian = inpKlu2Uraian.getValue(false);
    }

    @Override
    public void save(){
        final BaseActivity context = (BaseActivity) activity;
        saveObj();

        context.nextFrag();
    }

    @Override
    public boolean onSubmitValidate(BaseActivity act, BaseInput bi){
        ActDataRegistrasiNpwp actData = getActData();
        return actData.savingEregDataByEmail != null;
    }

    @Override
    public void onChangeInputComponent(BaseActivity act, BaseInput bi, boolean isValid){
        boolean isPembukuan = inpMetodePembukuan.getValue(R.string.radiovalue_metodebuku_buku, false);

        inpTahunBuku0.setVisible(isPembukuan);
        inpTahunBuku1.setVisible(isPembukuan);
    }

    private List<SpinItem> pegawaiList(){
        SpinItem2StrCustom pegawai;
        List<SpinItem> pegawais = new ArrayList<>();
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "1";
        pegawai.str0 = "PNS";
        pegawais.add(pegawai);
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "2";
        pegawai.str0 = "TNI/POLRI";
        pegawais.add(pegawai);
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "3";
        pegawai.str0 = "Pensiunan";
        pegawais.add(pegawai);
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "4";
        pegawai.str0 = "Pegawai Swasta";
        pegawais.add(pegawai);
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "5";
        pegawai.str0 = "Pegawai BUMN/BUMD";
        pegawais.add(pegawai);
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "6";
        pegawai.str0 = "Pegawai Badan Publik";
        pegawais.add(pegawai);
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "7";
        pegawai.str0 = "Pekerja pd pemberi kerja yg tdk termasuk subjek pajak";
        pegawais.add(pegawai);
        pegawai = new SpinItem2StrCustom();  // ----
        pegawai.idStr = "8";
        pegawai.str0 = "Pegawai Lainnya";
        pegawais.add(pegawai);

        return pegawais;
    }

    private List<SpinItem> tahunBukuList(){
        SpinItem2StrCustom d;
        List<SpinItem> l = new ArrayList<>();
        for(int i = 1; i<=12; i++) {
            d = new SpinItem2StrCustom();  // ----
            d.idStr = d.str0 = Integer.toString(i);
            l.add(d);
        }

        return l;
    }

}
