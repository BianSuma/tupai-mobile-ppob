package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiReqSso;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import okhttp3.ResponseBody;

public class ActivationActivity extends BaseActivity {

    private InpTextX inpEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        AppActionBar appActionBar = findViewById(R.id.activation_appactionbar);
        appActionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivationActivity.this.finish();
            }
        }, null);

        inpEmail = setCompInpTextX(R.id.activation_inp_email);
        inpEmail.setTypeEmail();
        inpEmail.constraintMandatory = true;
        inpEmail.setWarn(R.id.activation_lblwarn_email);

        setBotBtn(R.id.activation_btn_ok);
    }

    @Override
    protected void onResume(){
        super.onResume();

        checkInput(null);
    }

    @Override
    protected void save()
    {
        String email = inpEmail.getValue(false);

        ApiReqSso.resendActivation(this, new RequestParamConfig(), email, new CommonCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {
                successResendActivation();
            }
        });
    }

    private void successResendActivation(){
        Utility.toast(this, "Email aktivasi telah dikirim. Silahkan cek email Anda.");
        finish();
    }

    public static void startAct(Activity act){
        Intent intent = new Intent(act, ActivationActivity.class);
        act.startActivity(intent);
    }
}
