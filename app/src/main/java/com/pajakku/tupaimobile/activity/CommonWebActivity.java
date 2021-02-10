package com.pajakku.tupaimobile.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.actdata.ActDataCommonWeb;
import com.pajakku.tupaimobile.model.actparam.ActParamCommonWeb;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Utility;

import java.util.Date;

import okhttp3.ResponseBody;

// @test
//import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class CommonWebActivity extends BaseActivity {
    public static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_COMMON_WEB;
    private ActDataCommonWeb actData;

    public final static String TESTITN_PAYSIMUL_BILLID  = "testItn1";
    public final static String TESTITN_PAYSIMUL_AMOUNT  = "testItn2";
    public final static String TESTITN_PAYSIMUL_TRAXID  = "testItn3";

    public static final String TEST_BASE_URL = "http://192.168.3.106:9000/";
//    public static final String TEST_BASE_URL = "http://epay-cms.bdg.pajakku.com:9000/";

    private WebView webView;
    private TestHttp testHttp;

    private String billId;
    private long amount;
    private String traxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epay_simulation);

        initData(savedInstanceState);

//        AppActionBar appActionBar = findViewById(R.id.epaysimulation_appactionbar);
//        appActionBar.setBackFinish(this);
//        appActionBar.setRightButton(R.drawable.ic_btn_find, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickZoom();
//            }
//        });

        webView = findViewById(R.id.epaysimulation_webview);
        webView.setWebViewClient(new AppBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
//        webView.loadUrl("file:///android_asset/bri.html");
    }

    @Override
    protected void onResume() {
        super.onResume();

        webView.loadUrl("file:///android_asset/"+actData.ap.fileHtml);
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
            ActParamCommonWeb ap = (ActParamCommonWeb) itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataCommonWeb();
            actData.ap = ap;
        }else{
            actData = (ActDataCommonWeb) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    private void clickZoom(){
        WebSettings ws = webView.getSettings();
        boolean b = ws.getLoadWithOverviewMode();

        ws.setLoadWithOverviewMode( ! b );
        ws.setUseWideViewPort( ! b );
    }


    public static void startAct(Activity act, int actRes, String htmlFile){
        ActParamCommonWeb ap = new ActParamCommonWeb();
        ap.fileHtml = htmlFile;

        Intent i = new Intent(act, CommonWebActivity.class);
        i.putExtra(ACTDATA_KEY, ap);
        act.startActivityForResult(i, actRes);
    }

    private class AppBrowser extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        // TODO: @needtest
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            if(url.contains(EPayBRIWebActivity.redirectStr)){
                webView.stopLoading();

                final FlagMerchantDTO flag = new FlagMerchantDTO();
                flag.id_billing = billId;
//                flag.merchant_id = AppConstant.MERCHANT_ID;
                flag.amount = amount;
                flag.bill_reference_no = Long.toString(System.currentTimeMillis());
                flag.ntb = flag.bill_reference_no;
                flag.ntpn = flag.bill_reference_no;
                flag.status = "S";
                flag.transaction_date = Utility.dateToStringNoT(new Date());
                flag.id_transaction = traxId;

                requestHttpSimple(false, 0, true, null, false, new HttpCallbackInterfaceSimple<ResponseBody>() {
                    @Override
                    public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                        return testHttp.flaggingMerchat(flag);
                    }

                    @Override
                    public void onSuccess(ResponseBody response) {
                        CommonWebActivity.this.finish();
                    }

                    @Override
                    public boolean onFail(ResponseError error) {
                        CommonWebActivity.this.finish();
                        return false;
                    }
                });

            }else{
//                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            final SslErrorHandler h = handler;
            final AlertDialog.Builder builder = new AlertDialog.Builder(CommonWebActivity.this);
            builder.setMessage("Ada masalah sertifikat SSP untuk mengakses server E-Pay BRI");
            builder.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    h.proceed();
                }
            });
            builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    h.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private class FlagMerchantDTO {
        public String id_billing;
        public String merchant_id;
        public Long amount;
        public String bill_reference_no;
        public String ntb;
        public String ntpn;
        public String status;
        public String transaction_date;
        public String id_transaction;
    }

    private interface TestHttp {
        @POST("api/flagging")
        Call<ResponseBody> flaggingMerchat(@Body FlagMerchantDTO flag);
    }
}
