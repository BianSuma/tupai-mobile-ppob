package com.pajakku.tupaimobile.util;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.activity.LoginActivity;
import com.pajakku.tupaimobile.api.ApiCallBilling;
import com.pajakku.tupaimobile.api.ApiCallMpnPajakku;
import com.pajakku.tupaimobile.api.ApiCallTest;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.component.AppProgressDialog;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.tester.AppTester;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Created by dul on 31/12/18.
 */

public final class HttpReq {
    public static final String PARAM_CODE = "code";

    private static AccountManager accountManager;
    private static OkHttpClient okHttpClient;

    private static long lastOpenLogin;

    private static Http urlSsoBdgApiCallMain;
    private static Http apiMain;
    private static ApiCallBilling urlPrivateBilling;
    private static Http urlApiBdgApiCallMain;
    private static ApiCallMpnPajakku apiCallMpnPajakku;

    private static ApiCallTest urlApiBdgApiCallTest;

    public static void appCallNull(){

        urlSsoBdgApiCallMain = null;
        apiMain = null;
        urlPrivateBilling = null;
        urlApiBdgApiCallMain = null;
        apiCallMpnPajakku = null;

        urlApiBdgApiCallTest = null;

        HttpReq.okHttpClient = null;
    }

//    public static AccountManager getInstanceAM(Context ctx){
//        if(accountManager == null) accountManager = AccountManager.get( ((Activity)ctx).getBaseContext() );
//        return accountManager;
//    }

    private static OkHttpClient getInstanceHttpClient(Context ctx, String baseUrl){
        if(okHttpClient == null) {

            SSLSocketFactory sslSocketFactory = null;
            ConnectionSpec spec = null;

            // handle https request (SSL)
            if( baseUrl.startsWith("https") ) {
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                SSLContext sslContext;
                try {
                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                    sslSocketFactory = sslContext.getSocketFactory();

                    spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                            .cipherSuites(
                                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                                    CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                                    CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                                    CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                            .build();
                } catch (Exception ex) {
//                    SharePref.trackLog(ctx, AppConstant.LT_SSL_CONTEXT, ex.getMessage());
                    Utility.log(ex.getMessage());

                    sslSocketFactory = null;
                    spec = null;
                }
            }

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .readTimeout(10, TimeUnit.SECONDS)
//                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    ;

            // @prod
            if(sslSocketFactory != null){
                builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                })
                        .connectionSpecs(Collections.singletonList(spec))
                        .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                        })
                        ;
            }

            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    // ----------------- API CALL

    public static Http urlSsoBdgApiCallMain(Context ctx){
        if(urlSsoBdgApiCallMain == null) {
            String baseUrl = AppConf.urlSso();

            Retrofit retrofit = new Retrofit.Builder()
                    .client( getInstanceHttpClient(ctx, baseUrl) )
                    .baseUrl( baseUrl )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            urlSsoBdgApiCallMain = retrofit.create(Http.class);
        }
        return urlSsoBdgApiCallMain;
    }

    public static Http getApiMain(Context ctx){
        if(apiMain == null) {
            String baseUrl = AppConf.urlPrivate();

            Retrofit retrofit = new Retrofit.Builder()
                    .client( getInstanceHttpClient(ctx, baseUrl) )
                    .baseUrl( baseUrl )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiMain = retrofit.create(Http.class);
        }
        return apiMain;
    }

    public static ApiCallBilling getUrlPrivateBilling(Context ctx){
        if(urlPrivateBilling == null) {
            String baseUrl = AppConf.urlPrivate();

            Retrofit retrofit = new Retrofit.Builder()
                    .client( getInstanceHttpClient(ctx, baseUrl) )
                    .baseUrl( baseUrl )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            urlPrivateBilling = retrofit.create(ApiCallBilling.class);
        }
        return urlPrivateBilling;
    }

    public static Http getUrlApiBdgApiCallMain(Context ctx){
        if(urlApiBdgApiCallMain == null) {
            String baseUrl = AppConf.urlPrivate();

            Retrofit retrofit = new Retrofit.Builder()
                    .client( getInstanceHttpClient(ctx, baseUrl) )
                    .baseUrl( baseUrl )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            urlApiBdgApiCallMain = retrofit.create(Http.class);
        }
        return urlApiBdgApiCallMain;
    }

    public static ApiCallTest getUrlApiBdgApiCallTest(Context ctx){
        if(urlApiBdgApiCallTest == null) {
            String baseUrl = AppConf.urlPrivate();

            Retrofit retrofit = new Retrofit.Builder()
                    .client( getInstanceHttpClient(ctx, baseUrl) )
                    .baseUrl( baseUrl )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            urlApiBdgApiCallTest = retrofit.create(ApiCallTest.class);
        }
        return urlApiBdgApiCallTest;
    }

    public static ApiCallMpnPajakku getApiCallMpnPajakku(Context ctx){
        if(apiCallMpnPajakku == null) {
            String baseUrl = AppConf.urlPrivate();

            Retrofit retrofit = new Retrofit.Builder()
                    .client( getInstanceHttpClient(ctx, baseUrl) )
                    .baseUrl( baseUrl )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiCallMpnPajakku = retrofit.create(ApiCallMpnPajakku.class);
        }
        return apiCallMpnPajakku;
    }

    // ------------------ API ACCOUNT

