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
import com.pajakku.tupaimobile.activity.RegistrasiNpwpActivity;
import com.pajakku.tupaimobile.component.AppRadio;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpDate;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.DateFunc;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RegistrasiNpwp1ProfilFragment extends BaseFragment {

    private AppRadio inpGender;
    private AppRadio inpNikah;
    private InpTextX inpName;
    private InpTextX inpEmail;
    private InpTextX inpNik;
    private InpTextX inpKK;
//    private InpTextX inpKitas;
    private InpTextX inpLahirPlace;
    private InpDate inpLahirDate;
    private InpTextX inpTelp;
    private AppRadio inpTanggungan;
    private AppRadio inpJmlPenghasilan;

    private ActDataRegistrasiNpwp getActData(){
        return ((RegistrasiNpwpActivity)activity).actData;
    }

    public RegistrasiNpwp1ProfilFragment() {
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
        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;
        View v = onCreateView(R.layout.fragment_registrasinpwp1_profil, inflater, container, savedInstanceState);

        inpGender = setCompAppRadio(R.id.registrasinpwp1profil_inp_gender);
        inpGender.constraintMandatory = true;
        inpGender.setHeadView(R.id.registrasinpwp1profil_lbl_gender);
        inpGender.setWarn(R.id.registrasinpwp1profil_lblwarn_gender);

        inpNikah = setCompAppRadio(R.id.registrasinpwp1profil_inp_nikah);
        inpNikah.constraintMandatory = true;
        inpNikah.setHeadView(R.id.registrasinpwp1profil_lbl_nikah);
        inpNikah.setWarn(R.id.registrasinpwp1profil_lblwarn_nikah);

        inpName = setCompInpTextX(R.id.registrasinpwp1profil_inp_name);
        inpName.constraintMandatory = true;
        inpName.setWarn(R.id.registrasinpwp1profil_lblwarn_name);

        inpEmail = setCompInpTextX(R.id.registrasinpwp1profil_inp_email);
        inpEmail.constraintMandatory = true;
        inpEmail.setWarn(R.id.registrasinpwp1profil_lblwarn_email);

        inpNik = setCompInpTextX(R.id.registrasinpwp1profil_inp_nik);
        inpNik.constraintMandatory = true;
        inpNik.setWarn(R.id.registrasinpwp1profil_lblwarn_nik);

        inpKK = setCompInpTextX(R.id.registrasinpwp1profil_inp_kk);
        inpKK.constraintMandatory = true;
        inpKK.setWarn(R.id.registrasinpwp1profil_lblwarn_kk);

//        inpKitas = setCompInpTextX(R.id.registrasinpwp1profil_inp_kitas);
//        inpKitas.constraintMandatory = true;
//        inpKitas.setTypeKitas();
//        inpKitas.setWarn(R.id.registrasinpwp1profil_lblwarn_kitas);

        inpLahirPlace = setCompInpTextX(R.id.registrasinpwp1profil_inp_lahir_place);
        inpLahirPlace.constraintMandatory = true;
        inpLahirPlace.setWarn(R.id.registrasinpwp1profil_lblwarn_lahir_place);

        inpLahirDate = setCompInpDate(R.id.registrasinpwp1profil_inp_birthdate, null);
        inpLahirDate.constraintMandatory = true;
        inpLahirDate.setWarn(R.id.registrasinpwp1profil_lblwarn_birthdate);
        inpLahirDate.millisMax -= 300000000000L;

        inpTelp = setCompInpTextX(R.id.registrasinpwp1profil_inp_telp);
        inpTelp.constraintMandatory = true;
        inpTelp.setWarn(R.id.registrasinpwp1profil_lblwarn_telp);

        inpTanggungan = setCompAppRadio(R.id.registrasinpwp1profil_inp_tanggungan);
        inpTanggungan.constraintMandatory = true;
        inpTanggungan.setHeadView(R.id.registrasinpwp1profil_lbl_tanggungan);
        inpTanggungan.setWarn(R.id.registrasinpwp1profil_lblwarn_tanggungan);

        inpJmlPenghasilan = setCompAppRadio(R.id.registrasinpwp1profil_inp_jmlpenghasilan);
        inpJmlPenghasilan.constraintMandatory = true;
        inpJmlPenghasilan.setHeadView(R.id.registrasinpwp1profil_lbl_jmlpenghasilan);
        inpJmlPenghasilan.setWarn(R.id.registrasinpwp1profil_lblwarn_jmlpenghasilan);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;

        inpGender.setValueUnchange(saving.jenisKelamin);
        inpNikah.setValueUnchange(saving.stsNikahWp);
        inpName.setValueUnchange(saving.namaWp);
        inpEmail.setValueUnchange(saving.emailWp);
        inpNik.setValueUnchange(saving.noIDWp);
        inpKK.setValueUnchange(saving.noKKWp);
//        inpKitas.setValueUnchange(actData.savingKitas);
        inpLahirPlace.setValueUnchange(saving.tmpLahirWp);
        inpLahirDate.setValueUnchange(saving.tglLahirWp);
        inpTelp.setValueUnchange(saving.nomorHPWp);
        inpTanggungan.setValueUnchange(actData.savingTanggungan);
        inpJmlPenghasilan.setValueUnchange(actData.savingJmlPenghasilan);

        checkInput(null);
    }

    @Override
    public void onPause(){
        super.onPause();

        saveObj();
    }

    private void saveObj(){
        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;

        saving.jenisKelamin = inpGender.getValueStr(false);
        saving.stsNikahWp = inpNikah.getValueStr(false);
        saving.namaWp = inpName.getValue(false);
        saving.emailWp = inpEmail.getValue(false);
        saving.noIDWp = inpNik.getValue(false);
        saving.noKKWp = inpKK.getValue(false);
//        actData.savingKitas = inpKitas.getValue(false);
        saving.tmpLahirWp = inpLahirPlace.getValue(false);
        saving.tglLahirWp = DateFunc.longToIndoDate( inpLahirDate.getValue(true), false );
        saving.nomorHPWp = inpTelp.getValue(false);
        actData.savingTanggungan = inpTanggungan.getValueStr(false);
        actData.savingJmlPenghasilan = inpJmlPenghasilan.getValueStr(false);
    }

    @Override
    public void save(){
        final BaseActivity context = (BaseActivity) activity;
        saveObj();

        context.nextFrag();

    }

    @Override
    public void onChangeInputComponent(BaseActivity act, BaseInput bi, boolean isValid){
        ActDataRegistrasiNpwp actData = getActData();
        ReqValidasi1 saving = actData.savingValidasi1;

        boolean isWNI = saving.isWNI(false);
        inpNik.setHint( isWNI ? "NIK" : "No. Paspor");
        inpKK.setHint( isWNI ? "No. KK" : "Nomor Kitas" );
//        inpKitas.setVisible( ! isWNI);
    }
}
