package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.Validator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class AddEditWpActivity extends RepositoryActivity {
    public static final int MODE_ADD = 1;
    public static final int MODE_EDIT = 2;

    private AppActionBar appActionBar;

    private AppEditText inpWpNpwp;
    private AppEditText inpWpName;
    private AppEditText inpWpAddress;
    private AppEditText inpWpCity;
//    private AppEditText inpWpPostalCode;
//    private AppEditText inpWpPhone;
//    private AppEditText inpWpEmail;
//    private AppEditText inpWpPICName;
//    private AppEditText inpWpPICEmail;
//    private AppEditText inpWpPICPosition;
//    private AppEditText inpWpPICPhone;

    private Button btnSave;

    private int actionMode;
    private Wajibpajak wajibpajak;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_wp);

        appActionBar = findViewById(R.id.addeditwp_actionbar);
        appActionBar.setBackFinish(this);

        if((inpWpNpwp = findViewById(R.id.addeditwp_inp_npwp)) == null) throw new RuntimeException();
        inpWpNpwp.constraintMandatory = true;
        inpWpNpwp.setConstraintNPWP();
        if((inpWpName = findViewById(R.id.addeditwp_inp_wpname)) == null) throw new RuntimeException();
        inpWpName.constraintMandatory = true;
        if((inpWpAddress = findViewById(R.id.addeditwp_inp_address)) == null) throw new RuntimeException();
        inpWpAddress.constraintMandatory = true;
        if((inpWpCity = findViewById(R.id.addeditwp_inp_city)) == null) throw new RuntimeException();
        inpWpCity.constraintMandatory = true;
//        if((inpWpPostalCode = findViewById(R.id.addeditwp_inp_postalcode)) == null) throw new RuntimeException();
//        inpWpPostalCode.constraintMandatory = true;
//        if((inpWpPhone = findViewById(R.id.addeditwp_inp_phone)) == null) throw new RuntimeException();
//        if((inpWpEmail = findViewById(R.id.addeditwp_inp_email)) == null) throw new RuntimeException();
//        if((inpWpPICName = findViewById(R.id.addeditwp_inp_picname)) == null) throw new RuntimeException();
//        inpWpPICName.constraintMandatory = true;
//        if((inpWpPICEmail = findViewById(R.id.addeditwp_inp_picemail)) == null) throw new RuntimeException();
//        if((inpWpPICPosition = findViewById(R.id.addeditwp_inp_picposition)) == null) throw new RuntimeException();
//        inpWpPICPosition.constraintMandatory = true;
//        if((inpWpPICPhone = findViewById(R.id.addeditwp_inp_picphone)) == null) throw new RuntimeException();
//        inpWpPICPhone.constraintMandatory = true;

        // TODO: @test
//        inpWpNpwp.setText("668874407406000");
//        inpWpName.setText("Test User");
//        inpWpAddress.setText("Jln. Cigadung 6B");
//        inpWpCity.setText("Bandung");
//        inpWpPostalCode.setText("12345");
//        inpWpPhone.setText("88920192811");
//        inpWpEmail.setText("user@site.com");
//        inpWpPICName.setText("Test PIC");
//        inpWpPICEmail.setText("pic@site.com");
//        inpWpPICPosition.setText("Tester");
//        inpWpPICPhone.setText("89810108690");

//        Button btnInquiry = findViewById(R.id.addeditwp_btn_inquiry);
//        btnInquiry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickInquiryWP();
//            }
//        });

        btnSave = findViewById(R.id.addeditwp_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSave();
            }
        });

        validator = new Validator();
        validator.addEdit(inpWpNpwp);
        validator.addEdit(inpWpName);
        validator.addEdit(inpWpAddress);
        validator.addEdit(inpWpCity);
//        validator.addEdit(inpWpPostalCode);
//        validator.addEdit(inpWpPhone);
//        validator.addEdit(inpWpEmail);
//        validator.addEdit(inpWpPICName);
//        validator.addEdit(inpWpPICEmail);
//        validator.addEdit(inpWpPICPosition);
//        validator.addEdit(inpWpPICPhone);

        init();

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void init(){
        Intent itn = getIntent();
        actionMode = itn.getIntExtra(AppConstant.ITN_XADDEDITWP_ACTIONMODE, MODE_ADD);

        appActionBar.setRightMenu( new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                if(actionMode == MODE_ADD) return R.menu.addeditwp_toprightmenu;
                return R.menu.addeditwp_withdel;
            }

            @Override
            public void onClickRightMenu(int id) {
                clickRightMenu(id);
            }
        } );


        if(actionMode == MODE_ADD) return;

        wajibpajak = (Wajibpajak) itn.getSerializableExtra(AppConstant.ITN_XADDEDITWP_ORGANIZATION);

        inpWpNpwp.setText(wajibpajak.npwp);
        inpWpName.setText(wajibpajak.name);
        inpWpAddress.setText(wajibpajak.address);
        inpWpCity.setText(wajibpajak.city);
