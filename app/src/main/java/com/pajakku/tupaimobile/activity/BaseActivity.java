package com.pajakku.tupaimobile.activity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppProgressDialog;
import com.pajakku.tupaimobile.component.AppSpinner;
import com.pajakku.tupaimobile.component.AppViewPager;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpFile;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.component.RecyclerViewX;
import com.pajakku.tupaimobile.dao.AppDatabase;
import com.pajakku.tupaimobile.fragment.BaseFragment;
import com.pajakku.tupaimobile.model.Company;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.Transactionlog;
import com.pajakku.tupaimobile.model.dto.ClickItemListParam;
import com.pajakku.tupaimobile.model.dto.FieldErrorMessage;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.ProfileUserCompany;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.request.FirebaseToken;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseAccessToken;
import com.pajakku.tupaimobile.model.dto.response.ResponseAuthorize;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.model.dto.response.ResponseMe;
import com.pajakku.tupaimobile.model.spinitem.SpinItem;
import com.pajakku.tupaimobile.util.ApiHttpCallbackInterface;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.AppConf;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterface;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.SuccessFailCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

// @test
//import okhttp3.logging.HttpLoggingInterceptor;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Created by dul on 17/01/19.
 */

public class BaseActivity extends AppCompatActivity {

    private static SharedPreferences sharePref;
    private static Gson gson;
    private static Http httpService;
    public static Http ssoService;
    protected static AppDatabase appDatabase;
//    private static AccountManager accountManager;

    private static FirebaseInstanceId firebaseInstance;
    protected AppViewPager appViewPager;

//    private FCMBroadCastReceiver broadCastReceiver;

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE kjs add month1id INT;");
//            database.execSQL("ALTER TABLE kjs add month1active INT;");
//            database.execSQL("ALTER TABLE kjs add month2id INT;");
//            database.execSQL("ALTER TABLE kjs add month2active INT;");
//        }
//    };
//
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL( "CREATE TABLE sspunpaid_new (id INTEGER NOT NULL, name TEXT, npwp TEXT, address TEXT, amount INTEGER NOT NULL, id_billing TEXT, bill_num_expired TEXT, taxslipresponse_code TEXT, taxslipresponse_desc TEXT, id_billing_pajakku TEXT, status TEXT, month1 INTEGER NOT NULL, month2 INTEGER NOT NULL, year INTEGER NOT NULL, tax_type_code TEXT, tax_type_name TEXT, kjs_code TEXT, kjs_name TEXT, ntb TEXT, ntpn TEXT, updated_at TEXT, created_at TEXT, receipt_paydate TEXT, receipt_bookdate TEXT, receipt_provider TEXT, ref_no TEXT, npwp_penyetor TEXT, no_sk TEXT, nop TEXT, PRIMARY KEY(id));" );
//            database.execSQL("DROP TABLE sspunpaid;");
//            database.execSQL("ALTER TABLE sspunpaid_new RENAME TO sspunpaid;");
//
//            database.execSQL( "CREATE TABLE sspdone_new (id INTEGER NOT NULL, name TEXT, npwp TEXT, address TEXT, amount INTEGER NOT NULL, id_billing TEXT, bill_num_expired TEXT, taxslipresponse_code TEXT, taxslipresponse_desc TEXT, id_billing_pajakku TEXT, status TEXT, month1 INTEGER NOT NULL, month2 INTEGER NOT NULL, year INTEGER NOT NULL, tax_type_code TEXT, tax_type_name TEXT, kjs_code TEXT, kjs_name TEXT, ntb TEXT, ntpn TEXT, updated_at TEXT, created_at TEXT, receipt_paydate TEXT, receipt_bookdate TEXT, receipt_provider TEXT, ref_no TEXT, npwp_penyetor TEXT, no_sk TEXT, nop TEXT, PRIMARY KEY(id));" );
//            database.execSQL("DROP TABLE sspdone;");
//            database.execSQL("ALTER TABLE sspdone_new RENAME TO sspdone;");
//        }
//    };
//
//    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE sspunpaid ADD transaction_id INT;");
//            database.execSQL("ALTER TABLE sspdone ADD transaction_id INT;");
//        }
//    };
//
//    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL( "CREATE TABLE sspunpaid_new (id INTEGER NOT NULL, name TEXT, npwp TEXT, address TEXT, amount INTEGER NOT NULL, id_billing TEXT, bill_num_expired TEXT, taxslipresponse_code TEXT, taxslipresponse_desc TEXT, id_billing_pajakku TEXT, status TEXT, month1 INTEGER NOT NULL, month2 INTEGER NOT NULL, year INTEGER NOT NULL, tax_type_code TEXT, tax_type_name TEXT, kjs_code TEXT, kjs_name TEXT, trans_ref_id TEXT, ntb TEXT, ntpn TEXT, updated_at TEXT, created_at TEXT, receipt_paydate TEXT, receipt_bookdate TEXT, receipt_provider TEXT, ref_no TEXT, npwp_penyetor TEXT, no_sk TEXT, nop TEXT, PRIMARY KEY(id));" );
//            database.execSQL("DROP TABLE sspunpaid;");
//            database.execSQL("ALTER TABLE sspunpaid_new RENAME TO sspunpaid;");
//
//            database.execSQL( "CREATE TABLE sspdone_new (id INTEGER NOT NULL, name TEXT, npwp TEXT, address TEXT, amount INTEGER NOT NULL, id_billing TEXT, bill_num_expired TEXT, taxslipresponse_code TEXT, taxslipresponse_desc TEXT, id_billing_pajakku TEXT, status TEXT, month1 INTEGER NOT NULL, month2 INTEGER NOT NULL, year INTEGER NOT NULL, tax_type_code TEXT, tax_type_name TEXT, kjs_code TEXT, kjs_name TEXT, trans_ref_id TEXT, ntb TEXT, ntpn TEXT, updated_at TEXT, created_at TEXT, receipt_paydate TEXT, receipt_bookdate TEXT, receipt_provider TEXT, ref_no TEXT, npwp_penyetor TEXT, no_sk TEXT, nop TEXT, PRIMARY KEY(id));" );
//            database.execSQL("DROP TABLE sspdone;");
//            database.execSQL("ALTER TABLE sspdone_new RENAME TO sspdone;");
//        }
//    };

    private List<BaseInput> inputs;

    public AppCompatButton btnBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputs = new ArrayList<>();

//        broadCastReceiver = new FCMBroadCastReceiver();

        if(firebaseInstance == null){
            firebaseInstance = FirebaseInstanceId.getInstance();
        }

        if(sharePref == null){
            sharePref = getSharedPreferences(getString(R.string.global_package_name)+AppConstant.SHARE_PREF_KEY, Context.MODE_PRIVATE);
        }

        if(gson == null) gson = new Gson();

        if(httpService == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(AppConf.urlPrivate())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            httpService = retrofit.create(Http.class);

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(AppConf.urlSso())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ssoService = retrofit.create(Http.class);
        }

        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppConstant.DB_NAME)
//                    .addMigrations(MIGRATION_1_2)
//                    .addMigrations(MIGRATION_2_3)
//                    .addMigrations(MIGRATION_3_4)
//                    .addMigrations(MIGRATION_4_5)
                    .build();
        }

