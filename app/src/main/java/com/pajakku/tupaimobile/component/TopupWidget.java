package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.activity.SendOTPActivity;
import com.pajakku.tupaimobile.activity.TopupWebActivity;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Utility;

import retrofit2.Call;

/**
 * Created by dul on 18/12/18.
 */

public class TopupWidget extends RelativeLayout {

    public static int MODE_MCASH_BELUM_ACTIVATION = 1;
    public static int MODE_MCASH_SUDAH_ACTIVATION = 2;
    public static int MODE_LOADING = 3;

    private int mode = MODE_MCASH_BELUM_ACTIVATION;

    private BaseActivity baseActivity;
    private TextView tvUserName;
    private TextView tvNominal;
    private TextView tvTupaiSaldo;
    public Button btnTopUp;
    private ImageButton btnReload;
    private TextView btnActivate;

    public TopupWidget(Context context, AttributeSet attrs){
        super(context, attrs);

        baseActivity = (BaseActivity)context;
        setBackgroundColor(Color.parseColor("#3d5afe"));

        OnClickListener clickListenerNominal = new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNominal();
            }
        };

        btnReload = new ImageButton(context, attrs);
        btnReload.setId(View.generateViewId());
        btnReload.setBackgroundResource(R.drawable.animation_refresh);
//        btnReload.setImageResource(R.drawable.ic_refresh);
        btnReload.setOnClickListener(clickListenerNominal);
        addView(btnReload);

        tvUserName = new TextView(context, attrs);
        tvUserName.setId(View.generateViewId());
        tvUserName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tvUserName.setTextColor(Color.parseColor("#ffffff"));
//        tvUserName.setOnClickListener(clickListenerNominal);
        addView(tvUserName);

        tvNominal = new TextView(context, attrs);
        tvNominal.setId(View.generateViewId());
        tvNominal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvNominal.setTextColor(Color.parseColor("#ffffff"));
        tvNominal.setTypeface(null, Typeface.BOLD);
//        tvNominal.setOnClickListener(clickListenerNominal);
        addView(tvNominal);

        tvTupaiSaldo = new TextView(context, attrs);
        tvTupaiSaldo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tvTupaiSaldo.setText(R.string.comp_topupwidget_label_saldo);
        tvTupaiSaldo.setTextColor(Color.parseColor("#ffffff"));
//        tvTupaiSaldo.setOnClickListener(clickListenerNominal);
        addView(tvTupaiSaldo);

        int padLeftRight = getResources().getDimensionPixelOffset(R.dimen.layout_content_pad);

        btnTopUp = new Button(context, attrs);
        btnTopUp.setId(View.generateViewId());
        btnTopUp.setBackgroundResource(R.drawable.bg_btn_orange);
        btnTopUp.setPadding(padLeftRight, btnTopUp.getPaddingTop(), padLeftRight, btnTopUp.getPaddingBottom());
        btnTopUp.setAllCaps(false);
        btnTopUp.setTextColor(Color.parseColor("#073a87"));
        btnTopUp.setText(R.string.comp_topupwidget_btn_topup);
        btnTopUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTopup();
            }
        });
        addView(btnTopUp);

        btnActivate = new TextView(context, attrs);
        btnActivate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activateMc();
            }
        });
        btnActivate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnActivate.setTextColor(Color.parseColor("#fbc02e"));
        btnActivate.setTypeface(null, Typeface.BOLD);
        btnActivate.setText(R.string.comp_topupwidget_btn_activatemcash);
        addView(btnActivate);

        // set layout
        LayoutParams relativeParam;

        int btnReloadSize = getResources().getDimensionPixelSize(R.dimen.topupreloadbtn_size);
        relativeParam = new LayoutParams(btnReloadSize, btnReloadSize);
        relativeParam.addRule(LEFT_OF, btnTopUp.getId());
        relativeParam.addRule(CENTER_VERTICAL);
        relativeParam.rightMargin = getResources().getDimensionPixelOffset(R.dimen.layout_content_pad);
        btnReload.setLayoutParams(relativeParam);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        relativeParam.leftMargin = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        tvUserName.setLayoutParams(relativeParam);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(BELOW, tvUserName.getId());
        relativeParam.addRule(ALIGN_LEFT, tvUserName.getId());
        tvNominal.setLayoutParams(relativeParam);

        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(BELOW, tvNominal.getId());
        relativeParam.addRule(ALIGN_LEFT, tvNominal.getId());
        tvTupaiSaldo.setLayoutParams(relativeParam);

        int btnSize = getResources().getDimensionPixelSize(R.dimen.button_height);
        relativeParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, btnSize);
        relativeParam.addRule(CENTER_VERTICAL);
        relativeParam.addRule(ALIGN_PARENT_RIGHT);
        btnTopUp.setLayoutParams(relativeParam);

        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.addRule(CENTER_VERTICAL);
        btnActivate.setLayoutParams(relativeParam);

        // ----

