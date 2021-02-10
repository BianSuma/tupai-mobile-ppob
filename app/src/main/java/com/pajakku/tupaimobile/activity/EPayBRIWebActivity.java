package com.pajakku.tupaimobile.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.util.AppConstant;

public class EPayBRIWebActivity extends AppCompatActivity {

    public static final String redirectStr = "http://redirect";
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epaybri_web);

        AppActionBar appActionBar = findViewById(R.id.epaybriweb_appactionbar);
        appActionBar.setBackFinish(this);
        appActionBar.setRightButton(R.drawable.ic_btn_find, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickZoom();
            }
        });

        Intent itn = getIntent();
        String url = itn.getStringExtra(AppConstant.ITN_PAYEPAYBRIWEB_URL);

        webView = findViewById(R.id.epaybriweb_webview);
        webView.setWebViewClient(new AppBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl( url );

        progressBar = findViewById(R.id.epaybriweb_progressbar);
    }

    private void clickZoom(){
        WebSettings ws = webView.getSettings();
        boolean b = ws.getLoadWithOverviewMode();

        ws.setLoadWithOverviewMode( ! b );
        ws.setUseWideViewPort( ! b );
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
            if(url.startsWith(redirectStr)) {
                webView.stopLoading();
                EPayBRIWebActivity.this.finish();
//            }else if(url.startsWith("https://sandbox.bri.co.id/staging/epay_aggregator_mpn/briPayment?tid=")){
//                Toast.makeText(EPayBRIWebActivity.this, "Pembayaran berhasil", Toast.LENGTH_SHORT).show();
//                webView.stopLoading();
//                EPayBRIWebActivity.this.finish();
            }else{
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url)
        {
//            if(url.equals("https://sandbox.bri.co.id/pg/ecommerce/ecommerce_payment")){
//                webView.stopLoading();
//                EPayBRIWebActivity.this.finish();
//            }

            return null;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            final SslErrorHandler h = handler;
            final AlertDialog.Builder builder = new AlertDialog.Builder(EPayBRIWebActivity.this);
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
}