//        inpWpPostalCode.setText(wajibpajak.postalCode);
//        inpWpPhone.setText(wajibpajak.phone);
//        inpWpEmail.setText(wajibpajak.email);
//        inpWpPICName.setText(wajibpajak.picName);
//        inpWpPICEmail.setText(wajibpajak.picEmail);
//        inpWpPICPosition.setText(wajibpajak.picPosition);
//        inpWpPICPhone.setText(wajibpajak.picPhone);
    }

    private void clickRightMenu(int id){
        switch (id){
//            case R.id.addeditwp_btnmenu_ntpn:
//                clickRightDataNTPN();
//                break;
            case R.id.addeditwp_btnmenu_quickhelp:
                clickQuickHelp();
                break;
            case R.id.addeditwp_btnmenu_del:
                clickRightDel();
                break;
        }
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpwp), getString(R.string.addeditwp_qhelpbody_inpwp), (View)inpWpNpwp.getParent(), inpWpNpwp);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpname), getString(R.string.addeditwp_qhelpbody_inpname), (View)inpWpNpwp.getParent(), inpWpName);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpaddress), getString(R.string.addeditwp_qhelpbody_inpaddress), (View)inpWpAddress.getParent(), inpWpAddress);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_inpcity), getString(R.string.addeditwp_qhelpbody_inpcity), (View)inpWpNpwp.getParent(), inpWpCity);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(this, getString(R.string.addeditwp_qhelptitle_save), getString(R.string.addeditwp_qhelpbody_save), (View)btnSave.getParent(), btnSave);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    private void clickInquiryWP(){
        final Wajibpajak org = setRequestBody();
        this.requestHttpSimple(true, R.string.progressdialog_inquirywp, true, null, true, new HttpCallbackInterfaceSimple<Wajibpajak>() {
            @Override
            public Call<Wajibpajak> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId){
                return httpService.inquiryWP(bearerAuth, companyId, org);
            }

            @Override
            public void onSuccess(Wajibpajak response){
            }

            @Override
            public boolean onFail(ResponseError re){
                return true;
            }
        });
    }


    private void clickRightDataNTPN(){
        Intent intent = new Intent(this, WPListNPTNActivity.class);
        startActivity(intent);
    }

    private void clickRightDel(){
        Utility.showConfirmationDialog(this, R.string.addeditwp_label_delorganization, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                deleteOrganization();
            }
        });
    }

    private void deleteOrganization(){
        if(wajibpajak == null) return;

        RequestParamConfig rpc = new RequestParamConfig();
        rpc.forceRequest = true;
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_delorganization;
        ApiMain.wpDel(this, rpc, wajibpajak.id, new CommonCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {
                successAction();
            }
        });
//        this.requestHttpSimple(true, R.string.progressdialog_delorganization, true, null, true, new HttpCallbackInterfaceSimple<ResponseBody>() {
//            @Override
//            public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId){
//                return httpService.deleteOrganization(bearerAuth, wajibpajak.id);
//            }
//
//            @Override
//            public void onSuccess(ResponseBody response){
//                successAction();
//            }
//
//            @Override
//            public boolean onFail(ResponseError re){
//                return true;
//            }
//        });
    }

    private void clickSave(){
        if( !validator.check(this) ) return;

        if(actionMode == MODE_ADD){
            createNewOrganization();
        }else{
            editOrganization();
        }
    }

    private Wajibpajak setRequestBody(){
        Wajibpajak org = new Wajibpajak();
        org.id = (System.currentTimeMillis()/1000);
        org.npwp = inpWpNpwp.getOnlyNumber();
        org.name = inpWpName.getText();
        org.address = inpWpAddress.getText();
        org.city = inpWpCity.getText();
//        org.postalCode = inpWpPostalCode.getText();
//        org.phone = inpWpPhone.getText();
//        org.email = inpWpEmail.getText();
//        org.picName = inpWpPICName.getText();
//        org.picEmail = inpWpPICEmail.getText();
//        org.picPosition = inpWpPICPosition.getText();
//        org.picPhone = inpWpPICPhone.getText();
        return org;
    }

    private void createNewOrganization(){
        final Wajibpajak org = setRequestBody();

        ApiMain.wpAdd(this, new RequestParamConfig(), org, new CommonCallback<Wajibpajak>() {
            @Override
            public void onSuccess(Wajibpajak data) {
                successAction();
            }
        });

//        this.requestHttpSimple(true, R.string.progressdialog_savewp, true, null, true,
//                new HttpCallbackInterfaceSimple<Wajibpajak>() {
//            @Override
//            public Call<Wajibpajak> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId){
//                return httpService.saveWP(bearerAuth, org);
//            }
//
//            @Override
//            public void onSuccess(Wajibpajak response){
//                successAction();
//            }
//
//            @Override
//            public boolean onFail(ResponseError re){
//                return true;
//            }
//        });
    }

    private void editOrganization(){
        if(wajibpajak == null) return;
        final Wajibpajak updated = setRequestBody();
        updated.id = wajibpajak.id;

        ApiMain.wpEdit(this, new RequestParamConfig(), updated, new CommonCallback<Wajibpajak>() {
            @Override
            public void onSuccess(Wajibpajak data) {
                successAction();
            }
        });

//        this.requestHttpSimple(true, R.string.progressdialog_savewp, true, null,
//                true, new HttpCallbackInterfaceSimple<Wajibpajak>() {
//            @Override
//            public Call<Wajibpajak> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId){
//                return httpService.editOrganization(bearerAuth, wajibpajak.id, updated);
//            }
//
//            @Override
//            public void onSuccess(Wajibpajak response){
//                successAction();
//            }
//
//            @Override
//            public boolean onFail(ResponseError re){
//                return true;
//            }
//        });
    }

    private void successAction()
    {
        removeCache(AppConstant.SP_CACHEKEY_WPLIST, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                AddEditWpActivity.this.finish();
            }
        });
    }
}