//        if(accountManager == null) accountManager = AccountManager.get(getBaseContext());

    }

    @Override
    protected void onResume(){
        super.onResume();

        if( ! (
                this instanceof LoginActivity ||
                        this instanceof SignUpActivity ||
                        this instanceof AboutActivity ||
                        this instanceof ResetPasswordActivity ||
                        this instanceof ActivationActivity ||
                        this instanceof DevActivity
                ) ) {
            long authTokenExpire = SharePref.getLong(this, AppConstant.SP_AUTHTOKENEXPIRE);
            String authToken = SharePref.getStr(this, AppConstant.SP_AUTH_TOKEN);
            if ( authTokenExpire <= System.currentTimeMillis() || authToken.isEmpty() ){
                LoginActivity.startAct(this);
            }
        }

        setViewData();
        checkInput(null);

        if(!AppConf.NO_DEV_MARK) {
            AppCompatTextView markView = findViewById(R.id.devmark_top_left);
            if(markView == null) {
                ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                markView = new AppCompatTextView(this);
                markView.setId(R.id.devmark_top_left);
                markView.setBackgroundColor(Color.BLUE);
                markView.setTextColor(Color.WHITE);
                markView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                addContentView(markView, param);
            }
            markView.setText((
                    AppConf.APP_BUILD_DATE+" "+ AppConf.urltypeStr()
                    +" "+(AppConf.VALIDATE ?"VAL":"NOVAL")
                    +" "+getClass().getSimpleName()
            ));
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        saveObj();
    }

    @Override
    protected void onStart(){
        super.onStart();
//        LocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiver, new IntentFilter(AppConstant.ITNFILTER_TUPAINOTIF));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) return;

        Serializable seri = null;
        if(data != null) seri = data.getSerializableExtra(AppConstant.SP_ACTIVITYRESULT);

        if(appViewPager != null){
            BaseFragment frag = (BaseFragment) appViewPager.getItem(appViewPager.getCurrentItem());
            frag.onActResultFrag(this, requestCode, seri);
        }else {

            Utility.proceedActivityResult(inputs, requestCode, seri, new CommonCallback<BaseInput>() {
                @Override
                public void onSuccess(BaseInput data) {
                    checkInput(data);
                }
            });

//            InpFile inpFile;
//            for (BaseInput i : inputs) {
//                if (i instanceof InpFile) {
//                    inpFile = ((InpFile) i);
//                    if (inpFile.actRes != requestCode) continue;
//                    inpFile.setValueUnchange((FileBrowserItem) data.getSerializableExtra(AppConstant.SP_ACTIVITYRESULT));
//                    checkInput(inpFile);
//                    break;
//                }
//            }
        }
    }

    // ------------- SET COMPONENT

    protected View setClickView(int id, View.OnClickListener l){
        View v = findViewById(id);
        v.setOnClickListener(l);
        return v;
    }

    public PackageInfo getPInfo(){
        PackageInfo pInfo;
        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            pInfo = new PackageInfo();
            pInfo.versionCode = 0;
            pInfo.versionName = "???";
        }
        return pInfo;
    }

    public int getVersionCode(){
        return getPInfo().versionCode;
    }

    public void setSpBool(String key){
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putBoolean(key, true);
        editor.commit();
    }
    public boolean getSpBool(String key){
        return sharePref.getBoolean(key, false);
    }
    public void setSpString(String key, String data){
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putString(key, data);
        editor.commit();
    }
    public String getSpString(String key){
        return sharePref.getString(key, "");
    }
    public void setSpLong(String key, long d){
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putLong(key, d);
        editor.commit();
    }
    public long getSpLong(String key){
        return sharePref.getLong(key, 0);
    }

    public void setSpStringSet(String key, Set<String> val){
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putStringSet(key, val);
        editor.commit();
    }
    public Set<String> getSpStringSet(String key){
        return sharePref.getStringSet(key, new HashSet<String>());
    }

//    public Account getAccount(){
//        if( ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.GET_ACCOUNTS}, AppConstant.RP_GET_ACCOUNT);
//            return null;
//        }
//
//        String username = sharePref.getString(AppConstant.SP_USERNAME, "");
//
//        Account[] accounts = accountManager.getAccountsByType(getString(R.string.key_account_type));
//        for(Account a : accounts){
//            if(a.name.equals(username)){
//                return a;
//            }
//        }
//        Toast.makeText(this, R.string.base_toast_noaccount, Toast.LENGTH_SHORT).show();
//        return null;
//    }

    public void getAuthToken(boolean isRelogin, boolean showLoginForm, final SuccessFailCallback<String,ResponseError> callback){
//        final Account account = getAccount();
//        if(account == null){
//
//            if(showLoginForm) accountManager.addAccount(getString(R.string.key_account_type), AppConstant.TOKEN_TYPE, null, new Bundle(), this, null, null);
//
//            return;
//        }

        long authTokenExpire = sharePref.getLong(AppConstant.SP_AUTHTOKENEXPIRE, 0);

//        String authToken = accountManager.peekAuthToken(account, AppConstant.TOKEN_TYPE);
        String authToken = SharePref.getStr(this, AppConstant.SP_AUTH_TOKEN);
        if( authTokenExpire > System.currentTimeMillis() ) {
            if (authToken != null) {
                if(callback!=null) callback.onSuccess( Utility.getBearerSpaceAuth(authToken) );
                return;
            }
        }

        if(!AppConstant.AUTO_RELOGIN){
            SharedPreferences.Editor editor = SharePref.getInstance(this).edit();
            editor.remove(AppConstant.SP_AUTHTOKENEXPIRE);
            editor.remove(AppConstant.SP_PASSWORD_AES_KEY);
            editor.remove(AppConstant.SP_AUTH_TOKEN);
            editor.commit();

//            AccountManager am = HttpReq.getInstanceAM(this);
//            am.clearPassword(account);
            showLoginForm = true;
        }

        if( ! isRelogin ) {
            ResponseError re = new ResponseError();
            re.error_description = "";
            callback.onFail(re);
            return;
        }

        if( getSpString(AppConstant.SP_PASSWORD_AES_KEY).length() > 0 ){
            Utility.toast(this, "Otentikasi gagal. Silahkan login ulang.");
//            requestReloginAutomatic(account, new SuccessFailCallback<String,ResponseError>() {
//                @Override
//                public void onSuccess(String bearerAuthToken) {
//                    if(callback!=null) callback.onSuccess(bearerAuthToken);
//                }
//                @Override
//                public boolean onFail(ResponseError re){
//                    if(callback != null) callback.onFail(re);
//
//                    String msg = getString(R.string.global_toast_failrelogin);
//                    if(re != null) msg = re.error_description;
//                    if(msg != null) Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
//
//                    return false;
//                }
//            });
            return;
        }

//        accountManager.invalidateAuthToken(getString(R.string.key_account_type), authToken);
//
//        if(showLoginForm) accountManager.getAuthToken(account, AppConstant.TOKEN_TYPE, new Bundle(), this, null, null);

        LoginActivity.startAct(this);
    }

    public void logoutClearCacheConfirm(){
        Utility.showConfirmationDialog(this, R.string.profhome_label_logoutmsg, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                logoutClearCache();
            }
        });
    }

    public void logoutClearCache(){
        SharedPreferences.Editor editor = sharePref.edit();
        editor.remove(AppConstant.SP_AUTHTOKENEXPIRE);
        editor.remove(AppConstant.SP_PASSWORD_AES_KEY);
        editor.remove(AppConstant.SP_AUTH_TOKEN);
        editor.remove(AppConstant.SP_RECENT_TAXTYPE);
        editor.remove(AppConstant.SP_RECENT_KJS);

//        Account account = getAccount();
//        if(account != null){
//            accountManager.setUserData(account, AppConstant.AUD_REFRESH_TOKEN, null);
//        }

        editor.commit();

        removeCache(AppConstant.SP_CACHEKEY_ME, true, null);
//        removeCache(AppConstant.SP_CACHEKEY_MCACTIVATE, true, null);
        removeCache(AppConstant.SP_CACHEKEY_EMONBALANCE, true, null);
        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, null);
        removeCache(AppConstant.SP_CACHEKEY_SSPDONE, true, null);
        removeCache(AppConstant.SP_CACHEKEY_STREAMKJS, true, null);
        removeCache(AppConstant.SP_CACHEKEY_TRANSLOG, true, null);
        removeCache(AppConstant.SP_CACHEKEY_WPLIST, true, null);
