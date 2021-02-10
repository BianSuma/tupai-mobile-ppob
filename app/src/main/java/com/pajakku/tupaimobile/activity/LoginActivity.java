package com.pajakku.tupaimobile.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.model.Company;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.response.ResponseAccessToken;
import com.pajakku.tupaimobile.model.dto.response.ResponseAuthorize;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseMe;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConf;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.SuccessFailCallback;
import com.pajakku.tupaimobile.util.Validator;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class LoginActivity extends RepositoryActivity {

    private AccountManager accountManager;

    private AppEditText inpUsername;
    private AppEditText inpPassword;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_login);

        accountManager = AccountManager.get(getBaseContext());

        TextView tvVer = findViewById(R.id.login_label_ver);
        tvVer.setText(getString(R.string.global_version, getPInfo().versionName));

        if((inpUsername = findViewById(R.id.login_inp_username)) == null) throw new RuntimeException();
        inpUsername.constraintMandatory = true;
        inpUsername.setConstraintNoMiddleSpace();
        inpUsername.setText( getSpString(AppConstant.SP_USERNAME) );

        if(!AppConf.NO_LONG_CLICK){
            inpUsername.inpText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    inpUsername.setText("abdul");
                    inpPassword.setText("12qwaszx");
                    return true;
                }
            });
        }

        if((inpPassword = findViewById(R.id.login_inp_password)) == null) throw new RuntimeException();
        inpPassword.constraintMandatory = true;

//        Intent itn = getIntent();
//        inpUsername.getEditText().setText( itn.getStringExtra(AccountManager.KEY_ACCOUNT_NAME) );

        // TODO: @test
//        inpUsername.getEditText().setText("abdul");
//        inpPassword.getEditText().setText("12qwaszx");
//        inpUsername.getEditText().setText("sinatria");
//        inpPassword.getEditText().setText("Jordy123");

        Button btnLogin = findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin();
            }
        });

        Button btnSignUp = findViewById(R.id.login_btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clickSignUp();
                SignUpActivity.startAct(context);
            }
        });

        TextView btnReset = findViewById(R.id.login_btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordActivity.startAct(context);
            }
        });

        setClickView(R.id.login_btn_aktivasi, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivationActivity.startAct(context);
            }
        });

        validator = new Validator();
        validator.addEdit(inpUsername);
        validator.addEdit(inpPassword);
    }

    @Override
    protected void onResume(){
        super.onResume();

        long lastLogin = SharePref.getLong(this, SharePref.SP_LAST_LOGIN);
        if(System.currentTimeMillis() - lastLogin < 400){
            finish();
            return;
        }

        String username = SharePref.getStr(this, AppConstant.SP_USERNAME);
        inpUsername.setText( username );
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void httpAuthorize(){

        final String username = inpUsername.getText();
        final String password = inpPassword.getText();

        requestAuthorize(username, password, new SuccessFailCallback<ResponseAuthorize,ResponseError>() {
            @Override
            public void onSuccess(ResponseAuthorize data) {
                httpAccessToken(username, password, data);
            }
            @Override
            public boolean onFail(ResponseError re){
                String msg = "Gagal login. Periksa kembali data masukan.";
                if(re != null) msg = re.error_description;
                if(msg != null) Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void httpAccessToken(final String username, final String password, final ResponseAuthorize responseAuthorize){
        requestAccessToken(responseAuthorize, new SuccessFailCallback<ResponseAccessToken,ResponseError>() {
            @Override
            public void onSuccess(ResponseAccessToken data) {
                httpAccessTokenSuccess(username, password, data);
            }
            @Override
            public boolean onFail(ResponseError re){
                return true;
            }
        });
    }
    private void httpAccessTokenSuccess(final String username, final String password, final ResponseAccessToken responseAccessToken){
        requestMeWithDialog(responseAccessToken.access_token, new SuccessFailCallback<ResponseMe,ResponseError>() {
            @Override
            public void onSuccess(ResponseMe data) {
                // data responseMe sudah disimpan di cache
                saveLoginResult(username, password, responseAccessToken);
            }
            @Override
            public boolean onFail(ResponseError re){
                return true;
            }
        });

    }

    private void saveLoginResult(String username, String password, ResponseAccessToken responseAccessToken) {

        String key = String.valueOf( System.currentTimeMillis() );
        setSpString(AppConstant.SP_PASSWORD_AES_KEY, key);
        String passwordEncrypt;
        try {
            passwordEncrypt = AESCrypt.encrypt(key, password);
        }catch (GeneralSecurityException e){
            passwordEncrypt = password;
        }

//        Account account = new Account(username, getString(R.string.key_account_type));
//        accountManager.addAccountExplicitly(account, passwordEncrypt, null);
//        accountManager.setPassword(account, passwordEncrypt);
//        accountManager.setUserData(account, AppConstant.AUD_REFRESH_TOKEN, responseAccessToken.refresh_token);

//        setAuthTokenExpire(account, responseAccessToken.access_token, responseAccessToken.expires_in);
        setAuthTokenExpire(username, responseAccessToken.access_token, responseAccessToken.expires_in);

        finish();
        
    }


    public void clickLogin(){
        if(!validator.check(this)) return;

        httpAuthorize();  // TODO: @prod

        // TODO: @test
//        String username = inpUsername.getText();
//        String password = inpPassword.getText();
//        ResponseMe responseMe = new ResponseMe();
//        responseMe.mobilePhone = "888888888888";
//        responseMe.email = "han@site.com";
//        setCache(AppConstant.SP_CACHEKEY_ME, responseMe);
//        ResponseAccessToken rat = new ResponseAccessToken();
//        rat.access_token = "access_token";
//        rat.expires_in = System.currentTimeMillis()+3600000;
//        rat.refresh_token = "refresth_token";
//        saveLoginResult(username, password, rat);

    }

//    public void clickSignUp(){
//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
//        finish();
//    }

    public static void startAct(Activity act){
        Intent itn = new Intent(act, LoginActivity.class);
        act.startActivity(itn);
    }
}
