package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.fragment.registrasinpwp.RegistrasiNpwp0FotoIdentitasFragment;
import com.pajakku.tupaimobile.fragment.registrasinpwp.RegistrasiNpwp1ProfilFragment;
import com.pajakku.tupaimobile.fragment.registrasinpwp.RegistrasiNpwp2AddressFragment;
import com.pajakku.tupaimobile.fragment.registrasinpwp.RegistrasiNpwp3WajahFragment;
import com.pajakku.tupaimobile.fragment.registrasinpwp.RegistrasiNpwp4KluFragment;
import com.pajakku.tupaimobile.fragment.registrasinpwp.RegistrasiNpwp5AlamatUsahaFragment;
import com.pajakku.tupaimobile.fragment.registrasinpwp.RegistrasiNpwp6SubmitFragment;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.ArrayList;
import java.util.List;

public class RegistrasiNpwpActivity extends RepositoryActivity {
    public static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_REGISTRASI_NPWP;
    public ActDataRegistrasiNpwp actData;

    public static final int FRAG_ADDRESS = 2;
    public static final int FRAG_KLU = 4;

//    private AppActionBar appActionBar;
    private AppCompatImageView btnActionbarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_registrasi_npwp);

        initData(savedInstanceState);

        findViewById(R.id.registrasinpwp_btn_actionbar_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseFragment bf = getCurrFrag();
                if(bf instanceof RegistrasiNpwp4KluFragment){
                    ((RegistrasiNpwp4KluFragment)bf).onFragBack();
                }
                if( ! actData.savingValidasi1.isWNI(false) &&
                        getFragIdx() == FRAG_KLU
                    ){
                    gotoFrag(FRAG_ADDRESS);
                }else{
                    prevFrag();
                }
            }
        });

        btnActionbarRight = findViewById(R.id.registrasinpwp_btn_actionbar_right);
        btnActionbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrFrag().validateSave();
            }
        });

        List<Fragment> frags = new ArrayList<>();
        frags.add(new RegistrasiNpwp0FotoIdentitasFragment());
        frags.add(new RegistrasiNpwp1ProfilFragment());
        frags.add(new RegistrasiNpwp2AddressFragment());
        frags.add(new RegistrasiNpwp3WajahFragment());
        frags.add(new RegistrasiNpwp4KluFragment());
        frags.add(new RegistrasiNpwp5AlamatUsahaFragment());
        frags.add(new RegistrasiNpwp6SubmitFragment());
        setViewPager(R.id.registrasinpwp_viewpager, frags);

    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
//            Intent itn = getIntent();
//            ActParamRefundForm ap = (ActParamRefundForm) itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataRegistrasiNpwp();
//            actData.ap = ap;

            HolderCommon2Val kdNegara = new HolderCommon2Val();
            kdNegara.mini = AppConstant.EREG_KODE_NEGARA_INDO;
            kdNegara.bold = "Indonesia";

            actData.tmpValidasi1kdNegaraWp = kdNegara;
            actData.savingValidasi1.kdNegaraWp = actData.tmpValidasi1kdNegaraWp.mini;

        }else{
            actData = (ActDataRegistrasiNpwp) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ACTDATA_KEY, actData);
    }

    @Override
    protected void setViewData(){

//        ReqRefund saving = actData.saving;
//        SpinItem2Str bank = new SpinItem2Str();
//        bank.str0 = saving.bankCode;
//        bank.str1 = saving.bankName;
//
//        inpBank.setValueUnchange( bank );
//        inpRekening.setValueUnchange(saving.accountNumber);
//        inpName.setValueUnchange(saving.accountHolderName);
//        inpKet.setValueUnchange(saving.description);
    }

//    private void clickQuickHelp(){
//
//        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
//        BubbleShowCaseBuilder sc;
//
//        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpwp), getString(R.string.addeditwp_qhelpbody_inpwp), (View)inpWpNpwp.getParent(), inpWpNpwp);
//        if(sc != null){
//            listSc.add(sc);
//        }
//        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpname), getString(R.string.addeditwp_qhelpbody_inpname), (View)inpWpNpwp.getParent(), inpWpName);
//        if(sc != null){
//            listSc.add(sc);
//        }
//        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpaddress), getString(R.string.addeditwp_qhelpbody_inpaddress), (View)inpWpAddress.getParent(), inpWpAddress);
//        if(sc != null){
//            listSc.add(sc);
//        }
//        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpcity), getString(R.string.addeditwp_qhelpbody_inpcity), (View)inpWpNpwp.getParent(), inpWpCity);
//        if(sc != null){
//            listSc.add(sc);
//        }
//        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_save), getString(R.string.addeditwp_qhelpbody_save), (View)btnSave.getParent(), btnSave);
//        if(sc != null){
//            listSc.add(sc);
//        }
//
//        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
//        scs.addShowCases(listSc);
//        scs.show();
//
//    }

    @Override
    protected void saveObj(){
//        SpinItem2Str bank = (SpinItem2Str) inpBank.getValue();
//        if(bank == null) bank = new SpinItem2Str();
//
//        actData.saving.sspId = actData.ap.responseSsp.id;
//        actData.saving.bankCode = bank.str0;
//        actData.saving.bankName = bank.str1;
//        actData.saving.accountNumber = inpRekening.getValue(false);
//        actData.saving.accountHolderName = inpName.getValue(false);
//        actData.saving.description = inpKet.getValue(false);
    }

    @Override
    protected void save(){
        final Activity context = this;
        saveObj();

    }

    public void setActionbarBtnRightVisible(boolean b){
        btnActionbarRight.setVisibility(b?View.VISIBLE:View.GONE);
    }


    public static void startAct(Activity act){
//        ActParamRefundForm ap = new ActParamRefundForm();
//        ap.ssId = ssId;
//        ap.responseSsp = responseSsp;

        Intent itn = new Intent(act, RegistrasiNpwpActivity.class);
//        itn.putExtra(ACTDATA_KEY, ap);
        act.startActivity(itn);
    }
}
