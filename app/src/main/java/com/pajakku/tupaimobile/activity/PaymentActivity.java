package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.list.PayMethodAdapter;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.model.dto.PayMethod;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ActRtnMpnPajakku;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.model.dto.response.TransLog;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.SuccessFailCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PaymentActivity extends RepositoryActivity {

    private ResponseSsp sspunpaid;
    private TaxWPHeader taxWPHeader;
    private View invisView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initData(savedInstanceState);
//        if(savedInstanceState == null) {
//            Intent itn = getIntent();
//            sspunpaid = (Sspunpaid) itn.getSerializableExtra(AppConstant.ITN_SSPDETAILPAY_SSPUNPAID);
//        }else{
//            sspunpaid = (Sspunpaid) savedInstanceState.getSerializable(AppConstant.SP_DETAILACTIVIY_SSP);
//        }

        invisView = findViewById(R.id.payment_qhelp_invis);

        AppActionBar appActionBar = findViewById(R.id.pay_appactionbar);
        appActionBar.setBackFinish(this);
        appActionBar.setRightMenu( new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.payment_toprightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                switch (id){
                    case R.id.payment_btnmenu_quickhelp:
                        clickQuickHelp();
                        break;
                }
            }
        } );

        taxWPHeader = findViewById(R.id.pay_taxwpheader);
        taxWPHeader.setIcon(sspunpaid.fetchIcon(), sspunpaid.taxType.codeNotNull());
        taxWPHeader.setHeadName(sspunpaid.name);
        taxWPHeader.setNpwp(sspunpaid.npwp);
        taxWPHeader.setTaxAccountPeriod(sspunpaid);
        taxWPHeader.setAmount(sspunpaid.amount);

        PayMethod pm;
        List<PayMethod> listPaymethod = new ArrayList<>();
        pm = new PayMethod();  // ---
        pm.icon = R.drawable.ic_mpnlogo;
        pm.name = "MPN Pajakku";
        listPaymethod.add(pm);