//        relativeParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
//        progressBar.setLayoutParams(relativeParam);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(GONE);
//        addView(progressBar);

        int pad = (int)getResources().getDimension(R.dimen.holo_shadow_pad);
        setPadding(pad, pad, pad, pad);

        setModeMcashBelumActivation();
    }

    public void setModeMcashBelumActivation(){
        mode = MODE_MCASH_BELUM_ACTIVATION;

        tvUserName.setVisibility(GONE);
        tvNominal.setVisibility(GONE);
        tvTupaiSaldo.setVisibility(GONE);

        btnActivate.setVisibility(VISIBLE);

        stopProgressBar();
    }

    public void setModeMcashSudahActivation(){
        mode = MODE_MCASH_SUDAH_ACTIVATION;

        tvUserName.setVisibility(VISIBLE);
        tvNominal.setVisibility(VISIBLE);
        tvTupaiSaldo.setVisibility(VISIBLE);

        btnActivate.setVisibility(GONE);

        stopProgressBar();
    }

    public void init(long saldo){
        tvNominal.setText(Utility.toMoney(true, saldo));
    }

    public void setUserName(String u){
        tvUserName.setText(u);
    }

    public void setSaldo(String m){
        if(m.length() <= 0){
            tvNominal.setText("----");
            return;
        }
        tvNominal.setText( Utility.toMoney(true, Long.parseLong(m)) );
    }

    public void startProgressBar(){
        if(btnReload == null) return;
        AnimationDrawable ad = (AnimationDrawable) btnReload.getBackground();
        ad.start();

        mode = MODE_LOADING;
    }
    public void stopProgressBar(){
        if(btnReload == null) return;
        AnimationDrawable ad = (AnimationDrawable) btnReload.getBackground();
        ad.stop();
    }

    private void clickNominal(){
        if(mode == MODE_LOADING){
            Toast.makeText(getContext(), getResources().getString(R.string.comp_topupwidget_toast_loading), Toast.LENGTH_LONG).show();
            return;
        }
        if(mode == MODE_MCASH_BELUM_ACTIVATION){
            activateMc();
            return;
        }

        startProgressBar();

        final TopupWidget iam = this;

        baseActivity.requestFinnet(0, AppConstant.SP_CACHEKEY_EMONBALANCE, true, true, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.mcBalance(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseFinnet response) {
                setSaldo(response.custBalance);
                iam.stopProgressBar();
            }

            @Override
            public boolean onFail(ResponseError error) {
                iam.stopProgressBar();
                return true;
            }
        });

    }

    private void activateMc(){
        if(mode == MODE_LOADING){
            Toast.makeText(getContext(), getResources().getString(R.string.comp_topupwidget_toast_loading), Toast.LENGTH_LONG).show();
            return;
        }
        BaseActivity act = (BaseActivity)getContext();
        act.activateMcConfirmation();
    }

    @Deprecated
    public void showOTPInput(ResponseFinnet responseFinnet){
        Intent intent = new Intent(baseActivity, SendOTPActivity.class);
        intent.putExtra(AppConstant.ITN_TOPUPSENDOTP_CUSTSTATUSCODE, responseFinnet.custStatusCode);
        baseActivity.startActivity(intent);
    }


    private void clickTopup(){
        if(mode == MODE_LOADING){
            Toast.makeText(getContext(), getResources().getString(R.string.comp_topupwidget_toast_loading), Toast.LENGTH_LONG).show();
            return;
        }
        if(mode == MODE_MCASH_BELUM_ACTIVATION){
            activateMc();
            return;
        }

        baseActivity.requestFinnet(R.string.progressdialog_accesstopup, null, true, true, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.getTopupLink(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseFinnet response) {
//                progressBar.setVisibility(GONE);
                showTopupWebview(response.widgetURL);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return true;
            }
        });

    }

    private void showTopupWebview(String widgetURL){
        Intent intent = new Intent(getContext(), TopupWebActivity.class);
        intent.putExtra(AppConstant.ITN_TOPUPWEBIVEW_URL, widgetURL);
        baseActivity.startActivityForResult(intent, AppConstant.ACTRES_TOP_UP);
    }


}
