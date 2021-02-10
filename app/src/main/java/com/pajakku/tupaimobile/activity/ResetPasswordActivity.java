package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.model.dto.request.RequestResetPass;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterface;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.Validator;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ResetPasswordActivity extends BaseActivity {

    private AppEditText inpEmail;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        AppActionBar appActionBar = findViewById(R.id.resetpass_appactionbar);
        appActionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordActivity.this.finish();
            }
        }, null);

        if((inpEmail = findViewById(R.id.resetpass_inp_email)) == null) throw new RuntimeException();
        inpEmail.constraintMandatory = true;

        Button btnReset = findViewById(R.id.resetpass_btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReset();
            }
        });

        // TODO: @test
//        inpEmail.getEditText().setText("dullohmail@yahoo.com");

        validator = new Validator();
        validator.addEdit(inpEmail);
    }

    private void clickReset()
    {
        if(!validator.check(this)) return;

        // TODO: @test
//        if(true) successReset();

        final String email = inpEmail.getText();

        requestHttp(false, R.string.progressdialog_resetpass, true, null, true, new HttpCallbackInterface<ResponseBody,Void>() {
            @Override
            public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return ssoService.resetPass(new RequestResetPass(email));
            }
            @Override
            public Call<ResponseBody> requestMethodStream(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return null;
            }

            @Override
            public void onSuccess(ResponseBody response) {
                successReset();
            }

            @Override
            public void onSuccessStream(List<Void> response) {

            }

            @Override
            public boolean onFail(ResponseError re){
                return true;
            }
        });
    }

    private void successReset(){
        Utility.toast(this, R.string.resetpass_toast_checkemail);
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
        finish();
    }

    public static void startAct(Activity act){
        Intent intent = new Intent(act, ResetPasswordActivity.class);
        act.startActivity(intent);
    }
}