//        pm = new PayMethod();  // ---
//        pm.icon = R.drawable.epay_bri;
//        pm.name = "E-Pay BRI";
//        listPaymethod.add(pm);
//        pm = new PayMethod();  // ---
//        pm.icon = R.drawable.logo_finnet;
//        pm.name = "Finnet";
//        listPaymethod.add(pm);

        PayMethodAdapter payMethodAdapter = new PayMethodAdapter(this);
        payMethodAdapter.addAll(listPaymethod);
        ListView listView = findViewById(R.id.pay_listview);
        listView.setAdapter(payMethodAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem( (PayMethod)parent.getItemAtPosition(position) );
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(AppConstant.SP_DETAILACTIVIY_SSP, sspunpaid);
    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
            Intent itn = getIntent();
            sspunpaid = (ResponseSsp) itn.getSerializableExtra(AppConstant.ITN_SSPDETAILPAY_SSPUNPAID);
        }else{
            sspunpaid = (ResponseSsp) savedInstanceState.getSerializable(AppConstant.SP_DETAILACTIVIY_SSP);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case AppConstant.ACTRES_EPAYBRI_PAY: {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
                break;
            }
            case AppConstant.ACTRES_MPN_PAJAKKU:

                Intent itn = new Intent();

                ActRtnMpnPajakku actRtn = (ActRtnMpnPajakku) data.getSerializableExtra(AppConstant.SP_ACTIVITYRESULT);
                if(actRtn != null){
                    itn.putExtra(AppConstant.SP_ACTIVITYRESULT, actRtn);
                }

                setResult(Activity.RESULT_OK, itn);
                finish();
                break;
        }

    }

    public void clickItem(PayMethod pm){
        final Activity context = this;
        switch (pm.icon){
            case R.drawable.ic_mpnlogo:
                ApiMain.generateMpnUrlShowWebX(this, sspunpaid, true, AppConstant.ACTRES_MPN_PAJAKKU);
//                ApiMain.generateMpnUrlShowWeb(this, sspunpaid, true, AppConstant.ACTRES_MPN_PAJAKKU);
//                RequestParamConfig rpc = new RequestParamConfig();
//                ApiMain.generateMpnUrl(this, rpc, new CommonCallback<MpnPajakkuUrlDTO>() {
//                    @Override
//                    public void onSuccess(MpnPajakkuUrlDTO data) {
//                        String androidUri = context.getString(R.string.deeplink_scheme) + AppConstant.DEEPLINK_SCHEME_SEPARATOR + context.getString(R.string.deeplink_host) +
//                                "?"+DeepLinkHandlerActivity.DEEPLINK_PARAM_SSPID+"="+sspunpaid.id;
//                        String mpnUrl = data.accessKey + "&billingCode="+ sspunpaid.billing.idBillingNotNull() +
//                                "&androidUri="+androidUri;
//                        Utility.log("url "+mpnUrl);
//                        MPNPajakkuActivity.startAct(context, mpnUrl, AppConstant.ACTRES_MPN_PAJAKKU);
//                    }
//                });
                break;
            case R.drawable.epay_bri:
                startEPayBriTransaction();
                break;
            case R.drawable.logo_finnet: {
                sspPayConfirm(sspunpaid, new SuccessFailCallback<ResponseSsp,ResponseError>() {
                    @Override
                    public void onSuccess(ResponseSsp data) {
                        Utility.toast(PaymentActivity.this, R.string.sspdetailunpaid_toast_successpayssp);
                        successPaySSP();
                    }
                    @Override
                    public boolean onFail(ResponseError e){
                        return true;
                    }
                });
            } break;
        }
    }

    private void successPaySSP(){
        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                removeCache(AppConstant.SP_CACHEKEY_SSPDONE, true, new CommonCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        removeCache(AppConstant.SP_CACHEKEY_EMONBALANCE, true, new CommonCallback<Void>() {
                            @Override
                            public void onSuccess(Void data) {
                                PaymentActivity.this.setResult(Activity.RESULT_OK, new Intent());
                                PaymentActivity.this.finish();
                            }
                        });
                    }
                });
            }
        });
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(this, getString(R.string.pay_qhelptitle_taxheader), getString(R.string.pay_qhelpbody_taxheader), (View)taxWPHeader.getParent(), taxWPHeader);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(this, getString(R.string.pay_qhelptitle_paymethod), getString(R.string.pay_qhelpbody_paymethod), (View)invisView.getParent(), invisView);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    // TODO: @test
    @Deprecated
    private void showEPaySimlation(String billId, long amount, String tx){
        Intent i = new Intent(this, CommonWebActivity.class);
        i.putExtra(CommonWebActivity.TESTITN_PAYSIMUL_BILLID, billId);
        i.putExtra(CommonWebActivity.TESTITN_PAYSIMUL_AMOUNT, amount);
        i.putExtra(CommonWebActivity.TESTITN_PAYSIMUL_TRAXID, tx);
        startActivityForResult(i, AppConstant.ACTRES_EPAYBRI_PAY);
    }

    private void startEPayBriTransaction(){

        saveFCMTokenToServerValidate(new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                requestHttpSimple(true, R.string.progressdialog_starttransaction, true, null, true, new HttpCallbackInterfaceSimple<TransLog>() {
                    @Override
                    public Call<TransLog> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                        return httpService.startTransaction(bearerAuth, sspunpaid.id);
                    }

                    @Override
                    public void onSuccess(TransLog response) {
                        // TODO: @prod
//                      showEpayBri(sspunpaid.idBilling, sspunpaid.amount, response.refId);

                        // TODO: @test
                        showEPaySimlation(sspunpaid.idBilling, sspunpaid.amount, response.refId);

                    }

                    @Override
                    public boolean onFail(ResponseError error) {
                        return true;
                    }
                });
            }
        });

    }

//    private void showEpayBri(String billId, long amount, String tx){
//
//        String url = "https://sandbox.bri.co.id/staging/epay_aggregator_mpn/?merchant_id="+AppConstant.MERCHANT_ID+"&id_billing="+billId+"&redirect="+EPayBRIWebActivity.redirectStr+"&amount="+amount+"&id_transaction="+tx;
//        Log.d(AppConstant.LOG_TAG, "url: "+url);
//        Intent intent = new Intent(this, EPayBRIWebActivity.class);
//        intent.putExtra(AppConstant.ITN_PAYEPAYBRIWEB_URL, url);
//        startActivityForResult(intent, AppConstant.ACTRES_EPAYBRI_PAY);
//    }

    public static void startAct(Activity act, int actRes, ResponseSsp respSsp){
        Intent itn = new Intent(act, PaymentActivity.class);
        itn.putExtra(AppConstant.ITN_SSPDETAILPAY_SSPUNPAID, respSsp);
        act.startActivityForResult(itn, actRes);
    }
}
