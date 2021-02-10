package com.pajakku.tupaimobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.model.dto.request.RequestFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Validator;

import retrofit2.Call;

@Deprecated
public class ActivateMCActivity extends BaseActivity {

//    private boolean modeInsertPhone = true;
    private String insertedPhone = null;

    private TextView labelGuide;
    private AppEditText inpPhoneOTP;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_mc);

        AppActionBar appActionBar = findViewById(R.id.inputotp_appactionbar);
        appActionBar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLeftTopBtn();
            }
        });

        labelGuide = findViewById(R.id.inputotp_label_guide);

        inpPhoneOTP = findViewById(R.id.inputotp_inp_phoneotp);
        inpPhoneOTP.constraintMandatory = true;
        inpPhoneOTP.setConstraintNumber();

        Button btnSend = findViewById(R.id.inputotp_btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSend();
            }
        });

        validator = new Validator();
        validator.addEdit(inpPhoneOTP);


        if(savedInstanceState != null) {
            insertedPhone = savedInstanceState.getString(AppConstant.SP_ACTIVATEMC_MODEINSERTPHONE, null);
            if( insertedPhone != null ) successSendPhone();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(AppConstant.SP_ACTIVATEMC_MODEINSERTPHONE, insertedPhone);
    }

    private void clickLeftTopBtn(){
        if( insertedPhone == null ){
            finish();
        }else{
            switchToInsertPhone();
        }
    }

    private void clickSend(){
        if( ! validator.check(this) ) return;

        if(insertedPhone == null){
            requestSendPhone();
        }else{
            requestSendOTP();
        }
    }

    private void requestSendPhone(){

        RequestFinnet rf = new RequestFinnet();
        rf.requestType = RequestFinnet.REQUESTTYPE_EMON_REGISTERPHONE;
        rf.phoneNumber = inpPhoneOTP.getText();
        final RequestFinnet requestFinnet = rf;

        requestHttpSimple(true, R.string.progressdialog_emonregisterphone, true, null, true, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.emonRegisterPhone(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseFinnet response) {
                if( response.statusCode.equals(ResponseFinnet.STATUSCODE_TUPAI_SEND_OTP) ){
                    successSendPhone();
                }else if( response.statusCode.equals(ResponseFinnet.STATUSCODE_TUPAI_EMON_REGISTERED) ){
                    successSendOTP();
                }
            }

            @Override
            public boolean onFail(ResponseError error) {
                return true;
            }
        });
    }

    private void successSendPhone(){
        insertedPhone = inpPhoneOTP.getText();
        labelGuide.setText(R.string.inputotp_label_guideotp);
        inpPhoneOTP.setText("");
        inpPhoneOTP.setHint(R.string.inputotp_hint_otp);

    }

    private void switchToInsertPhone(){
        insertedPhone = null;
        labelGuide.setText(R.string.inputotp_label_guidephone);
        inpPhoneOTP.setText("");
        inpPhoneOTP.setHint(R.string.inputotp_hint_phone);
    }

    private void requestSendOTP(){

        RequestFinnet rf = new RequestFinnet();
        rf.requestType = RequestFinnet.REQUESTTYPE_EMON_SENDOTP;
        rf.phoneNumber = insertedPhone;
        rf.otp = inpPhoneOTP.getText();
        final RequestFinnet requestFinnet = rf;

        requestHttpSimple(true, R.string.progressdialog_sendemonotp, true, null, true, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.sendEmonOTP(bearerAuth, "otp", "custStatusCode");
            }

            @Override
            public void onSuccess(ResponseFinnet response) {
                successSendOTP();
            }

            @Override
            public boolean onFail(ResponseError error) {
                return true;
            }
        });
    }

    private void successSendOTP(){
        Toast.makeText(this, R.string.inputotp_toast_successotp, Toast.LENGTH_LONG).show();
        finish();
    }
}
