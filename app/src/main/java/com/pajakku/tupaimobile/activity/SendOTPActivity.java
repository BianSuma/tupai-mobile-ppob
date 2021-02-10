package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Validator;

import retrofit2.Call;

public class SendOTPActivity extends BaseActivity {

//    private boolean modeInsertPhone = true;
    private String insertedPhone = null;

    private TextView labelGuide;
//    private AppEditText inpPhoneOTP;
//    private CodeInput ciOtp;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        AppActionBar appActionBar = findViewById(R.id.inputotp_appactionbar);
        appActionBar.setBackFinish(this);

        labelGuide = findViewById(R.id.inputotp_label_guide);

//        inpPhoneOTP = findViewById(R.id.inputotp_inp_phoneotp);
//        inpPhoneOTP.constraintMandatory = true;
//        inpPhoneOTP.setConstraintNumber();
//        ciOtp = findViewById(R.id.sendotp_inp_otp);
//        if(ciOtp == null) throw new RuntimeException();

        Button btnSend = findViewById(R.id.inputotp_btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSend();
            }
        });

        validator = new Validator();
//        validator.addEdit(inpPhoneOTP);


//        if(savedInstanceState != null) {
//            insertedPhone = savedInstanceState.getString(AppConstant.SP_ACTIVATEMC_MODEINSERTPHONE, null);
//            if( insertedPhone != null ) successSendPhone();
//        }

//        Intent intent = getIntent();
//        insertedPhone = intent.getStringExtra(AppConstant.ITN_TOPUPSENDOTP_CUSTSTATUSCODE);
//        successSendPhone();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putString(AppConstant.SP_ACTIVATEMC_MODEINSERTPHONE, insertedPhone);
//    }

    private void clickSend(){
//        if( ! validator.check(this) ) return;

        requestSendOTP();
    }

//    private void successSendPhone(){
//        insertedPhone = inpPhoneOTP.getText();
//        labelGuide.setText(R.string.inputotp_label_guideotp);
//        inpPhoneOTP.setText("");
//        inpPhoneOTP.setHint(R.string.inputotp_hint_otp);
//
//    }

    private void requestSendOTP(){

        String o = "";
        Character c[];
//        try {
//            c = ciOtp.getCode();
//            if(c == null) throw new Exception();
//            if(c.length != 6) throw new Exception();
//
//            for (Character i : c){
//                if(i != null) o += i.toString();
//                else throw new Exception();
//            }
//
//        }catch (Exception e){
//            Toast.makeText(this, "Wajib diisi enam angka.", Toast.LENGTH_LONG).show();
//            return;
//        }
;
        final String otp = o;

        Intent intent = getIntent();
        final String custStatusCode = intent.getStringExtra(AppConstant.ITN_TOPUPSENDOTP_CUSTSTATUSCODE);

        requestHttpSimple(true, R.string.progressdialog_sendemonotp, true, null, true, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.sendEmonOTP(bearerAuth, otp, custStatusCode);
            }

            @Override
            public void onSuccess(ResponseFinnet response) {
                if(response.statusCode != null) {
                    if(response.statusCode.equals(ResponseFinnet.STATUSCODE_EMON_OK)) {
                        successSendOTP();
                    }else{
                        Toast.makeText(SendOTPActivity.this, getString(R.string.sendotp_toast_wrongotp), Toast.LENGTH_LONG).show();
                    }
                }
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
