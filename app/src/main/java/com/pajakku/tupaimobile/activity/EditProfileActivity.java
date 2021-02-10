package com.pajakku.tupaimobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.model.dto.ProfileUserCompany;
import com.pajakku.tupaimobile.model.dto.request.RequestRegister;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseRegister;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Validator;

import retrofit2.Call;

// TODO: fix
public class EditProfileActivity extends BaseActivity {

    private AppEditText inpUsername;
    private AppEditText inpFullname;
    private AppEditText inpEmail;
    private AppEditText inpPhone;
//    private AppEditText inpAddress;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppActionBar actionBar = findViewById(R.id.editprof_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.finish();
            }
        }, null);

        // TODO: fix
//        ImageView personIcon = findViewById(R.id.editprof_img_usericon);
//        personIcon.setImageResource(R.drawable.ic_person_none);

        if((inpUsername = findViewById(R.id.editprof_inp_username)) == null) throw new RuntimeException();
        if((inpFullname = findViewById(R.id.editprof_inp_name)) == null) throw new RuntimeException();
        inpFullname.constraintMandatory = true;
        if((inpEmail = findViewById(R.id.editprof_inp_email)) == null) throw new RuntimeException();
        inpEmail.constraintMandatory = true;
        if((inpPhone = findViewById(R.id.editprof_inp_phone)) == null) throw new RuntimeException();
        inpPhone.constraintMandatory = true;
//        if((inpAddress = findViewById(R.id.editprof_inp_address)) == null) throw new RuntimeException();

        Button btnSave = findViewById(R.id.editprof_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSave();
            }
        });

        validator = new Validator();
        validator.addEdit(inpFullname);
        validator.addEdit(inpEmail);
        validator.addEdit(inpPhone);

        init();
    }

    private void init(){
        ProfileUserCompany pc = getProfileUserCompany();

        inpUsername.setText(pc.username);
        inpFullname.setText(pc.fullname);
        inpEmail.setText(pc.email);
        inpPhone.setText(pc.phone);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    private void clickSave(){
        if( ! validator.check(this) ) return;

        final ProfileUserCompany pc = getProfileUserCompany();

        final RequestRegister rr = new RequestRegister();
        rr.name = inpFullname.getText();
        rr.email = inpEmail.getText();
        rr.mobilePhone = inpPhone.getText();

        requestHttpSimple(true, R.string.progressdialog_saveprofile, true, null, true, new HttpCallbackInterfaceSimple<ResponseRegister>() {
            @Override
            public Call<ResponseRegister> requestMethod(Http httpService, String bearerAuth, long companyId, long suscriptionId) {
                return httpService.editProfile(bearerAuth, pc.id, rr);
            }

            @Override
            public void onSuccess(ResponseRegister response) {
                successEditProfile();
            }

            @Override
            public boolean onFail(ResponseError re){
                return true;
            }
        });
    }

    private void successEditProfile(){
        removeCache(AppConstant.SP_CACHEKEY_ME, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {

            }
        });
        reloadProfile(new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                EditProfileActivity.this.finish();
            }
        });
    }

}
