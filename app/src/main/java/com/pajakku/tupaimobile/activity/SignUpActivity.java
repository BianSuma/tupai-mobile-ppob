package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.model.dto.request.RequestRegister;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseRegister;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.Validator;

import retrofit2.Call;

public class SignUpActivity extends BaseActivity {

    private AppEditText tvUsername;
    private AppEditText tvEmail;
    private AppEditText tvFullname;
    private AppEditText tvPhone;
//    private AppEditText tvPassword;
    private InpTextX inpPassword;
//    private AppEditText tvPasswordConfirm;
    private InpTextX inpPasswordConfirm;
    private AppCompatTextView lblWarnPasswordIsSame;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ((AppActionBar)findViewById(R.id.signup_actionbar)).setBackFinish(this);

        if((tvUsername = findViewById(R.id.signup_inp_username)) == null) throw new RuntimeException();
        tvUsername.constraintMandatory = true;
        tvUsername.setConstraintNoMiddleSpace();
        if((tvEmail = findViewById(R.id.signup_inp_email)) == null) throw new RuntimeException();
        tvEmail.constraintMandatory = true;
        if((tvFullname = findViewById(R.id.signup_inp_fullname)) == null) throw new RuntimeException();
        if((tvPhone = findViewById(R.id.signup_inp_phone)) == null) throw new RuntimeException();
        tvPhone.constraintMandatory = true;

        inpPassword = setCompInpTextX(R.id.signup_inp_password);
        inpPassword.constraintMandatory = true;
        inpPassword.constraintMin = 5;
        inpPassword.setPasswordToggle();
        inpPassword.setWarn(R.id.signup_lblwarn_password);

//        if((tvPassword = findViewById(R.id.signup_inp_password)) == null) throw new RuntimeException();
//        tvPassword.constraintMandatory = true;
//        tvPassword.constraintMin = 5;

        inpPasswordConfirm = setCompInpTextX(R.id.signup_inp_passwordconfirm);
        inpPasswordConfirm.constraintMandatory = true;
//        inpPasswordConfirm.constraintMin = 5;
        inpPasswordConfirm.setPasswordToggle();
        inpPasswordConfirm.setWarn(R.id.signup_lblwarn_passwordconfirm);

//        if((tvPasswordConfirm = findViewById(R.id.signup_inp_passwordconfirm)) == null) throw new RuntimeException();
//        tvPasswordConfirm.confirmSourceField = tvPassword;

        lblWarnPasswordIsSame = findViewById(R.id.signup_lblwarn_passwordconfirm_is_same);

        TextView tvTerm = findViewById(R.id.signup_btn_term);
        tvTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.gotoWebsite(SignUpActivity.this, AppConstant.PAJAKKU_LINK);
            }
        });

        // TODO: @test
//        tvUsername.getEditText().setText("user005");
//        tvEmail.getEditText().setText("dullohmail@yahoo.com");
//        tvFullname.getEditText().setText("user005");
//        tvPhone.getEditText().setText("089773892742");
//        tvPassword.getEditText().setText("Passwd123");
//        tvPasswordConfirm.getEditText().setText("Passwd123");

//        Button btnRegister = findViewById(R.id.signup_btn_register);
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickRegister();
//            }
//        });

        TextView btnAlreadyRegister = findViewById(R.id.signup_btn_alreadyregister);
        btnAlreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clickAlreadyRegistered();
                finish();
            }
        });

        validator = new Validator();
        validator.addEdit(tvUsername);
        validator.addEdit(tvEmail);
        validator.addEdit(tvPhone);
//        validator.addEdit(tvPassword);
//        validator.addEdit(tvPasswordConfirm);

        setBotBtn(R.id.signup_btn_register);
    }

    @Override
    protected void onResume(){
        super.onResume();

        checkInput(null);
    }

    private void httpRegister(final RequestRegister requestRegister){

        requestHttpSimple(false, R.string.progressdialog_register, true, null, true, new HttpCallbackInterfaceSimple<ResponseRegister>() {
            @Override
            public Call<ResponseRegister> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return ssoService.register(requestRegister);
            }

            @Override
            public void onSuccess(ResponseRegister response) {
                httpSignUpSuccess(requestRegister.email);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return true;
            }
        });


    }
    private void httpSignUpSuccess(String email){
        popNotifSuccessRegister( getString(R.string.signup_notif_successregister_title), getString(R.string.signup_notif_successregister_body, email) );
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
        finish();
    }

    private void popNotifSuccessRegister(String title, String body){

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AppConstant.NOTIF_CHANNEL_ID )
                .setSmallIcon(R.drawable.main_actionbar_logo)
                .setContentTitle( title )
                .setContentText( body )
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setDefaults(NotificationCompat.DEFAULT_SOUND|NotificationCompat.DEFAULT_LIGHTS|NotificationCompat.DEFAULT_VIBRATE|NotificationCompat.FLAG_AUTO_CANCEL)
                ;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(AppConstant.NTF_NORMAL, builder.build());

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notChannel = new NotificationChannel(AppConstant.NOTIF_CHANNEL_ID, title, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notChannel);
        }
    }

    @Override
    public boolean onSubmitValidate(BaseInput bi){

        boolean isValidPasswordConfirm = inpPassword.getValue(false).equals(inpPasswordConfirm.getValue(false));

        lblWarnPasswordIsSame.setText(isValidPasswordConfirm ? "" : "Password tidak sama");
        lblWarnPasswordIsSame.setVisibility(isValidPasswordConfirm ? View.GONE : View.VISIBLE);

        return isValidPasswordConfirm;
    }

    @Override
    public void save(){
        if( ! validator.check(this) ) return;

        String username = tvUsername.getText();
        String email = tvEmail.getText();
        String fullname = tvFullname.getText();
        String phone = tvPhone.getText();
//        String password = tvPassword.getText();
        String password = inpPassword.getValue(false);
//        String passwordConfirm = tvPasswordConfirm.getText();
        String passwordConfirm = inpPasswordConfirm.getValue(false);
        RequestRegister requestRegister = new RequestRegister(username, fullname, email, password, passwordConfirm, phone);
        httpRegister(requestRegister);

        // @test
//        httpSignUpSuccess();
    }

//    public void clickAlreadyRegistered(){
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }

    public static void startAct(Activity act){
        Intent intent = new Intent(act, SignUpActivity.class);
        act.startActivity(intent);
    }
}
