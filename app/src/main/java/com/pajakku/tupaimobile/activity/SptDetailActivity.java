package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppButton;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.component.AppTextView;
import com.pajakku.tupaimobile.component.SSPDetailRowInfo;
import com.pajakku.tupaimobile.fragment.Spt0SptTypeFragment;
import com.pajakku.tupaimobile.model.Spt;
import com.pajakku.tupaimobile.model.SptType;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.ApiHttpCallbackInterface;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.Utility;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class SptDetailActivity extends BaseActivity {

    private Spt spt;

    private RelativeLayout layoutSptStatus;
    private ImageView sptIcon;
    private AppTextView atvIcondesc;
    private SSPDetailRowInfo rowInfoSptType;
    private SSPDetailRowInfo rowInfoSptStatus;
    private SSPDetailRowInfo rowInfoYear;
    private SSPDetailRowInfo rowInfoNpwp;
    private SSPDetailRowInfo rowInfoWpname;

    private AppCircleButton appCircleButton;
    private AppButton botBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sptdetail);

        if (savedInstanceState == null) {
            Intent itn = getIntent();
            spt = (Spt) itn.getSerializableExtra(AppConstant.ITN_SPTDETAIL_SPT);
        } else {
            spt = (Spt) savedInstanceState.getSerializable(AppConstant.SP_SPTDETAIL_SPT);
        }

        AppActionBar appActionBar = findViewById(R.id.sptdetail_appactionbar);
        appActionBar.setBackFinish(this);

        layoutSptStatus = findViewById(R.id.sptdetail_layout_status);
        sptIcon = findViewById(R.id.sptdetail_spticon);
        atvIcondesc = findViewById(R.id.sptdetail_label_icondesc);
        rowInfoSptType = findViewById(R.id.sptdetail_rowinfo_spttype);
        rowInfoSptStatus = findViewById(R.id.sptdetail_rowinfo_statusspt);
        rowInfoYear = findViewById(R.id.sptdetail_rowinfo_year);
        rowInfoNpwp = findViewById(R.id.sptdetail_rowinfo_npwp);
        rowInfoWpname = findViewById(R.id.sptdetail_rowinfo_wpname);

        appCircleButton = findViewById(R.id.sptdetail_circlebtn_edit);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCircleBtn();
            }
        });

        botBtn = findViewById(R.id.sptdetail_btn_bot);
        botBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBotBtn();
            }
        });

//        setViewData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(AppConstant.SP_SPTDETAIL_SPT, spt);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case AppConstant.RP_WRITE_EXTERNAL_STORAGE: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request.
//        }
//    }

    @Override
    protected void setViewData() {

        SptType st = Spt0SptTypeFragment.fetchSingleSpttype(spt.sptTypeCode);

        if(spt.fetchStatusColor() != 0) layoutSptStatus.setBackgroundResource(spt.fetchStatusColor());
        if(spt.fetchStatusIconWhite() != 0) sptIcon.setImageResource(spt.fetchStatusIconWhite());
        atvIcondesc.setText(spt.fetchStatusDesc(this));
        if(st != null) rowInfoSptType.init( st.name );
        rowInfoSptStatus.init( spt.fetchPembetulan() );
        rowInfoYear.init( Integer.toString(spt.year) );
        rowInfoNpwp.init(Utility.toPrettyNpwp(spt.npwp) );
        rowInfoWpname.init(spt.wpName);

        switch (spt.fetchStatusIcon()){
            case R.drawable.spt_status_draft:
                appCircleButton.setVisibility(View.VISIBLE);
                botBtn.setVisibility(View.VISIBLE);
                break;
            case R.drawable.spt_status_submit:
            case R.drawable.spt_status_done:
            default:
                appCircleButton.setVisibility(View.GONE);
                botBtn.setVisibility(View.GONE);
        }
    }

    private void clickCircleBtn(){

    }

    public void clickBotBtn(){

        RequestParamConfig rpc = new RequestParamConfig();
        rpc.cacheKey = AppConstant.SP_CACHEKEY_REPORTSPT;
        rpc.isCache = false;
        rpc.progressTextRes = R.string.progressdialog_reportspt;
        rpc.isRelogin = true;
        apiRequestHttp(rpc, new ApiHttpCallbackInterface<ResponseBody, ResponseBody>() {
            @Override
            public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth) {
                return httpService.reportSPT(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseBody response) {

            }

            @Override
            public void onFail(ResponseError error) {

            }
        });
    }


}