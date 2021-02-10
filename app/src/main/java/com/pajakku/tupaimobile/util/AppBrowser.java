package com.pajakku.tupaimobile.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.MPNPajakkuActivity;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.ifc.OnWebBack;

public class AppBrowser extends WebViewClient {

    private Activity activity;
    private WebView webView;
    private ProgressBar progressBar;

    public AppBrowser(Activity act, WebView webView, ProgressBar progressBar){
        this.activity = act;
        this.webView = webView;
        this.progressBar = progressBar;
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        Utility.log("shouldOverrideUrlLoading "+url);
//        view.loadUrl(url);
//        return true;
//    }
//
//    @TargetApi(Build.VERSION_CODES.N)
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        Utility.log("shouldOverrideUrlLoading request "+request.getRequestHeaders().toString());
//        view.loadUrl(request.getUrl().toString());
//        return true;
//    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon){
        Utility.log("onPageStarted "+url);
        String deepLinkCmd = activity.getString(R.string.deeplink_scheme) + AppConstant.DEEPLINK_SCHEME_SEPARATOR + activity.getString(R.string.deeplink_host);
        if(url.startsWith( deepLinkCmd )){
            if(activity instanceof MPNPajakkuActivity){
                webView.stopLoading();
                ((MPNPajakkuActivity)activity).backWithValue(url);
            }else if(activity instanceof OnWebBack){
                webView.stopLoading();
                ((OnWebBack)activity).onWebBack(Uri.parse(url));
            }
//            activity.onBackPressed();
        }else{
            progressBar.setVisibility(View.VISIBLE);
        }
//        progressBar.setVisibility(View.VISIBLE);
    }

//    @SuppressLint("NewApi")
//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//        Utility.log("shouldInterceptRequest "+request.getRequestHeaders());
//
//        return null;
//    }

//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, String url)
//    {
//        return null;
//    }

//    private void handleDeepLink(WebView wv, String url){
//        String deepLinkCmd = activity.getString(R.string.deeplink_scheme) + AppConstant.DEEPLINK_SCHEME_SEPARATOR + activity.getString(R.string.deeplink_host);
//        if(url.startsWith( deepLinkCmd )){
//            wv.stopLoading();
//            activity.onBackPressed();
//        }
//    }

    @Override
    public void onPageFinished(WebView wv, String url) {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        final SslErrorHandler h = handler;
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Ada masalah sertifikat SSL untuk mengakses server");
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