//        removeCache(AppConstant.SP_CACHEKEY_STATUSDATA, true, null);

        getAuthToken(true, true, null);
    }

    public void removeCache(final String cacheKey, boolean withData, final CommonCallback<Void> callback){

        final List<Byte> cacheCount = new ArrayList<>();

        SharedPreferences.Editor editor = sharePref.edit();
        editor.remove(cacheKey);

        if(withData){
            if(cacheKey.equals(AppConstant.SP_CACHEKEY_ME)) {
                editor.remove(AppConstant.SP_CACHEME_ID);
                editor.remove(AppConstant.SP_CACHEME_FULLNAME);
                editor.remove(AppConstant.SP_CACHEME_PHONE);
                editor.remove(AppConstant.SP_CACHEME_EMAIL);
                editor.remove(AppConstant.SP_CACHEME_COMPANY_ID);
                editor.remove(AppConstant.SP_CACHEME_COMPANY_NAME);
                editor.remove(AppConstant.SP_CACHEME_SUBSCRIPTION_ID);
            }else if(cacheKey.equals(AppConstant.SP_CACHEKEY_EMONBALANCE)){
                editor.remove(AppConstant.SP_CACHEEMONBALANCE_CUSTBALANCE);
                editor.remove(AppConstant.SP_CACHEEMONBALANCE_IS_MC_ACTIVE);
            }else if(cacheKey.equals(AppConstant.SP_CACHEKEY_FIRST)){
                editor.remove(AppConstant.SP_CACHESTATUSDATA_CLIENTVERSION);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_ISOUTDATEVERSION);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_LASTCLIENTVERSION);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUWIDGET);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUCLIENTID);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUHOST);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUBASE);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_MPNWIDGET_USER);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_MPNWIDGET_PASS);
                editor.remove(AppConstant.SP_CACHESTATUSDATA_API_EREGHOST);
            }else{
                cacheCount.add((byte)1);
            }
        }

        editor.commit();

        if(withData) {
            executeQueryProcessSuccess(new ListenerQuerySuccess<Void>() {
                @Override
                public Void onProcess() {
                    if (cacheKey.equals(AppConstant.SP_CACHEKEY_ME)) {
                        appDatabase.wajibpajakDao().deleteAll();
                    } else if (cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID)) {
                        appDatabase.sspunpaidDao().deleteAll();
                    } else if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE) ){
                        appDatabase.sspdoneDao().deleteAll();
                    } else if( cacheKey.equals(AppConstant.SP_CACHEKEY_STREAMKJS) ){
                        appDatabase.kjsDao().deleteAll();
                    } else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TRANSLOG) ){
                        appDatabase.transactionlogDao().deleteAll();
                    } else {
                        if(cacheCount.size() == 1) Log.d(AppConstant.LOG_TAG, "No removeCache handler");
                    }
                    return null;
                }

                @Override
                public void onSuccess(Void result) {
                    if( callback != null ) callback.onSuccess(null);
                }
            });
        }else{
            if( callback != null ) callback.onSuccess(null);
        }
    }


    private <T,U> U apiResponseConvert(String cacheKey, T data){
        if(cacheKey == null) return (U)data;

        if (cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID)) {
            return (U) Sspunpaid.getInstanceList((List<ResponseSsp>) data);
        }else if(cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE)){
            return (U) Sspdone.getInstanceListSspdone((List<ResponseSsp>) data);
        }else if ( cacheKey.equals(AppConstant.SP_CACHEKEY_FIRST) ) {
            String currVer = getPInfo().versionName;
            AppStatusData d = (AppStatusData) data;
//            d.isOutdateVersion = !d.clientVersion.equals(currVer);
//            d.lastClientVersion = currVer;
            d.setNulled(this);
            return (U)d;
        }

        return (U)data;
    }

    @Deprecated  // apiResponseConvert
    private <T,U> List<U> responseConvert(String cacheKey, List<T> data){
        if(cacheKey == null) return (List<U>)data;

        if (cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID)) {
            return (List<U>) Sspunpaid.getInstanceList((List<ResponseSsp>) data);
        }else if(cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE)){
            return (List<U>) Sspdone.getInstanceListSspdone((List<ResponseSsp>) data);
        }

        return (List<U>)data;
    }

    public boolean isCacheNotExpire(String cacheKey){
        if(cacheKey == null) return false;

        long ms = sharePref.getLong(cacheKey, 0);
        if(ms < System.currentTimeMillis()) return false;

        return true;
    }

    public  <T> void setCache(String cacheKey, final T data){
        if(cacheKey == null) return;

        SharedPreferences.Editor editor = sharePref.edit();

        if(cacheKey.equals(AppConstant.SP_CACHEKEY_ME)) {
            editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
            ResponseMe d = (ResponseMe) data;
            editor.putLong(AppConstant.SP_CACHEME_ID, d.id);
            editor.putString(AppConstant.SP_CACHEME_FULLNAME, d.name);
            editor.putString(AppConstant.SP_CACHEME_PHONE, d.getMobilePhone());
            editor.putString(AppConstant.SP_CACHEME_EMAIL, d.getEmail());

            Company com = null;
            if(d.company.size() > 0) {
                for (Company c : d.company) {
                    if (c.subscriptions.size() > 0) {
                        com = c;
                        break;
                    }
                }
            }
            if(com != null){
                editor.putLong(AppConstant.SP_CACHEME_COMPANY_ID, com.id);
                editor.putString(AppConstant.SP_CACHEME_COMPANY_NAME, com.name);
                editor.putLong(AppConstant.SP_CACHEME_SUBSCRIPTION_ID, com.subscriptions.get(0).id);
            }else{
                editor.remove(AppConstant.SP_CACHEME_COMPANY_ID);
                editor.remove(AppConstant.SP_CACHEME_COMPANY_NAME);
                editor.remove(AppConstant.SP_CACHEME_SUBSCRIPTION_ID);
            }
        }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_EMONBALANCE) ){
            editor.putLong(cacheKey, System.currentTimeMillis() + AppConstant.MS_KEEPDATA_20HOUR);
            ResponseFinnet d = (ResponseFinnet)data;
            editor.putString(AppConstant.SP_CACHEEMONBALANCE_CUSTBALANCE, (d.custBalance!=null?d.custBalance:"") );
            if(d.custBalance != null) {
                if(d.custBalance.length() > 0) editor.putBoolean(AppConstant.SP_CACHEEMONBALANCE_IS_MC_ACTIVE, true);
            }
        }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_FIRST) ){
            editor.putLong(cacheKey, System.currentTimeMillis() + AppConstant.MS_KEEPDATA_HOUR);
            AppStatusData d = (AppStatusData) data;
            editor.putString(AppConstant.SP_CACHESTATUSDATA_CLIENTVERSION, d.clientVersion);
            editor.putBoolean(AppConstant.SP_CACHESTATUSDATA_ISOUTDATEVERSION, d.isOutdateVersion );
            editor.putString(AppConstant.SP_CACHESTATUSDATA_LASTCLIENTVERSION, d.lastClientVersion);
        }

        editor.commit();

        queryDeleteAllAndSaveCallback(cacheKey, data, null);

    }

    @Deprecated  // getApiCache
    public  <T,U> boolean getCache(final String cacheKey, final HttpCallbackInterface<T,U> callback){
        if(cacheKey == null) return false;

        long ms = sharePref.getLong(cacheKey, 0);
        if(ms < System.currentTimeMillis()) return false;

        final List<Byte> cacheCount = new ArrayList<>();

        if(cacheKey.equals(AppConstant.SP_CACHEKEY_ME)) {
            ResponseMe d = new ResponseMe();
            d.id = sharePref.getLong(AppConstant.SP_CACHEME_ID, 0);
            d.name = sharePref.getString(AppConstant.SP_CACHEME_FULLNAME, "");
            d.setMobilePhone(sharePref.getString(AppConstant.SP_CACHEME_PHONE, ""));
            d.setEmail(sharePref.getString(AppConstant.SP_CACHEME_EMAIL, ""));
            if(callback != null) callback.onSuccess((T) d);
        }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_EMONBALANCE) ){
            ResponseFinnet d = new ResponseFinnet();
            d.custBalance = sharePref.getString(AppConstant.SP_CACHEEMONBALANCE_CUSTBALANCE, "");
            if(callback != null) callback.onSuccess((T) d);
        }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_FIRST) ){
            String currVer = getPInfo().versionName;
            AppStatusData d = new AppStatusData();
            d.clientVersion = sharePref.getString(AppConstant.SP_CACHESTATUSDATA_CLIENTVERSION, currVer);
            d.isOutdateVersion = sharePref.getBoolean(AppConstant.SP_CACHESTATUSDATA_ISOUTDATEVERSION, false);
            d.lastClientVersion = sharePref.getString(AppConstant.SP_CACHESTATUSDATA_LASTCLIENTVERSION, currVer);
            if(callback != null) callback.onSuccess((T) d);
        }else {
            cacheCount.add((byte)1);
        }

        executeQueryProcessSuccess(new ListenerQuerySuccess<List<U>>() {
            @Override
            public List<U> onProcess() {
                if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID) ) {
                    return (List<U>)appDatabase.sspunpaidDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE) ){
                    return (List<U>)appDatabase.sspdoneDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_STREAMKJS) ){
                    return (List<U>)appDatabase.kjsDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TAXTYPE) ){
                    return (List<U>)appDatabase.taxTypeDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TRANSLOG) ){
                    return (List<U>)appDatabase.transactionlogDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_WPLIST) ){
                    return (List<U>)appDatabase.wajibpajakDao().getAll();
                }

                if( cacheCount.size() == 1 ){
                    Log.d(AppConstant.LOG_TAG, "No getCache handler");
                }

                return null;
            }

            @Override
            public void onSuccess(List<U> result) {
                if(result != null && callback != null){
                    if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID) || cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE) || cacheKey.equals(AppConstant.SP_CACHEKEY_WPLIST) ){
                        callback.onSuccess((T)result);
                    }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_STREAMKJS) || cacheKey.equals(AppConstant.SP_CACHEKEY_TAXTYPE) || cacheKey.equals(AppConstant.SP_CACHEKEY_TRANSLOG) ) {
                        callback.onSuccessStream(result);
                    }
                }
            }
        });

        return true;
    }

    public  <T,U> boolean getApiCache(final String cacheKey, final ApiHttpCallbackInterface<T,U> callback){

        long ms = sharePref.getLong(cacheKey, 0);
        if(ms < System.currentTimeMillis()) return false;

        if(cacheKey.equals(AppConstant.SP_CACHEKEY_ME)) {
            ResponseMe d = new ResponseMe();
            d.id = sharePref.getLong(AppConstant.SP_CACHEME_ID, 0);
            d.name = sharePref.getString(AppConstant.SP_CACHEME_FULLNAME, "");
            d.setMobilePhone(sharePref.getString(AppConstant.SP_CACHEME_PHONE, ""));
            d.setEmail(sharePref.getString(AppConstant.SP_CACHEME_EMAIL, ""));
            if(callback != null) callback.onSuccess((U) d);
        }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_EMONBALANCE) ){
            ResponseFinnet d = new ResponseFinnet();
            d.custBalance = sharePref.getString(AppConstant.SP_CACHEEMONBALANCE_CUSTBALANCE, "");
            if(callback != null) callback.onSuccess((U) d);
        }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_FIRST) ){
            String currVer = getPInfo().versionName;
            AppStatusData d = new AppStatusData();
            d.clientVersion = sharePref.getString(AppConstant.SP_CACHESTATUSDATA_CLIENTVERSION, currVer);
            d.isOutdateVersion = sharePref.getBoolean(AppConstant.SP_CACHESTATUSDATA_ISOUTDATEVERSION, false);
            d.lastClientVersion = sharePref.getString(AppConstant.SP_CACHESTATUSDATA_LASTCLIENTVERSION, currVer);
            if(callback != null) callback.onSuccess((U) d);
        }

        executeQueryProcessSuccess(new ListenerQuerySuccess<U>() {
            @Override
            public U onProcess() {
                if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID) ) {
                    return (U)appDatabase.sspunpaidDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE) ){
                    return (U)appDatabase.sspdoneDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_STREAMKJS) ){
                    return (U)appDatabase.kjsDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TAXTYPE) ){
                    return (U)appDatabase.taxTypeDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TRANSLOG) ){
                    return (U)appDatabase.transactionlogDao().getAll();
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_WPLIST) ){
                    return (U)appDatabase.wajibpajakDao().getAll();
                }

                return null;
            }

            @Override
            public void onSuccess(U result) {
                if(result != null && callback != null){
                    callback.onSuccess(result);
                }
            }
        });

        return true;
    }

    public <U> boolean getDataCacheSingle(String cacheKey, final CommonCallback<U> listenerHttpResponse){
        return getCache(cacheKey, new HttpCallbackInterface<U, U>() {
            @Override
            public Call<U> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                // nothing
                return null;
            }

            @Override
            public Call<ResponseBody> requestMethodStream(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                // nothing
                return null;
            }

            @Override
            public void onSuccess(U response) {
                listenerHttpResponse.onSuccess(response);
            }

            @Override
            public void onSuccessStream(List<U> response) {
                // nothing
            }

            @Override
            public boolean onFail(ResponseError error) {
                // nothing
                return true;
            }
        });
    }

    public <T,U> void getDataCacheList(String cacheKey, final CommonCallback<T> listenerHttpResponse){
        getCache(cacheKey, new HttpCallbackInterface<U, U>() {
            @Override
            public Call<U> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                // nothing
                return null;
            }

            @Override
            public Call<ResponseBody> requestMethodStream(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                // nothing
                return null;
            }

            @Override
            public void onSuccess(U response) {
                // nothing
            }

            @Override
            public void onSuccessStream(List<U> response) {
                listenerHttpResponse.onSuccess((T)response);
            }

            @Override
            public boolean onFail(ResponseError error) {
                // nothing
                return true;
            }
        });
    }

    public void requestFinnet(int progressDialogText, final String cacheKey, final boolean showActivate, boolean isRelogin, final HttpCallbackInterfaceSimple<ResponseFinnet> callback){
        final BaseActivity thisAct = BaseActivity.this;
        requestHttpSimple(true, progressDialogText, true, cacheKey, isRelogin, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId){
                return callback.requestMethod(httpService, bearerAuth, companyId, subscriptionId);
            }
            @Override
            public void onSuccess(ResponseFinnet data)
            {
                if(thisAct.isFinishing() || thisAct.isDestroyed()) return;

                String sc = "_"; if(data.statusCode != null) sc = data.statusCode;
                String sd = "System maintenance"; if(data.statusDesc != null) sd = data.statusDesc;
                if(sc.equals(ResponseFinnet.STATUSCODE_EMON_OK)){
                    callback.onSuccess(data);
                }else{
                    if(showActivate) {
                        if( sc.equals(ResponseFinnet.STATUSCODE_EMON_201) ) {
                            if (sd.contains("registrasi nomor") || sd.contains("Harap aktivasi nomor")) {
                                activateMcConfirmation();
                            } else {
                                Toast.makeText(BaseActivity.this, "[" + sc + "] " + sd, Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(BaseActivity.this, "[" + sc + "] " + sd, Toast.LENGTH_LONG).show();
                        }
                    }
                    callback.onFail(new ResponseError());
                }

            }
            @Override
            public  boolean onFail(ResponseError error){
                return callback.onFail(error);
            }
        });
    }

    public <T> void requestPaySSP(int progressDialogText, final String cacheKey, final HttpCallbackInterfaceSimple<T> callback){
        final BaseActivity thisAct = BaseActivity.this;
        requestHttpSimple(true, progressDialogText, true, cacheKey, true, new HttpCallbackInterfaceSimple<T>() {
            @Override
            public Call<T> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId){
                return callback.requestMethod(httpService, bearerAuth, companyId, subscriptionId);
            }
            @Override
            public void onSuccess(T data)
            {
                callback.onSuccess(data);

            }
            @Override
            public  boolean onFail(ResponseError error){
                return callback.onFail(error);
            }
        });
    }

    public void activateMcConfirmation(){
        Utility.showConfirmationDialog(this, R.string.comp_topupwidget_label_activatemcash, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                activateMC();
            }
        });
    }

    private void activateMC(){
        requestHttpSimple(true, R.string.progressdialog_emonregisterphone, true, null, true, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.emonRegisterPhone(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseFinnet response) {
                if( response.isActivate() ){
                    Toast.makeText(BaseActivity.this, R.string.inputotp_toast_successotp, Toast.LENGTH_LONG).show();
                    return;
                }
                showOTPInput(response);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return true;
            }
        });
    }


    private void showOTPInput(ResponseFinnet responseFinnet){
        Intent intent = new Intent(this, SendOTPActivity.class);
        intent.putExtra(AppConstant.ITN_TOPUPSENDOTP_CUSTSTATUSCODE, responseFinnet.custStatusCode);
        startActivity(intent);
    }

    public <T> void requestHttpSimple(boolean withAuth, final int progressDialogText, final boolean forceRequest, final String cacheKey, boolean isRelogin, final HttpCallbackInterfaceSimple<T> callback){
        requestHttp(withAuth, progressDialogText, forceRequest, cacheKey, isRelogin, new HttpCallbackInterface<T, T>() {
            @Override
            public Call<T> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return callback.requestMethod(httpService, bearerAuth, companyId, subscriptionId);
            }

            @Override
            public Call<ResponseBody> requestMethodStream(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                // nothing
                return null;
            }

            @Override
            public void onSuccess(T response) {
                callback.onSuccess(response);
            }

            @Override
            public void onSuccessStream(List<T> response) {
                // nothing
            }

            @Override
            public boolean onFail(ResponseError error) {

                return callback.onFail(error);
            }
        });
    }

    public <T,U> void apiRequestHttp(final RequestParamConfig rpc, final ApiHttpCallbackInterface<T, U> callback)
    {
        if(rpc.cacheKey == null){
            Log.d(AppConstant.LOG_TAG, "rpc.cacheKey must not NULL");
            return;
        }

        if( ! rpc.forceRequest ) if( getApiCache(rpc.cacheKey, callback) ) return;

        if(!rpc.withAuth){
            apiRequestHttpWithAuth(null, rpc, callback);
            return;
        }

        getAuthToken(rpc.isRelogin, false, new SuccessFailCallback<String,ResponseError>() {
            @Override
            public void onSuccess(String bearerAuth) {
                apiRequestHttpWithAuth(bearerAuth, rpc, callback);
            }
            @Override
            public boolean onFail(ResponseError re){
                callback.onFail(re);
                return rpc.isShowFailToast;
            }
        });
    }
    public <T,U> void apiRequestHttpWithAuth(String bearerAuth, final RequestParamConfig rpc, final ApiHttpCallbackInterface<T,U> callback){

        final BaseActivity thisAct = BaseActivity.this;

        final Call<T> call = callback.requestMethod(httpService, bearerAuth);
        if(rpc.progressTextRes != 0) {
            AppProgressDialog.show(this, getString(rpc.progressTextRes), new AppProgressDialog.AppListener() {
                @Override
                public void onClickCancel() {
                    call.cancel();
                }
            });
        }
        call.enqueue(new Callback<T>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<T> call, Response<T> response) {
                if(rpc.progressTextRes != 0) AppProgressDialog.hide();
                if( response.isSuccessful() ){
                    U d = apiResponseConvert(rpc.cacheKey, response.body());
                    if(rpc.isCache) setCache(rpc.cacheKey, d);
                    if(!thisAct.isFinishing() && !thisAct.isDestroyed()) callback.onSuccess(d);
                }else{
                    if(!thisAct.isFinishing() && !thisAct.isDestroyed()) {
                        ResponseError re = apiHandleResponseError(response, callback);
                        callback.onFail(re);
                        if( rpc.isShowFailToast && re.error_description != null && re.error_description.length() > 0) {
                            Toast.makeText(BaseActivity.this, re.error_description, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<T> call, Throwable t) {
                if(rpc.progressTextRes != 0) AppProgressDialog.hide();
                if(!thisAct.isFinishing() && !thisAct.isDestroyed()) {

                    ResponseError re = new ResponseError();
                    re.error_description = t.getMessage();
                    callback.onFail(re);

                    if(t.getMessage() != null) {
                        if (t.getMessage().equals("Socket closed")) {
                            return;
                        }
                    }

                    if( rpc.isShowFailToast) {
                        Toast.makeText(BaseActivity.this, getResources().getString(R.string.global_toast_unable_access_server, AppConf.urlPrivate()), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private <T> ResponseError apiHandleResponseError(Response<T> resp, ApiHttpCallbackInterface callback){
        String msg = "";
        ResponseError re = null;
        try {
            ResponseBody eb = resp.errorBody();
            if(eb == null) throw new Exception();
            String ebStr = eb.string();
            if(ebStr == null) throw new Exception();
            re = gson.fromJson(ebStr, ResponseError.class);
            if(re == null) throw new Exception();
            if(re.error_description == null) {
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
                        msg = re.errorMessage.id;
                    }
                }
            }else{
                msg = re.error_description;
            }
        }catch (Exception e){
            msg = getString(R.string.global_toast_server_return_code, resp.code());
            re = new ResponseError();
            FieldErrorMessage fem = new FieldErrorMessage();
            fem.id = msg;
            fem.en = msg;

            re.error_description = msg;
            re.errorMessage = fem;
        }

        if( resp.code() == 401 ) {
            setSpLong(AppConstant.SP_AUTHTOKENEXPIRE, 0);  // for auto login for next action
            msg = getString(R.string.global_toast_accessnoauth);
        }

        re.error_description = msg;

        return re;
    }

    @Deprecated  // use apiRequestHttp
    public <T,U> void requestHttp(boolean withAuth, final int progressDialogText, final boolean forceRequest, final String cacheKey, boolean isRelogin, final HttpCallbackInterface<T, U> callback) {
        if(!withAuth){
            requestHttpWithAuth(null, progressDialogText, forceRequest, cacheKey, callback);
            return;
        }

        getAuthToken(isRelogin, false, new SuccessFailCallback<String,ResponseError>() {
            @Override
            public void onSuccess(String token) {
                requestHttpWithAuth(token, progressDialogText, forceRequest, cacheKey, callback);
            }
            @Override
            public boolean onFail(ResponseError re){
                return callback.onFail(re);
            }
        });
    }
    @Deprecated  // use apiRequestHttpWithAuth
    public <T,U> void requestHttpWithAuth(String bearerAuth, final int progressDialogText, boolean forceRequest, final String cacheKey, final HttpCallbackInterface<T,U> callback){

        if(!forceRequest) if( getCache(cacheKey, callback) ) return;

        ProfileUserCompany pc = getProfileUserCompany();

        final BaseActivity thisAct = BaseActivity.this;

        final Call<T> call = callback.requestMethod(httpService, bearerAuth, pc.companyId, pc.subscriptionId);
        if(progressDialogText != 0) {
            AppProgressDialog.show(this, getString(progressDialogText), new AppProgressDialog.AppListener() {
                @Override
                public void onClickCancel() {
                    call.cancel();
                }
            });
        }
        call.enqueue(new Callback<T>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<T> call, Response<T> response) {
                if(progressDialogText != 0) AppProgressDialog.hide();
                if( response.isSuccessful() ){
                    T d = apiResponseConvert(cacheKey, response.body());
                    setCache(cacheKey, d);
                    if(!thisAct.isFinishing() && !thisAct.isDestroyed()) callback.onSuccess(d);
                }else{
                    if(!thisAct.isFinishing() && !thisAct.isDestroyed()) {
                        handleResponseError(response, callback);
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<T> call, Throwable t) {
                if(progressDialogText != 0) AppProgressDialog.hide();
                if(!thisAct.isFinishing() && !thisAct.isDestroyed()) {
                    boolean isDefaultToast = callback.onFail(new ResponseError());

                    if(t.getMessage() != null) {
                        if (t.getMessage().equals("Socket closed")) {
                            return;
                        }
                    }

                    if( isDefaultToast ) {
                        Toast.makeText(BaseActivity.this, getResources().getString(R.string.global_toast_unable_access_server, AppConf.urlPrivate()), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public <T,U> void requestHttpStream(final int progressDialogText, final boolean forceRequest, final String cacheKey, final Type typeOfT, boolean isRelogin, final HttpCallbackInterface<T, U> callback){
        getAuthToken(isRelogin, false, new SuccessFailCallback<String,ResponseError>() {
            @Override
            public void onSuccess(String token) {
                requestHttpStreamBase(token, progressDialogText, forceRequest, cacheKey, typeOfT, callback);
            }
            @Override
            public boolean onFail(ResponseError re){
                return callback.onFail(re);
            }
        });
    }
    public <T,U> void requestHttpStreamBase(String bearerAuth, final int progressDialogText, boolean forceRequest, final String cacheKey, final Type typeOfT, final HttpCallbackInterface<T, U> callback){

        if(!forceRequest) if( getCache(cacheKey, callback) ) return;

        if( ! checkReadyProfile(cacheKey) ) return;

        ProfileUserCompany pc = getProfileUserCompany();

        final BaseActivity thisAct = BaseActivity.this;

        final Call<ResponseBody> call = callback.requestMethodStream(httpService, bearerAuth, pc.companyId, pc.subscriptionId);
        if(progressDialogText != 0){
            AppProgressDialog.show(this, getString(progressDialogText), new AppProgressDialog.AppListener() {
                @Override
                public void onClickCancel() {
                    call.cancel();
                }
            });
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(progressDialogText != 0) AppProgressDialog.hide();

                if( response.isSuccessful() ){

                    List<T> list = Utility.streamParse(gson, response.body(), typeOfT);

                    List<U> listResult = responseConvert(cacheKey, list);
                    setCache(cacheKey, listResult);
                    if(!thisAct.isFinishing() && !thisAct.isDestroyed()) callback.onSuccessStream(listResult);
                }else{
                    if(!thisAct.isFinishing() && !thisAct.isDestroyed()) {
                        handleResponseError(response, callback);
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(progressDialogText != 0) AppProgressDialog.hide();
                if(!thisAct.isFinishing() && !thisAct.isDestroyed()) {
                    boolean isDefaultToast = callback.onFail(new ResponseError());

                    if(t.getMessage() != null) {
                        if (t.getMessage().equals("Socket closed")) {
                            return;
                        }
                    }

                    if( isDefaultToast ) {
                        Toast.makeText(BaseActivity.this, getResources().getString(R.string.global_toast_unable_access_server, AppConf.urlPrivate()), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private <T> void handleResponseError(Response<T> resp, HttpCallbackInterface callback){
        String msg = "";
        ResponseError re = null;
        try {
            ResponseBody eb = resp.errorBody();
            if(eb == null) throw new Exception();
            String ebStr = eb.string();
            if(ebStr == null) throw new Exception();
            re = gson.fromJson(ebStr, ResponseError.class);
            if(re == null) throw new Exception();
            if(re.error_description == null) {
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
                        msg = re.errorMessage.id;
                        re.error_description = msg;
                    }
                }
            }else{
                msg = re.error_description;
            }
        }catch (Exception e){
            msg = getString(R.string.global_toast_server_return_code, resp.code());
            re = new ResponseError();
            FieldErrorMessage fem = new FieldErrorMessage();
            fem.id = msg;
            fem.en = msg;

            re.error_description = msg;
            re.errorMessage = fem;
        }

        if( resp.code() == 401 ) {
            setSpLong(AppConstant.SP_AUTHTOKENEXPIRE, 0);  // for auto login for next action
            msg = getString(R.string.global_toast_accessnoauth);
        }

        if ( callback.onFail(re) ) {
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkReadyProfile(String cacheKey){

        if(cacheKey != null){
            if(cacheKey.equals(AppConstant.SP_CACHEKEY_ME)) return true;
        }

        if( ! getCache(AppConstant.SP_CACHEKEY_ME, null) ){
            reloadProfile(null);
            return false;
        }

        return true;
    }

    public void reloadProfile(final CommonCallback<Void> listenerHttpResponse){
        requestHttpSimple(true, R.string.progressdialog_reloadprofile, true, AppConstant.SP_CACHEKEY_ME, true, new HttpCallbackInterfaceSimple<ResponseMe>() {
            @Override
            public Call<ResponseMe> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.me(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseMe response) {
                if(listenerHttpResponse != null) listenerHttpResponse.onSuccess(null);
            }

            @Override
            public boolean onFail(ResponseError re){
                return true;
            }
        });
    }

    public boolean isShowSplash(){
        boolean splash = sharePref.getBoolean(AppConstant.SP_SPLASH, false);
        if(splash) return false;

        SharedPreferences.Editor editor = sharePref.edit();
        editor.putBoolean(AppConstant.SP_SPLASH, true);
        editor.apply();

        return true;
    }

    public ProfileUserCompany getProfileUserCompany(){
        ProfileUserCompany pc = new ProfileUserCompany();
        pc.id = sharePref.getLong(AppConstant.SP_CACHEME_ID, 0);
        pc.username = sharePref.getString(AppConstant.SP_USERNAME, "");
        pc.fullname = sharePref.getString(AppConstant.SP_CACHEME_FULLNAME, "");
        pc.phone = sharePref.getString(AppConstant.SP_CACHEME_PHONE, "");
        pc.email = sharePref.getString(AppConstant.SP_CACHEME_EMAIL, "");
        pc.companyId = sharePref.getLong(AppConstant.SP_CACHEME_COMPANY_ID, 0);
        pc.companyName = sharePref.getString(AppConstant.SP_CACHEME_COMPANY_NAME, "");
        pc.subscriptionId = sharePref.getLong(AppConstant.SP_CACHEME_SUBSCRIPTION_ID, 0);
        pc.custBalance = sharePref.getString(AppConstant.SP_CACHEEMONBALANCE_CUSTBALANCE, "");
        return pc;
    }

    public <T> void queryDeleteAllAndSaveCallback(final String cacheKey, final T data, final CommonCallback<Void> callback){
        executeQueryProcessSuccess(new ListenerQuerySuccess<Boolean>() {
            @Override
            public Boolean onProcess() {
                SharedPreferences.Editor editor = sharePref.edit();

                if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.sspunpaidDao().deleteAll();
                    appDatabase.sspunpaidDao().insertAll( (List<Sspunpaid>)data );
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.sspdoneDao().deleteAll();
                    appDatabase.sspdoneDao().insertAll( (List<Sspdone>)data );
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_STREAMKJS) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.kjsDao().deleteAll();
                    appDatabase.kjsDao().insertAll((List<Kjs>)data);
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TAXTYPE) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.taxTypeDao().deleteAll();
                    appDatabase.taxTypeDao().insertAll((List<Taxtype>)data);
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TRANSLOG) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.transactionlogDao().deleteAll();
                    appDatabase.transactionlogDao().insertAll((List<Transactionlog>)data);
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_WPLIST) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.wajibpajakDao().deleteAll();
                    appDatabase.wajibpajakDao().insertAll((List<Wajibpajak>)data);
                }else{
                    Log.d(AppConstant.LOG_TAG, "No deleteAll handler");
                    editor.apply();
                    return false;
                }

                editor.apply();

                return true;
            }

            @Override
            public void onSuccess(Boolean result) {
                if(callback != null) callback.onSuccess(null);
            }
        });
    }

    @Deprecated  // use queryDeleteAllAndSaveCallback
    private <T> void queryDeleteAllAndSave(final String cacheKey, final T data){

        executeQueryProcessSuccess(new ListenerQuerySuccess<Boolean>() {
            @Override
            public Boolean onProcess() {
                SharedPreferences.Editor editor = sharePref.edit();

                if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPUNPAID) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.sspunpaidDao().deleteAll();
                    appDatabase.sspunpaidDao().insertAll( Sspunpaid.getInstanceList((List<ResponseSsp>)data) );
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_SSPDONE) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.sspdoneDao().deleteAll();
                    appDatabase.sspdoneDao().insertAll( Sspdone.getInstanceListSspdone((List<ResponseSsp>)data) );
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_STREAMKJS) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.kjsDao().deleteAll();
                    appDatabase.kjsDao().insertAll((List<Kjs>)data);
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TAXTYPE) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.taxTypeDao().deleteAll();
                    appDatabase.taxTypeDao().insertAll((List<Taxtype>)data);
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_TRANSLOG) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.transactionlogDao().deleteAll();
                    appDatabase.transactionlogDao().insertAll((List<Transactionlog>)data);
                }else if( cacheKey.equals(AppConstant.SP_CACHEKEY_WPLIST) ) {
                    editor.putLong(cacheKey, System.currentTimeMillis()+AppConstant.MS_KEEPDATA_20HOUR);
                    appDatabase.wajibpajakDao().deleteAll();
                    appDatabase.wajibpajakDao().insertAll((List<Wajibpajak>)data);
                }else{
                    Log.d(AppConstant.LOG_TAG, "No deleteAll handler");
                    editor.apply();
                    return false;
                }

                editor.apply();

                return true;
            }

            @Override
            public void onSuccess(Boolean result) {
//                if(result) querySaveCache(cacheKey, data);
            }
        });
    }

    public  <T> void executeQueryProcessSuccess(ListenerQuerySuccess<T> listener){
        new QueryAsyncTask<T>(listener).execute();
    }

    public void executeQuery(final ListenerQuery listenerQuery){
        new QueryAsyncTask<>(new ListenerQuerySuccess<Void>() {
            @Override
            public Void onProcess() {
                listenerQuery.onProcess();
                return null;
            }

            @Override
            public void onSuccess(Void result) {
                // nothing
            }
        }).execute();
    }

    protected static class QueryAsyncTask<T> extends AsyncTask<Void, Void, T> {

        private ListenerQuerySuccess<T> listener;

        public QueryAsyncTask(ListenerQuerySuccess<T> listener) {
            this.listener = listener;
        }

        @Override
        protected T doInBackground(Void... params) {
            return listener.onProcess();
        }

        @Override
        protected void onPostExecute(T result) {
            listener.onSuccess(result);
        }


    }

    public interface ListenerQuerySuccess<T> {
        T onProcess();
        void onSuccess(T result);
    }

    public interface ListenerQuery {
        void onProcess();
    }

    public void requestAuthorize(final String username, final String password, final SuccessFailCallback<ResponseAuthorize,ResponseError> callback){

        requestHttpSimple(false, R.string.progressdialog_authorize, true, null, true, new HttpCallbackInterfaceSimple<ResponseAuthorize>() {
            @Override
            public Call<ResponseAuthorize> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return ssoService.authorize(AppConf.clientId(), AppConstant.LOGIN_REDIRECT_URI, AppConstant.LOGIN_STATE, AppConstant.LOGIN_CODE,
                        username, password, AppConstant.LOGIN_PASSWORD);
            }

            @Override
            public void onSuccess(ResponseAuthorize response) {
                callback.onSuccess(response);
            }

            @Override
            public boolean onFail(ResponseError re){
                return callback.onFail(re);
            }
        });
    }

    public void requestAccessToken(final ResponseAuthorize responseAuthorize, final SuccessFailCallback<ResponseAccessToken,ResponseError> callback){
        requestHttpSimple(false, R.string.progressdialog_accesstoken, true, null, true, new HttpCallbackInterfaceSimple<ResponseAccessToken>() {
            @Override
            public Call<ResponseAccessToken> requestMethod(Http httpService, String bearerAuth, long companyId, long suscriptionId) {
                return ssoService.accessToken(AppConf.clientId(), AppConf.clientSecret(), AppConstant.LOGIN_REDIRECT_URI, responseAuthorize.getCode(), responseAuthorize.getState(), AppConstant.LOGIN_AUTHORIZATION_CODE, null);
            }

            @Override
            public void onSuccess(ResponseAccessToken response) {
                callback.onSuccess(response);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return callback.onFail(error);
            }
        });
    }

    public void requestMeWithDialog(final String accessToken, final SuccessFailCallback<ResponseMe,ResponseError> callback){
        requestHttpSimple(false, R.string.progressdialog_userdata, true, AppConstant.SP_CACHEKEY_ME, true, new HttpCallbackInterfaceSimple<ResponseMe>() {
            @Override
            public Call<ResponseMe> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return ssoService.me(AppConstant.HEADER_BEARER_SPACE+accessToken);
            }

            @Override
            public void onSuccess(ResponseMe response) {
                callback.onSuccess(response);
            }

            @Override
            public boolean onFail(ResponseError re){
                return callback.onFail(re);
            }
        });
    }

//    public void requestLogin(final Account account, String password, final SuccessFailCallback<String,ResponseError> callback){
    public void requestLogin(final String username, String password, final SuccessFailCallback<String,ResponseError> callback){
        requestAuthorize(username, password, new SuccessFailCallback<ResponseAuthorize, ResponseError>() {
            @Override
            public void onSuccess(ResponseAuthorize data) {
                BaseActivity.this.requestAccessToken(data, new SuccessFailCallback<ResponseAccessToken,ResponseError>() {
                    @Override
                    public void onSuccess(final ResponseAccessToken responseAccessToken) {
                        BaseActivity.this.requestMeWithDialog(responseAccessToken.access_token, new SuccessFailCallback<ResponseMe,ResponseError>() {
                            @Override
                            public void onSuccess(ResponseMe data) {
//                                accountManager.setUserData(account, AppConstant.AUD_REFRESH_TOKEN, responseAccessToken.refresh_token);
//                                setAuthTokenExpire(account, responseAccessToken.access_token, responseAccessToken.expires_in);
                                setAuthTokenExpire(username, responseAccessToken.access_token, responseAccessToken.expires_in);
                                callback.onSuccess( Utility.getBearerSpaceAuth(responseAccessToken.access_token) );
                            }
                            @Override
                            public boolean onFail(ResponseError re){
                                return callback.onFail(re);
                            }
                        });
                    }
                    @Override
                    public boolean onFail(ResponseError re){
                        return callback.onFail(re);
                    }
                });
            }

            @Override
            public boolean onFail(ResponseError data){
                return callback.onFail(data);
            }
        });
    }

//    public void requestLoginWithRefreshToken(final Account account, final String refreshToken, final SuccessFailCallback<String,ResponseError> callback){
//
//        requestHttpSimple(false, R.string.progressdialog_accesstoken, true, null, true, new HttpCallbackInterfaceSimple<ResponseAccessToken>() {
//            @Override
//            public Call<ResponseAccessToken> requestMethod(Http httpService, String bearerAuth, long companyId, long suscriptionId) {
//                return httpService.accessToken(AppConstant.CLIENT_ID, AppConstant.CLIENT_SECRET, AppConstant.LOGIN_REDIRECT_URI, null, null, "refresh_token", refreshToken);
//            }
//
//            @Override
//            public void onSuccess(final ResponseAccessToken responseAccessToken) {
//                BaseActivity.this.requestMeWithDialog(responseAccessToken.access_token, new SuccessFailCallback<ResponseMe,ResponseError>() {
//                    @Override
//                    public void onSuccess(ResponseMe data) {
//                        accountManager.setUserData(account, AppConstant.AUD_REFRESH_TOKEN, responseAccessToken.refresh_token);
//                        setAuthTokenExpire(account, responseAccessToken.access_token, responseAccessToken.expires_in);
//                        callback.onSuccess( Utility.getBearerSpaceAuth(responseAccessToken.access_token) );
//                    }
//                    @Override
//                    public boolean onFail(ResponseError re){
//                        return callback.onFail(re);
//                    }
//                });
//            }
//
//            @Override
//            public boolean onFail(ResponseError error) {
//                return callback.onFail(error);
//            }
//        });
//
//    }

//    public void setAuthTokenExpire(Account account, final String authToken, long expireIn){
    public void setAuthTokenExpire(String username, final String authToken, long expireIn){
        final Activity context = this;
//        accountManager.setAuthToken(account, AppfConstant.TOKEN_TYPE, authToken);

        SharedPreferences.Editor editor = sharePref.edit();
//        editor.putString(AppConstant.SP_USERNAME, account.name);
        editor.putString(AppConstant.SP_USERNAME, username);
        editor.putLong(AppConstant.SP_AUTHTOKENEXPIRE, System.currentTimeMillis()+expireIn*1000);
        editor.putString(AppConstant.SP_AUTH_TOKEN, authToken);
        editor.putLong(SharePref.SP_LAST_LOGIN, System.currentTimeMillis());
        editor.commit();

        getFirebaseToken(new CommonCallback<String>() {
            @Override
            public void onSuccess(String fcmToken) {
                ApiMain.saveFCMTokenToServer(context, Utility.getBearerSpaceAuth(authToken),
                        fcmToken);
//                saveFCMTokenToServer(Utility.getBearerSpaceAuth(authToken), 0, fcmToken, null);
            }
        });
    }

//    private void requestReloginAutomatic(Account account, SuccessFailCallback<String,ResponseError> callback){
//        Account account = getAccount();
//        if(account == null) return;

//        String refreshToken = accountManager.getUserData(account, AppConstant.AUD_REFRESH_TOKEN);
//        requestLoginWithRefreshToken(account, refreshToken, callback);

//        String key = getSpString(AppConstant.SP_PASSWORD_AES_KEY);
//        String password = accountManager.getPassword(account);
//        try {
//            password = AESCrypt.decrypt(key, password);
//        }catch (GeneralSecurityException e){
//            // nothing
//        }
//        requestLogin(account, password, callback);
//    }

    public void sspPayConfirm(final ResponseSsp sspunpaid, final SuccessFailCallback<ResponseSsp,ResponseError> callback){

        if( ! Utility.isStillValid(sspunpaid.billing.expiredDate) ) {
            Toast.makeText(this, R.string.sspdetailunpaid_toast_billnumexpired, Toast.LENGTH_LONG).show();
            return;
        }

        saveFCMTokenToServerValidate(new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {

                Utility.showConfirmationDialog(BaseActivity.this, getString(R.string.paypphsummary_label_dialogconfirmpayssp, Utility.toMoney(true, sspunpaid.amount)), new CommonCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {

                        sspMPNPaySSP(sspunpaid.id, callback);

                    }
                });

            }
        });

    }

    private void sspMPNPaySSP(final long sspId, final SuccessFailCallback<ResponseSsp,ResponseError> callback){
        requestPaySSP(R.string.progressdialog_payssp, null, new HttpCallbackInterfaceSimple<ResponseSsp>() {
            @Override
            public Call<ResponseSsp> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
//                return httpService.paySSP(bearerAuth, AppConstant.HOST_NAME, sspId);
                return httpService.paySSP(bearerAuth, sspId);
            }

            @Override
            public void onSuccess(ResponseSsp response) {
                callback.onSuccess(response);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return callback.onFail(error);
            }
        });
    }

    public void deleteSSPConfirm(final long id, final String consumeId){
        final Activity context = this;
        Utility.showConfirmationDialog(this, R.string.sspdetailunpaid_label_msgdelssp, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                ApiMain.sspDel(context, new RequestParamConfig(), id, new CommonCallback<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody data) {
                        successDeleteSSP(id);
                    }
                });
//                deleteSSPProceed(id, consumeId);
            }
        });

    }

    @Deprecated
    private void deleteSSPProceed(final long id, final String consumeId){
        requestHttpSimple(true, R.string.progressdialog_delssp, true, null,
                true, new HttpCallbackInterfaceSimple<ResponseBody>() {
            @Override
            public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.deleteSSP(bearerAuth, consumeId, id);
            }

            @Override
            public void onSuccess(ResponseBody response) {
                successDeleteSSP(id);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return true;
            }
        });
    }

    private void successDeleteSSP(final long id){
        Utility.toast(this, "SSP berhasil dihapus.");
        executeQueryProcessSuccess(new ListenerQuerySuccess<Void>() {
            @Override
            public Void onProcess() {
                appDatabase.sspunpaidDao().deleteById(id);
                appDatabase.sspdoneDao().deleteById(id);
                return null;
            }

            @Override
            public void onSuccess(Void result) {
                BaseActivity.this.finish();
            }
        });
    }

    private void getFirebaseToken(final CommonCallback<String> callback){
        firebaseInstance.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(AppConstant.LOG_TAG, "Firebase getInstanceId failed", task.getException());
                    if(callback != null) callback.onSuccess(null);
                    return;
                }

                String token = task.getResult().getToken();

                String msg = "FirebaseToken: "+token;
                Log.d(AppConstant.LOG_TAG, msg);
                if(callback != null) callback.onSuccess(token);
            }
        });
    }


    public void saveFCMTokenToServerValidate(final CommonCallback<Void> callback) {
        final Activity context = this;
        final String oldToken = getSpString(AppConstant.SP_FCM_TOKEN);
        getFirebaseToken(new CommonCallback<String>() {
            @Override
            public void onSuccess(String fcmToken) {
                callback.onSuccess(null);
                if(fcmToken == null) return;
                if( ! oldToken.equals(fcmToken) ){
                    ApiMain.saveFCMTokenToServer(context, null,
                            fcmToken);
//                    saveFCMTokenToServer(null, 0, fcmToken, null);
                }
            }
        });
    }

    @Deprecated
    public void saveFCMTokenToServer(final String bearerAuthSpace, int resDialogText, final String fcmToken,
                                     final CommonCallback<Void> callback){
        final FirebaseToken ft = new FirebaseToken();
        ft.deviceId = fcmToken;
        requestHttpSimple( (bearerAuthSpace==null), resDialogText, true, null,
                true, new HttpCallbackInterfaceSimple<ResponseBody>() {
            @Override
            public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.updateFirebaseToken( (bearerAuthSpace!=null?bearerAuthSpace:bearerAuth), ft);
            }

            @Override
            public void onSuccess(ResponseBody response) {
                setSpString(AppConstant.SP_FCM_TOKEN, fcmToken);
                if(callback!=null) callback.onSuccess(null);
            }

            @Override
            public boolean onFail(ResponseError error) {
                Log.w(AppConstant.LOG_TAG, "Fail update firebase token: " + (error!=null?error.error_description:"") );
                if(callback!=null) callback.onSuccess(null);
                return false;
            }
        });
    }

    public void kjsFindOneByCode(final String code, final CommonCallback<Kjs> callback){
        executeQueryProcessSuccess(new ListenerQuerySuccess<Kjs>() {
            @Override
            public Kjs onProcess() {
                return appDatabase.kjsDao().getByCode(code);
            }

            @Override
            public void onSuccess(Kjs result) {
                callback.onSuccess(result);
            }
        });
    }

    public void updateSspBillNumber(final Sspunpaid sspUnpaidNotif, final CommonCallback<Sspunpaid> callback){
        executeQueryProcessSuccess(new ListenerQuerySuccess<Sspunpaid>() {
            @Override
            public Sspunpaid onProcess() {
                Sspunpaid sspunpaid;

                // TODO: @test
//                appDatabase.sspunpaidDao().deleteById(sspUnpaidNotif.id);
//                List<Sspunpaid> listSspUnpaid = new ArrayList<>();
//                sspunpaid = new Sspunpaid();
//                sspunpaid.id = sspUnpaidNotif.id;
//                sspunpaid.name = "Citra Lestari";
//                sspunpaid.npwp = "347294732984732897423";
//                sspunpaid.address = "Jln. Pajakku";
//                sspunpaid.amount = 40000;
//                sspunpaid.idBilling = null;
//                sspunpaid.billNumExpired = null;
//                sspunpaid.idBillingPajakku = null;
//                sspunpaid.status = 0;
//                sspunpaid.month1 = 1;
//                sspunpaid.month2 = 2;
//                sspunpaid.year = 2018;
//                sspunpaid.taxTypeCode = "411121";
//                sspunpaid.taxTypeName = "PPh 21";
//                sspunpaid.kjsCode = "300";
//                sspunpaid.kjsName = "STP";
//                sspunpaid.updatedAt = "2019-03-22 10:10:10";
//                sspunpaid.refNo = "389274327498324";
//                listSspUnpaid.add(sspunpaid);
//                appDatabase.sspunpaidDao().insertAll(listSspUnpaid);

                sspunpaid = appDatabase.sspunpaidDao().getById(sspUnpaidNotif.id);
                if(sspunpaid == null) return null;

                sspunpaid.taxslipresponseCode = sspUnpaidNotif.taxslipresponseCode;
                sspunpaid.idBilling = sspUnpaidNotif.idBilling;
                sspunpaid.billNumExpired = sspUnpaidNotif.billNumExpired;

                appDatabase.sspunpaidDao().update(sspunpaid);

                return sspunpaid;
            }

            @Override
            public void onSuccess(Sspunpaid result) {
                if(result != null) if(callback != null) callback.onSuccess(result);
            }
        });
    }

    // ---------- SET UI

    protected void setViewData(){
        Utility.logWarn("2020 0821 2155 override jika dperlukan");
    }


    protected InpFile setCompInpFile(int id, int actRes, CommonCallback<BaseInput> cbPre, CommonCallback<FileBrowserItem> onPick){
        InpFile comp = new InpFile(this, null, id, actRes, cbPre, onPick);
        inputs.add(comp);
        return comp;
    }

    protected InpTextX setCompInpTextX(int id){
        InpTextX comp = new InpTextX(this, null, id, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                checkInput(data);
            }
        });
        inputs.add(comp);
        return comp;
    }

    protected AppSpinner setCompAppSpinner(int id, List<SpinItem> items, CommonCallback<BaseInput> cbPre){
        AppSpinner bi = new AppSpinner(this, null, id, items, cbPre, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                checkInput(data);
            }
        });
        inputs.add(bi);
        return bi;
    }

    protected void setBotBtn(int id){
        btnBot = findViewById(id);
        btnBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    protected AppViewPager setViewPager(int id, List<Fragment> listFrag){
        AppViewPager v = appViewPager = findViewById(id);
        v.init(getSupportFragmentManager(), listFrag);
        return v;
    }


    protected void setAppRecyclerViewStart(){
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.forceRequest = false;
        rpc.isShowFailToast = false;
        fetch(rpc);
    }

    protected void recyclerRefresh(){
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.isRelogin = true;
        fetch(rpc);
    }

    protected RecyclerViewX setRecyclerViewX(int viewId, final CommonCallback<ClickItemListParam> callback, final CommonCallback<RecyclerViewX> refreshCb, int... menus){
        RecyclerViewX recyclerViewX = new RecyclerViewX(this, null, viewId);
        recyclerViewX.setOnClick(callback, menus);
        recyclerViewX.setOnRefresh(refreshCb != null ? refreshCb : new CommonCallback<RecyclerViewX>() {
            @Override
            public void onSuccess(RecyclerViewX data) {
                recyclerRefresh();
//                recyclerViewX.setRefreshing(false);
            }
        });
        return recyclerViewX;
    }

    protected void fetch(final RequestParamConfig rpc){
        Utility.logErr("2020 0904 1739 must override");
    }

    // -----------------------

    protected boolean checkInput(BaseInput baseInput){

        return Utility.checkInput(this, null, baseInput, inputs, btnBot);

    }

    public boolean onSubmitValidate(BaseInput bi){
        Utility.logWarn("2020 1221 1912 onSubmitValidate boleh di-override");
        return true;
    }

    protected void saveObj(){
        Utility.logWarn("2020 0821 2155 override jika dperlukan");
    }

    protected void save(){
        Utility.logErr("2020 0821 2153 must override if use setBotBtn");
    }

    public void prevFrag(){
        int i = appViewPager.getCurrentItem();
        if(i == 0){
            finish();
        }else {
            appViewPager.setCurrentItem(i-1, true);
        }
    }

    public void nextFrag(){
        int cnt = appViewPager.getCount();
        int i = appViewPager.getCurrentItem();
        if(i >= cnt-1){
            finish();
        }else {
            appViewPager.setCurrentItem(i+1, true);
        }

    }

    public void gotoFrag(int idx){
        appViewPager.setCurrentItem(idx, true);
    }

    public int getFragIdx(){
        return appViewPager.getCurrentItem();
    }

    public BaseFragment getCurrFrag(){
        return (BaseFragment) appViewPager.getItem( appViewPager.getCurrentItem() );
    }

}
