package com.pajakku.tupaimobile.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.pager.MainPagerAdapter;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.FilterView;
import com.pajakku.tupaimobile.fragment.HelpFragment;
import com.pajakku.tupaimobile.fragment.HelpHomeFragment;
import com.pajakku.tupaimobile.fragment.HomeFragment;
import com.pajakku.tupaimobile.fragment.ProfileFragment;
import com.pajakku.tupaimobile.fragment.SSPDoneFragment;
import com.pajakku.tupaimobile.fragment.SSPFragment;
import com.pajakku.tupaimobile.fragment.SSPUnpaidFragment;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.GridItem;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.TaxSlipResponse;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.TransLog;
import com.pajakku.tupaimobile.ppob.PpobFragment;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.AppFirebaseService;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends RepositoryActivity {

    private ViewPager viewPager;
    private MyBroadCastReceiver broadCastReceiver;
    private FilterView filterView;
    private RelativeLayout layoutOutdateversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadCastReceiver = new MyBroadCastReceiver();

        layoutOutdateversion = findViewById(R.id.main_outdateversion_layout);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        TabLayout.Tab tab;
        List<Fragment> fragList = new ArrayList<>();

        tab = tabLayout.newTab();
        tab.setText(R.string.main_btn_home);
        tab.setIcon(R.drawable.ic_main_home);
        tabLayout.addTab(tab);
        fragList.add( new PpobFragment());

        tab = tabLayout.newTab();
        tab.setText(R.string.main_btn_ssp);
        tab.setIcon(R.drawable.ic_main_ssp);
        tabLayout.addTab(tab);
        fragList.add( new SSPFragment() );

        // TODO: @warn hide SPT function
        tab = tabLayout.newTab();
        tab.setText(R.string.main_btn_spt);
        tab.setIcon(R.drawable.ic_main_spt);
        tabLayout.addTab(tab);
        fragList.add( new HomeFragment() );

        tab = tabLayout.newTab();
        tab.setText(R.string.main_btn_help);
        tab.setIcon(R.drawable.ic_main_help);
        tabLayout.addTab(tab);
        fragList.add( new HelpFragment() );

        tab = tabLayout.newTab();
        tab.setText(R.string.main_btn_profile);
        tab.setIcon(R.drawable.ic_main_profile);
        tabLayout.addTab(tab);
        fragList.add( new ProfileFragment() );

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.main_pager);
        final MainPagerAdapter adapter = new MainPagerAdapter( getSupportFragmentManager(), fragList );
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( position == SSPFragment.VIEW_PAGER_CODE ) {
                    Fragment frag = getFragment(position);
                    if (frag != null) {
                        ((SSPFragment) frag).showQuickHelp();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                highlightTabBtn(tab);
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#9A9A9A"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                highlightTabBtn(tab);
            }
        });

        filterView = findViewById(R.id.main_filterview);

    }

    @Override
    protected void onStart(){
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiver, new IntentFilter(AppConstant.ITNFILTER_TUPAINOTIF));
    }

    @Override
    protected void onResume(){
        super.onResume();

//        String usr = getSpString(AppConstant.SP_PASSWORD_AES_KEY);
//        String authToken = SharePref.getStr(this, AppConstant.SP_AUTH_TOKEN);
//        if (usr.length() <= 0 || getAccount() == null) {
//        if (authToken.isEmpty()) {
//            LoginActivity.startAct(this);
//        }else {
//            checkThenGotoSSPUnpaidDetail();
//            checkThenGotoSSPDoneDetail();
//
//            checkOutdateVersion();
//        }


        checkThenGotoSSPUnpaidDetail();
        checkThenGotoSSPDoneDetail();

        checkOutdateVersion();

        // utk cek ukuran layar
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        float dpHeight = outMetrics.heightPixels / density;
        Log.d(AppConstant.LOG_TAG, "ukuran layar "+dpWidth+" x "+dpHeight);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utility.log( "onActRes "+requestCode+" / "+resultCode);
        switch (requestCode)
        {
            case AppConstant.ACTRES_CREATE_SSP: {
                switch (resultCode) {
                    case RESULT_OK:
                        gotoSspunpaidList();
                        break;
                    default:
                        break;
                }
                break;
            }
            case AppConstant.ACTRES_TOP_UP:
                break;

            case AppConstant.ACTRES_TAXTYPE_AS_LIST:
                switch (resultCode){
                    case RESULT_OK:
                        Taxtype taxtype = (Taxtype) data.getSerializableExtra(AppConstant.ITN_TAXTYPELISTHOME_TAXTYPE);
                        GridItem gi = new GridItem(taxtype.fetchIcon(), taxtype.fetchAlias(getResources()), taxtype.fetchCategory());
                        gi.taxtype = taxtype;

                        Fragment f = getFragment(HomeFragment.VIEW_PAGER_CODE);
                        if(f != null){
                            HomeFragment frag = (HomeFragment)f;
                            frag.clickGridItemTax(gi);
                        }
                        break;
                    default:
                        break;
                }
                break;

            case AppConstant.ACTRES_SSPUNPAID_DETAIL_TO_LIST:
                if(resultCode == RESULT_OK){
                    gotoSSPDoneListFragment();
                }
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if( filterView.getVisibility() == View.VISIBLE ) {
            filterView.hide();
        }else{
            if( viewPager.getCurrentItem() == HelpFragment.VIEW_PAGER_CODE ){
                Fragment frag = getFragment(HelpFragment.VIEW_PAGER_CODE);
                if( frag != null ){
                    HelpFragment hf = (HelpFragment)frag;
                    if( hf.viewPager.getCurrentItem() != HelpHomeFragment.VIEW_PAGER_CODE ){
                        hf.onBackPressed();
                        return;
                    }
                }
            }
            Utility.showConfirmationDialog(this, R.string.main_label_confirmbackmsg, new CommonCallback<Void>() {
                @Override
                public void onSuccess(Void data) {
                    MainActivity.super.onBackPressed();
                }
            });
        }
    }

    private void checkOutdateVersion(){
        String currVer = getPInfo().versionName;
        String lastVer = getSpString(AppConstant.SP_CACHESTATUSDATA_LASTCLIENTVERSION);
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.forceRequest = !currVer.equals(lastVer);
        ApiMain.httpFirst(this, rpc, new SuccessFailCallback<AppStatusData, ResponseDTO>() {
            @Override
            public void onSuccess(AppStatusData data) {
                setOutdateVersionVisible(data);
            }

            @Override
            public void onFail(ResponseDTO data) {
                AppStatusData asd = new AppStatusData();
                asd.isOutdateVersion = false;
                setOutdateVersionVisible(asd);
            }
        });
//        getStatusData(rpc, new CommonCallback<AppStatusData>() {
//            @Override
//            public void onSuccess(AppStatusData data) {
//                layoutOutdateversion.setVisibility( data.isOutdateVersion ? View.VISIBLE : View.GONE );
//            }
//        });
    }

    private void setOutdateVersionVisible(AppStatusData data){
        layoutOutdateversion.setVisibility( data.isOutdateVersion ? View.VISIBLE : View.GONE );
    }

    public void setFilterViewCallback(int listType, CommonCallback<FilterParam> callback){
        filterView.setCallback(listType, callback);
        filterView.show();
        switch (listType){
            case FilterView.LISTTYPE_WP:
                filterView.setModeFindWp();
                break;
            default:
                filterView.setModeFind();
                break;
        }

    }

    public void setFilterViewSort(int listType, CommonCallback<FilterParam> callback){
        filterView.setCallbackSort(listType, callback);
        filterView.show();

        switch (listType){
            case FilterView.LISTTYPE_WP:
                filterView.setModeSortWp();
                break;
            default:
                filterView.setModeSort();
                break;
        }

    }

    public void resetFilterViewparam(int type){
        switch (type) {
            case FilterView.LISTTYPE_SSPDONE:
                filterView.paramFindSspDone = new FilterParam();
                break;
            case FilterView.LISTTYPE_WP:
                filterView.paramFindWp = new FilterParam();
                break;
            default:
                filterView.paramFindSspUnpaid = new FilterParam();
                break;
        }
    }

    public FilterParam getFilterViewParam(int listType){
        switch (listType){
            case FilterView.LISTTYPE_SSPDONE: return filterView.paramFindSspDone;
            case FilterView.LISTTYPE_WP: return filterView.paramFindWp;
        }
        return filterView.paramFindSspUnpaid;
    }

    private void checkThenGotoSSPUnpaidDetail(){
        Intent itn = getIntent();
        int notifType = itn.getIntExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, 0);
        if(notifType != AppFirebaseService.NOTIF_TYPE_JUSTGENERATEBILLNUM) return;

        long notifDate = itn.getLongExtra(AppConstant.ITN_NOTIFSSP_NOTIFDATE, 0);
        long lastDate = getSpLong(AppConstant.SP_ONCE_DATENOTIF);
        if(notifDate <= lastDate) return;
        setSpLong(AppConstant.SP_ONCE_DATENOTIF, notifDate);

        final Sspunpaid ssp = (Sspunpaid) itn.getSerializableExtra(AppConstant.ITN_NOTIFSSP_SSPUNPAID);

        repoSingleSSPUnpaid(ssp.id, new CommonCallback<Sspunpaid>() {
            @Override
            public void onSuccess(Sspunpaid data) {
                if(data != null){
                    gotoSSPUnpaidDetail(data);
                    return;
                }
                gotoSspunpaidList();
            }
        });
    }

    private void checkThenGotoSSPDoneDetail(){
        Intent itn = getIntent();
        int notifType = itn.getIntExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, 0);
        if(notifType != AppFirebaseService.NOTIF_TYPE_PAIDSSP) return;

        long notifDate = itn.getLongExtra(AppConstant.ITN_NOTIFSSP_NOTIFDATE, 0);
        long lastDate = getSpLong(AppConstant.SP_ONCE_DATENOTIF);
        if(notifDate <= lastDate) return;
        setSpLong(AppConstant.SP_ONCE_DATENOTIF, notifDate);
        long sspId = itn.getLongExtra(AppConstant.ITN_NOTIFPAY_SSPID, 0);

        repoSingleSSPDone(sspId, new CommonCallback<Sspdone>() {
            @Override
            public void onSuccess(Sspdone data) {
                if(data != null){
                    Intent itnSsp = new Intent(MainActivity.this, SSPDetailDoneActivity.class);
                    itnSsp.putExtra(AppConstant.ITN_SSPDETAILDONE_SSPDONE, data);
                    startActivity(itnSsp);
                    return;
                }
                gotoSSPDoneListFragment();
            }
        });
    }

    private void gotoSSPUnpaidDetail(Sspunpaid ssp){
        Intent itnSsp = new Intent(this, SSPDetailUnpaidActivity.class);
        itnSsp.putExtra(AppConstant.ITN_SSPDETAILUNPAID_SSPUNPAID, ssp);
        startActivity(itnSsp);
    }

    private void gotoSspunpaidList(){
        viewPager.setCurrentItem( SSPFragment.VIEW_PAGER_CODE );
        Fragment f = getFragment(SSPFragment.VIEW_PAGER_CODE);
        if( f != null ){
            ((SSPFragment)f).viewPager.setCurrentItem(SSPUnpaidFragment.VIEW_PAGER_CODE);
        }

    }

    private void gotoSSPDoneListFragment(){
        viewPager.setCurrentItem( SSPFragment.VIEW_PAGER_CODE );
        Fragment f = getFragment(SSPFragment.VIEW_PAGER_CODE);
        if( f != null ){
            ((SSPFragment)f).viewPager.setCurrentItem(SSPDoneFragment.VIEW_PAGER_CODE);
        }
    }

    private void highlightTabBtn(TabLayout.Tab tab){
        tab.getIcon().setColorFilter(Color.parseColor("#22409F"), PorterDuff.Mode.SRC_IN);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
//    {
//        switch (requestCode) {
//            case AppConstant.RP_GET_ACCOUNT: {
//                if(grantResults.length > 0)
//                    if ( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
//                    }
//                break;
//            }
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item){
//
//        Log.d(AppConstant.LOG_TAG, "---------------- on menu item click");
//        return true;
//    }

    public Fragment getFragment(int id){
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            switch (id){
                case HomeFragment.VIEW_PAGER_CODE:
                    if(f instanceof HomeFragment) return f;
                    break;
                case SSPFragment.VIEW_PAGER_CODE:
                    if(f instanceof SSPFragment) return f;
                    break;

                    // @warn hide SPT functiono for moment
//                case MainSptFragment.VIEW_PAGER_CODE:
//                    if(f instanceof MainSptFragment) return f;
//                    break;

                case HelpFragment.VIEW_PAGER_CODE:
                    if(f instanceof HelpFragment) return f;
                    break;
                case ProfileFragment.VIEW_PAGER_CODE:
                    if(f instanceof ProfileFragment) return f;
            }
        }
        return null;
    }

    private void successPaySSP(){
        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, null);
        removeCache(AppConstant.SP_CACHEKEY_EMONBALANCE, true, null);

        RequestParamConfig rpc = new RequestParamConfig();
        rpc.reqPaging.page = 1L;
        ApiMain.getSspDone(this, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Sspdone>, ResponseDTO>() {
            @Override
            public void onSuccess(List<Sspdone> data) {
                Fragment f = getFragment(SSPFragment.VIEW_PAGER_CODE);
                if( f == null ) return;
                f = ((SSPFragment) f).getFragment(SSPDoneFragment.VIEW_PAGER_CODE);
                if( f == null ) return;
                ((SSPDoneFragment)f).updateListView(data);
            }

            @Override
            public void onFail(ResponseDTO data) {

            }
        });
