package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.Base64Utils;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ifc.OnWebBack;
import com.pajakku.tupaimobile.model.actdata.ActDataMpnPajakku;
import com.pajakku.tupaimobile.model.actparam.ActParamMpnPajakku;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ActRtnMpnPajakku;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.util.AppBrowser;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.Utility;

import java.util.HashMap;
import java.util.Map;


public class WebEregActivity extends AppCompatActivity implements OnWebBack {
    public static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_MPNPAJAKKU;

    public static final String HTTPQUERY_ACCESSTOKEN = "accessToken";
    public static final String HTTPQUERY_ANDROID_URI = "androidUri";
    public static final String HTTPQUERY_BILLCODE = "billingCode";
    public static final String HTTPQUERY_WIDGET_CLIENT_ID = "widgetClientId";

    public ActDataMpnPajakku actData;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_webholder);

        initData(savedInstanceState);

//        setTitle(R.id.mpnpajakku_appactionbar, "MPN Pajakku");
//        setBackFinish();
//        appActionBar.setTypeNoShadow();

        ProgressBar progressBar = findViewById(R.id.commonwebholder_progressbar);

        webView = findViewById(R.id.commonwebholder_webview);
        webView.setWebViewClient(new AppBrowser(this, webView, progressBar));
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
//        ws.setLoadWithOverviewMode(true);
//        ws.setUseWideViewPort(true);

//        ws.setBuiltInZoomControls(true);
//        ws.setSupportZoom(true);

//        webView.addJavascriptInterface(new WebAppInterface(this), "AndroidSobPajak");

        String basicAuth = "Basic "+Base64Utils.encode((actData.ap.appStatusData.mpnWidgetUser+":"+actData.ap.appStatusData.mpnWidgetPass).getBytes());

        Map<String,String> header = new HashMap<>();
        header.put("Authorization", basicAuth);

        webView.loadUrl( actData.ap.url, header );
//        webView.loadUrl( "file:///android_asset/communication_test.html" );

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent());
        super.onBackPressed();
    }

    @Override
    public void onWebBack(Uri uri) {
        // app://com.pajakku.tupaimobile?institutionCode=77777&vaNo=2254058450&xp1=35

        Intent itn = new Intent();
//        itn.putExtra(AppConstant.SP_ACTIVITYRESULT, actRtn);
        setResult(RESULT_OK, itn);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//
        outState.putSerializable(ACTDATA_KEY, actData);
    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
            Intent itn = getIntent();
            ActParamMpnPajakku ap = (ActParamMpnPajakku) itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataMpnPajakku();
            actData.ap = ap;

        }else{
            actData = (ActDataMpnPajakku) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }


    public static void startAct(Activity act, String url, int reqCode, AppStatusData appStatusData){
        ActParamMpnPajakku ap = new ActParamMpnPajakku();
        ap.url = url;
        ap.appStatusData = appStatusData;

        Intent itn = new Intent(act, WebEregActivity.class);
        itn.putExtra(ACTDATA_KEY, ap);
        act.startActivityForResult(itn, reqCode);
    }
}