//    public static Account getAccount(Activity ctx){
//        if( ActivityCompat.checkSelfPermission(ctx, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(ctx, new String[]{android.Manifest.permission.GET_ACCOUNTS}, AppConstant.RP_GET_ACCOUNT);
//            return null;
//        }
//
//        String username = SharePref.getStr(ctx, AppConstant.SP_USERNAME);
//
//        Account[] accounts = getInstanceAM(ctx).getAccountsByType(ctx.getString(R.string.key_account_type));
//        for(Account a : accounts){
//            if(a.name.equals(username)){
//                return a;
//            }
//        }
//        return null;
//    }

    public static void getAuthToken(final Context ctx, boolean showLoginForm, RequestParamConfig rpc, final SuccessFailCallback<String, ResponseDTO> callback){
        Activity act = (Activity)ctx;
        long currMs = System.currentTimeMillis();

//        final Account account = getAccount(act);
//        if(account == null){
//            if(showLoginForm && currMs-2000 > lastOpenLogin) {
//                lastOpenLogin = currMs;
//                getInstanceAM(act).addAccount(ctx.getString(R.string.key_account_type), AppConstant.TOKEN_TYPE, null, new Bundle(), act, null, null);
//            }
//            return;
//        }

        long authTokenExpire = SharePref.getLong(ctx, AppConstant.SP_AUTHTOKENEXPIRE);

//        String authToken = getInstanceAM(act).peekAuthToken(account, AppConstant.TOKEN_TYPE);
        String authToken = SharePref.getStr(ctx, AppConstant.SP_AUTH_TOKEN);
        if( authTokenExpire > currMs ) {
            if (authToken != null) {
                if(callback!=null) callback.onSuccess( Utility.getBearerSpaceAuth(authToken) );
                return;
            }
        }

        if(!AppConstant.AUTO_RELOGIN){
            SharedPreferences.Editor editor = SharePref.getInstance(ctx).edit();
            editor.remove(AppConstant.SP_AUTHTOKENEXPIRE);
            editor.remove(AppConstant.SP_PASSWORD_AES_KEY);
            editor.remove(AppConstant.SP_AUTH_TOKEN);
            editor.commit();

//            AccountManager am = HttpReq.getInstanceAM(ctx);
//            am.clearPassword(account);
            showLoginForm = true;
        }

        if( ! rpc.isRelogin ) {
            Log.d(AppConstant.LOG_TAG, "Attempt to relogin but relogin = false");
            ResponseDTO re = new ResponseDTO();
            callback.onFail(re);
            return;
        }

        if( SharePref.getStr(ctx, AppConstant.SP_PASSWORD_AES_KEY).length() > 0 ){
//            ApiAccount.relogin(ctx, account, rpc, new SuccessFailCallback<String, ResponseDTO>() {
//                @Override
//                public void onSuccess(String bearerAuthToken) {
//                    if(callback!=null) callback.onSuccess(bearerAuthToken);
//                }
//                @Override
//                public void onFail(ResponseDTO re){
//                    if(callback != null) callback.onFail(re);
//
//                    Utility.toast(ctx, re.msg);
//
//                }
//            });
            return;
        }

//        getInstanceAM(ctx).invalidateAuthToken(ctx.getString(R.string.global_package_name), authToken);
//
//        if(showLoginForm && currMs-2000 > lastOpenLogin){
//            lastOpenLogin = currMs;
//            getInstanceAM(ctx).getAuthToken(account, AppConstant.TOKEN_TYPE, new Bundle(), act, null, null);
//        }

        LoginActivity.startAct(act);
    }

    public static <T,U> void run(final Context ctx, final String rk, final RequestParamConfig rpc, final HttpCacheCallback<T, U> cb)
    {
        if(rpc.isCache && rpc.keepdataDuration == 0) Utility.log(rk+" Warning, cache true and keep data duration is ZERO");
        rpc.requestKey = rk;

        if(ctx instanceof BaseActivity){
//            rpc.baseActivity = (BaseActivity)ctx;
//            rpc.appActionBar = rpc.baseActivity.appActionBar;
        }

        if( ! rpc.forceRequest ) {
            long ms = SharePref.getLong(ctx, rk);
            if(ms > System.currentTimeMillis()) {
                rpc.stopLoading();
                rpc.hideReloader();
                cb.getCache(new CommonCallback<U>() {
                    @Override
                    public void onSuccess(U data) {
//                        if(rpc.baseActivity != null && rpc.baseActivity.isAlive()) cb.onSuccess(data);
                        cb.onSuccess(data);
                    }
                });
                return;
            }
        }

        // @test
        if( AppTester.request(ctx, rk, new SuccessFailCallback<T, ResponseDTO>() {
            @Override
            public void onSuccess(T data) {
                rpc.stopLoading();
                rpc.hideReloader();
                U d = cb.apiConvertResponse(data);

                if(rpc.isCache) {
                    cb.setCache(d);
                    SharePref.setLong(ctx, rk, System.currentTimeMillis() + rpc.keepdataDuration);
                }

//                if(rpc.baseActivity != null && rpc.baseActivity.isAlive()) cb.onSuccess(d);
                cb.onSuccess(d);
            }
            @Override
            public void onFail(ResponseDTO data) {
                rpc.stopLoading();
                rpc.showReloader();
                Utility.toast(ctx, "Http request fail intentionally.");
            }
        }) ) {
            return;
        }

        if(!rpc.withAuth){
            httpRequestProceed(ctx,null, rpc, cb);
            return;
        }

        getAuthToken(ctx, false, rpc, new SuccessFailCallback<String, ResponseDTO>() {
            @Override
            public void onSuccess(String bearerAuth) {
                httpRequestProceed(ctx, bearerAuth, rpc, cb);
            }
            @Override
            public void onFail(ResponseDTO re){
                cb.onFail(re);
            }
        });
    }

    private static <T,U> void httpRequestProceed(final Context ctx, String bearerAuth, final RequestParamConfig rpc, final HttpCacheCallback<T,U> cb){

        final Call<T> call = cb.requestMethod(bearerAuth);
        if(rpc.progressTextRes != 0) {
            AppProgressDialog.show(ctx, rpc.progressTextRes, new AppProgressDialog.AppListener() {
                @Override
                public void onClickCancel() {
                    call.cancel();
                }
            });
        }
        rpc.startLoading();
        call.enqueue(new Callback<T>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<T> call, Response<T> response) {
                if(rpc.progressTextRes != 0) AppProgressDialog.hide();
                rpc.stopLoading();
                if( response.isSuccessful() ){
                    rpc.hideReloader();
                    U d = cb.apiConvertResponse(response.body());

                    if(rpc.isCache) {
                        cb.setCache(d);
                        SharePref.setLong(ctx, rpc.requestKey, System.currentTimeMillis() + rpc.keepdataDuration);
                    }

//                    if(rpc.baseActivity != null) if( rpc.baseActivity.isAlive() ) cb.onSuccess(d);
                    cb.onSuccess(d);
                }else{
                    rpc.showReloader();
//                    if( rpc.baseActivity != null && rpc.baseActivity.isAlive() ) {
//                        ResponseDTO re = handleResponseError(ctx, rpc.requestKey, response);
//                        cb.onFail(re);
//                        if( rpc.isShowFailToast ) {
//                            if( re.msg.contains("not found by the code") ) {  // Authorized information is not found by the code
//                                Utility.toast(ctx, "Terjadi penyesuaian sistem. Silahkan coba lagi.");
//                            }else{
//                                Utility.toast(ctx, re.msg);
//                                if(rpc.requestKey.equalsIgnoreCase(SharePref.SPRK_UPD_IAM_PROFILE)){
//                                    rpc.baseActivity.finish();
//                                }
//                            }
//                        }
//                    }
                    ResponseDTO re = handleResponseError(ctx, rpc.requestKey, response);
                    cb.onFail(re);
                    if( rpc.isShowFailToast ) {
                        if( re.messageNotNull().contains("not found by the code") ) {  // Authorized information is not found by the code
                            Utility.toast(ctx, "Terjadi penyesuaian sistem. Silahkan coba lagi.");
                        }else{
                            Utility.toast(ctx, re.messageNotNull());
//                            if(rpc.requestKey.equalsIgnoreCase(SharePref.SPRK_UPD_IAM_PROFILE)){
//                                rpc.baseActivity.finish();
//                            }
                        }
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<T> call, Throwable t) {
                Utility.log(rpc.requestKey+" "+t.getMessage());   // Multipart body must have at least one part.
                if(rpc.progressTextRes != 0) AppProgressDialog.hide();
                rpc.stopLoading();
                rpc.showReloader();
//                if( rpc.baseActivity != null && rpc.baseActivity.isAlive() ) {
//
//                    ResponseDTO re = new ResponseDTO();
//                    re.error_description = t.getMessage();
//                    cb.onFail(re);
//
//                    if(t.getMessage() != null) {
//                        if (t.getMessage().equals("Socket closed")) {
//                            return;
//                        }
//                    }
//
//                    if( rpc.isShowFailToast) {
//                        Utility.toast(ctx, ctx.getString(R.string.clbase_toast_couldnotbeaccess, AppConstant.BASE_URL ));
//                    }
//                }

                ResponseDTO re = new ResponseDTO();
                re.message = t.getMessage();
                cb.onFail(re);

                if(t.getMessage() != null) {
                    if (t.getMessage().equals("Socket closed")) {
                        return;
                    }
                }

                if( rpc.isShowFailToast) {
                    Utility.toast(ctx, ctx.getString(R.string.clbase_toast_couldnotbeaccess, AppConf.urlPrivate()));
                }
            }
        });
    }

    private static <T> ResponseDTO handleResponseError(Context ctx, String rk, Response<T> resp){
        String msg = "";
        ResponseDTO re = new ResponseDTO();
        try {
            ResponseBody eb = resp.errorBody();
            if(eb == null) throw new Exception();
            String ebStr = eb.string();

//            SharePref.trackLog(ctx, AppConstant.LT_ALL_API_ERROR_RESPONSE, rk+" "+resp.code()+" "+ebStr);
            Utility.log( rk+" "+resp.code()+" "+ebStr );

            re = Utility.gson().fromJson(ebStr, ResponseDTO.class);
            if(re == null) re = new ResponseDTO();
            if(re.kode != 0) {
                switch (re.kode){
//                    case 97:
//                        msg = "Data rincian\ntidak ditemukan di tahun arsip "+SharePref.yearGet(ctx);
//                        break;
                    default:
                        msg = re.kode + " - " +re.message;
                }
            }else if(re.error_description == null) {
                if (re.errorMessage == null){
                    JsonParser parser = new JsonParser();
                    JsonElement rootNode = parser.parse(ebStr);
                    if(rootNode == null) throw new Exception();
                    JsonObject jo = rootNode.getAsJsonObject();
                    if(jo == null) throw new Exception();
                    JsonElement node = jo.get("error");
                    if(node == null) throw new Exception();
                    jo = node.getAsJsonObject();
                    if(jo == null) throw new Exception();
                    msg = jo.toString();
                    msg = (msg.length() > 80 ? msg.substring(0, 80) : msg );
                }else{
                    if( re.errorMessage.id == null ){
                        throw new Exception();
                    }else{
                        if(re.errorMessage.id.length() > 0){
                            msg = re.errorMessage.id;
                        }else{
                            JsonParser parser = new JsonParser();
                            JsonElement rootNode = parser.parse(ebStr);
                            if(rootNode == null) throw new Exception();
                            JsonObject jo = rootNode.getAsJsonObject();
                            if(jo == null) throw new Exception();
                            JsonElement node = jo.get("error");
                            if(node == null) throw new Exception();
                            jo = node.getAsJsonObject();
                            if(jo == null) throw new Exception();
                            msg = jo.toString();
                            msg = (msg.length() > 80 ? msg.substring(0, 80) : msg );
                        }
                    }
                }
            }else{
                msg = re.error_description;
            }
        }catch (Exception e){
//            msg = ctx.getString(R.string.clbase_toast_failresponsecode, resp.code());
//            re = new ResponseDTO();
//            FieldErrorMessage fem = new FieldErrorMessage();
//            fem.id = msg;
//
//            re.error_description = msg;
//            re.errorMessage = fem;
        }

        String msgCodeHeader = null;
        if( !rk.equalsIgnoreCase(SharePref.SPRK_REVOKE) ) {
            String wwwAuth = resp.headers().get("WWW-Authenticate");
            if(wwwAuth == null) wwwAuth = "";
            if ( (resp.code() == 401) ||
                    (resp.code() == 400 && wwwAuth.contains("login kembali") ) ) {
                if (AppConstant.AUTO_RELOGIN) {
                    SharePref.setLong(ctx, AppConstant.SP_AUTHTOKENEXPIRE, 0);  // for auto login for next action
                    msgCodeHeader = ctx.getString(R.string.clbase_toast_accessnoauth);
                } else {
                    ((BaseActivity) ctx).logoutClearCache();
                    msgCodeHeader = "Waktu interaksi habis.\nSilahkan login ulang.";
                }
            }
        }

        if(re == null) re = new ResponseDTO();

        if(resp.code() == 502) re.message = "502 Bad Gateway";

        re.setNulled();

        if(msgCodeHeader != null) re.message = msgCodeHeader;

        return re;
    }

    private HttpReq(){}

}