//        requestSSPDone(1, null, null, null, null,
//                rpc, new SuccessFailCallback<List<Sspdone>, ResponseError>() {
//            @Override
//            public void onSuccess(List<Sspdone> data) {
//                Fragment f = getFragment(SSPFragment.VIEW_PAGER_CODE);
//                if( f == null ) return;
//                f = ((SSPFragment) f).getFragment(SSPDoneFragment.VIEW_PAGER_CODE);
//                if( f == null ) return;
//                ((SSPDoneFragment)f).updateListView(data);
//            }
//
//            @Override
//            public boolean onFail(ResponseError data) {
//                return false;
//            }
//        });
    }

    private class MyBroadCastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context /*application*/, Intent intent) {
            int notifType = intent.getIntExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, 0);

            if(notifType == AppFirebaseService.NOTIF_TYPE_JUSTGENERATEBILLNUM) {

                MainActivity mainActivity = MainActivity.this;

                Sspunpaid sspunpaid = (Sspunpaid) intent.getSerializableExtra(AppConstant.ITN_NOTIFSSP_SSPUNPAID);
                final String title = intent.getStringExtra(AppConstant.ITN_NOTIFSSP_TITLE);
                String body = intent.getStringExtra(AppConstant.ITN_NOTIFSSP_BODY);

                if (!sspunpaid.taxslipresponseCode.equals(TaxSlipResponse.RESPONSECODE_OK))
                    body = sspunpaid.idBilling;
                final String bodyFi = body;

                updateSspBillNumber(sspunpaid, null);

                setupNotifCreateBillId(title, bodyFi, sspunpaid);

                if (mainActivity.isDestroyed() || mainActivity.isFinishing()) return;

                Fragment f = mainActivity.getFragment(SSPFragment.VIEW_PAGER_CODE);
                if (f == null) return;
                f = ((SSPFragment) f).getFragment(SSPUnpaidFragment.VIEW_PAGER_CODE);
                if (f == null) return;
                ((SSPUnpaidFragment) f).updateListData();
            }else if(notifType == AppFirebaseService.NOTIF_TYPE_PAIDSSP){
                setupNotifPaySSP(intent);
            }
        }

    }

    private void setupNotifCreateBillId(String title, String body, Sspunpaid sspunpaid){

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, AppFirebaseService.NOTIF_TYPE_JUSTGENERATEBILLNUM);
        intent.putExtra(AppConstant.ITN_NOTIFSSP_NOTIFDATE, System.currentTimeMillis());
        intent.putExtra(AppConstant.ITN_NOTIFSSP_SSPUNPAID, sspunpaid);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        popNotification(title, body, pendingIntent);
    }

    private void setupNotifPaySSP(Intent intent){
        long sspId = Long.parseLong( intent.getStringExtra(AppConstant.ITN_NOTIFPAY_SSPID) );
        String refNum = intent.getStringExtra(AppConstant.ITN_NOTIFPAY_REFNUM);
        String payStatus = intent.getStringExtra(AppConstant.ITN_NOTIFPAY_STATUS);

        successPaySSP();

        Intent itn = new Intent(this, MainActivity.class);
        itn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        itn.putExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, AppFirebaseService.NOTIF_TYPE_PAIDSSP);
        itn.putExtra(AppConstant.ITN_NOTIFSSP_NOTIFDATE, System.currentTimeMillis());
        itn.putExtra(AppConstant.ITN_NOTIFPAY_SSPID, sspId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, itn, PendingIntent.FLAG_UPDATE_CURRENT);

        String msg = getString(R.string.main_notiflabel_bodyfail, refNum);
        if(payStatus.equals(TransLog.STATUS_LUNAS)) msg = getString(R.string.main_notiflabel_bodysuccess, refNum);

        popNotification(getString(R.string.main_notiflabel_title), msg, pendingIntent);
    }

    private void popNotification(String title, String body, PendingIntent pendingIntent){

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AppConstant.NOTIF_CHANNEL_ID )
                .setSmallIcon(R.drawable.main_actionbar_logo)
                .setContentTitle( title )
                .setContentText( body )
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setDefaults(NotificationCompat.DEFAULT_SOUND|NotificationCompat.DEFAULT_LIGHTS|NotificationCompat.DEFAULT_VIBRATE)
                ;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(AppConstant.NTF_NORMAL, builder.build());

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notChannel = new NotificationChannel(AppConstant.NOTIF_CHANNEL_ID, title, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notChannel);
        }
    }

}
