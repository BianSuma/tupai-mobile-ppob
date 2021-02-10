package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.api.ApiReqMpnPajakku;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.AppSpinner;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.actdata.ActDataRefundForm;
import com.pajakku.tupaimobile.model.actparam.ActParamRefundForm;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqMpnRefund;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqRefund;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.model.spinitem.SpinItem;
import com.pajakku.tupaimobile.model.spinitem.SpinItem2Str;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Validator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class RefundFormActivity extends RepositoryActivity {
    public static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_REFUND;
    private ActDataRefundForm actData;

    private AppActionBar appActionBar;

    private AppSpinner inpBank;
    private InpTextX inpRekening;
    private InpTextX inpName;
    private InpTextX inpKet;

    private AppCompatButton btnSave;

    private int actionMode;
    private Wajibpajak wajibpajak;

//    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_form);

        initData(savedInstanceState);

        appActionBar = findViewById(R.id.refundform_actionbar);
        appActionBar.setBackFinish(this);

        SpinItem2Str aBank;
        List<SpinItem> banks = new ArrayList<>();
        aBank = new SpinItem2Str();  // --
        aBank.str0 = "002";
        aBank.str1 = "BRI";
        banks.add(aBank);
        aBank = new SpinItem2Str();  // --
        aBank.str0 = "008";
        aBank.str1 = "Bank Mandiri";
        banks.add(aBank);
        aBank = new SpinItem2Str();  // --
        aBank.str0 = "009";
        aBank.str1 = "BNI";
        banks.add(aBank);
        aBank = new SpinItem2Str();  // --
        aBank.str0 = "014";
        aBank.str1 = "BCA";
        banks.add(aBank);
        aBank = new SpinItem2Str();  // --
        aBank.str0 = "022";
        aBank.str1 = "CIMB Niaga";
        banks.add(aBank);

//        inpBank = new AppSpinner(this, null, R.id.refundform_inp_bank, banks, new CommonCallback<BaseInput>() {
//            @Override
//            public void onSuccess(BaseInput data) {
//                saveObj();
//            }
//        }, null);
        inpBank = setCompAppSpinner(R.id.refundform_inp_bank, banks, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                saveObj();
            }
        });
        inpBank.constraintMandatory = true;
        inpBank.setWarn(R.id.refundform_lblwarn_bank);

        inpRekening = setCompInpTextX(R.id.refundform_inp_rekening);
        inpRekening.constraintMandatory = true;
        inpRekening.setWarn(R.id.refundform_lblwarn_rekening);

        inpName = setCompInpTextX(R.id.refundform_inp_name);
        inpName.constraintMandatory = true;
        inpName.setWarn(R.id.refundform_lblwarn_name);

        inpKet = setCompInpTextX(R.id.refundform_inp_ket);
        inpKet.setWarn(R.id.refundform_lblwarn_ket);

//        if((inpRekening = findViewById(R.id.refundform_inp_rekening)) == null) throw new RuntimeException();
//        inpRekening.constraintMandatory = true;
//        if((inpName = findViewById(R.id.refundform_inp_name)) == null) throw new RuntimeException();
//        inpName.constraintMandatory = true;
//        if((inpKet = findViewById(R.id.refundform_inp_ket)) == null) throw new RuntimeException();

        setBotBtn(R.id.refundform_btn_save);
//        btnSave = findViewById(R.id.refundform_btn_save);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickSave();
//            }
//        });

//        validator = new Validator();
//        validator.addEdit(inpRekening);
//        validator.addEdit(inpName);
//        validator.addEdit(inpKet);

    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
            Intent itn = getIntent();
            ActParamRefundForm ap = (ActParamRefundForm) itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataRefundForm();
            actData.ap = ap;
        }else{
            actData = (ActDataRefundForm) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ACTDATA_KEY, actData);
    }

    @Override
    protected void setViewData(){

        ReqRefund saving = actData.saving;
        SpinItem2Str bank = null;
        if(saving.bankCode != null) {
            bank = new SpinItem2Str();
            bank.str0 = saving.bankCode;
            bank.str1 = saving.bankName;
        }

        inpBank.setValueUnchange( bank );
        inpRekening.setValueUnchange(saving.accountNumber);
        inpName.setValueUnchange(saving.accountHolderName);
        inpKet.setValueUnchange(saving.description);
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
        SpinItem2Str bank = (SpinItem2Str) inpBank.getValue();
        if(bank == null) bank = new SpinItem2Str();

        actData.saving.sspId = actData.ap.responseSsp.id;
        actData.saving.bankCode = bank.str0;
        actData.saving.bankName = bank.str1;
        actData.saving.accountNumber = inpRekening.getValue(false);
        actData.saving.accountHolderName = inpName.getValue(false);
        actData.saving.description = inpKet.getValue(false);
    }

    @Override
    protected void save(){
        final Activity context = this;
        saveObj();

        RequestParamConfig rpc = new RequestParamConfig();
        rpc.forceRequest = false;
        ApiMain.httpFirst(context, rpc, new SuccessFailCallback<AppStatusData, ResponseDTO>() {
            @Override
            public void onSuccess(AppStatusData appStatusData) {
                proceedRefund(appStatusData);
            }

            @Override
            public void onFail(ResponseDTO data) {

            }
        });

//        ApiMain.mpnPajakkuRefund(this, new RequestParamConfig(), actData.saving, new CommonCallback<ResponseSsp>() {
//            @Override
//            public void onSuccess(ResponseSsp data) {
//                successAction();
//            }
//        });
    }

    private void proceedRefund(AppStatusData appStatusData){
        Activity context = this;
        final ResponseSsp responseSsp = actData.ap.responseSsp;
        final ReqRefund saving = actData.saving;

        ReqMpnRefund reqMpnRefund = new ReqMpnRefund();
        reqMpnRefund.paymentId.$oid = responseSsp.payment.transRefId;
        reqMpnRefund.bankName = saving.bankName;
        reqMpnRefund.bankCode = saving.bankCode;
        reqMpnRefund.accountNumber = saving.accountNumber;
        reqMpnRefund.accountHolderName = saving.accountHolderName;
        reqMpnRefund.amount = responseSsp.payment.amount;
        reqMpnRefund.description = saving.description;
        ApiReqMpnPajakku.refund(context, new RequestParamConfig(), appStatusData, reqMpnRefund, new CommonCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {
                successAction();
            }
        });
    }

    private void successAction()
    {
        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                RefundFormActivity.this.finish();
            }
        });
    }

    public static void startAct(Activity act, ResponseSsp responseSsp){
        ActParamRefundForm ap = new ActParamRefundForm();
//        ap.ssId = ssId;
        ap.responseSsp = responseSsp;

        Intent itn = new Intent(act, RefundFormActivity.class);
        itn.putExtra(ACTDATA_KEY, ap);
        act.startActivity(itn);
    }
}
