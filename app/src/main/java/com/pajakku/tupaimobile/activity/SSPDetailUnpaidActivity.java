package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.api.ApiReqBilling;
import com.pajakku.tupaimobile.api.ApiReqMpnPajakku;
import com.pajakku.tupaimobile.api.ApiReqWrapMpnPajakku;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.SSPDetailRowInfo;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.actdata.ActDataSspDetailUnpaid;
import com.pajakku.tupaimobile.model.actparam.ActParamSspDetailUnpaid;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.billing.BillingRetryDTO;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ActRtnMpnPajakku;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqCekBillPayment;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.RespMpnBillingStatus;
import com.pajakku.tupaimobile.model.dto.request.UpdateTaxReceiptDTO;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.MPNPaymentResponse;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnetFrame;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.AppFirebaseService;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class SSPDetailUnpaidActivity extends RepositoryActivity {
    private static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_SSP_DETAIL_BELUM_BAYAR;

//    private static final int ACTMODE_GENERATE_BILLID = 1;
//    private static final int ACTMODE_PAY_SSP = 2;
//    private static final int ACTMODE_SSPCOULDNOTGETBILLID = 3;
//    private static final int ACTMODE_PAY_SSP_DONE = 4;

    private ActDataSspDetailUnpaid actData;

    private NewGenerateBillNumBReceiver broadCastReceiver;

//    private int actMode = ACTMODE_GENERATE_BILLID;
//    private Sspunpaid sspunpaid;

    private TaxWPHeader taxWPHeader;

    private SSPDetailRowInfo valRowStatus;
    private SSPDetailRowInfo rowBillCode;
    private SSPDetailRowInfo rowDetailNpwp;
    private SSPDetailRowInfo rowDetailRefNo;
    private SSPDetailRowInfo rowDetailName;
    private SSPDetailRowInfo rowDetailAddress;
    private SSPDetailRowInfo rowDetailKapKjs;
    private SSPDetailRowInfo rowDetailTaxPeriod;
    private SSPDetailRowInfo rowDetailSetor;
    private SSPDetailRowInfo rowDetailPenyetor;

    private AppCompatButton btnReload;

    private Button btnCheckBill;
    private AppCompatButton btnProceedPay;
    private AppCompatButton btnCheckPayStatus;
    private AppCompatButton btnViewPayment;
    private AppCompatButton btnRefund;

    private View invisView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_sspdetailunfinish);

        initData(savedInstanceState);

        broadCastReceiver = new NewGenerateBillNumBReceiver();

//        sspunpaid = (sspunpaid != null ? sspunpaid : new Sspunpaid());

        invisView = findViewById(R.id.sspdetailunpaid_qhelp_invis);

        final AppActionBar actionBar = findViewById(R.id.sspdetailunfinish_actionbar);
        actionBar.setBackFinish(this);
        actionBar.setRightMenu(new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.sspdetailunpaid_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                switch (id){
                    case R.id.sspdetailunpaid_btnmenu_quickhelp:
                        clickQuickHelp();
                        break;
                    case R.id.sspdetail_btnmenu_delssp:
//                        deleteSSPConfirm(sspunpaid.id, "");
                        deleteSSPConfirm(actData.ap.sspId, "");
                        break;
                }
            }
        });

        taxWPHeader = findViewById(R.id.sspdetailunpaid_taxwpheader);

        valRowStatus = findViewById(R.id.sspdetailunfinish_label_row_status);
        rowBillCode = findViewById(R.id.sspdetailunfinish_label_row_billcode);
        rowDetailNpwp = findViewById(R.id.sspdetailunfinish_label_row_npwp);
        rowDetailRefNo = findViewById(R.id.sspdetailunfinish_label_row_refno);
//        labelRow = findViewById(R.id.sspdetailunfinish_label_row_sspdate);
//        labelRow.setCallback( sspunpaid.updatedAt );
        rowDetailName = findViewById(R.id.sspdetailunfinish_label_row_name);
        rowDetailAddress = findViewById(R.id.sspdetailunfinish_label_row_address);
        rowDetailKapKjs = findViewById(R.id.sspdetailunfinish_label_row_kapkjs);
        rowDetailTaxPeriod = findViewById(R.id.sspdetailunfinish_label_row_taxperiod);
        rowDetailSetor = findViewById(R.id.sspdetailunfinish_label_row_setor);
        rowDetailPenyetor = findViewById(R.id.sspdetailunfinish_label_row_npwppenyetor);

        btnReload = findViewById(R.id.sspdetailunfinish_btn_reloader);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewData();
            }
        });

        btnCheckBill = findViewById(R.id.sspdetailunfinish_btn_check_bill);
        btnCheckBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clickBotBtn();
                generateBillNum();
            }
        });

        btnProceedPay = findViewById(R.id.sspdetailunfinish_btn_proceed_pay);
        btnProceedPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentActivity.startAct(context, AppConstant.ACTRES_SSPUNPAID_DETAIL_PAY, actData.responseSsp);
            }
        });

        btnCheckPayStatus = findViewById(R.id.sspdetailunfinish_btn_cekbillstatus);
        btnCheckPayStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clickCekPayStatus(actData.responseSsp.billing.idBillingNotNull(), null, null);
                ApiReqWrapMpnPajakku.cekPayStatus(context, new RequestParamConfig(), actData.ap.sspId, actData.responseSsp.billing.idBillingNotNull(),
                        null, null, new CommonCallback<ResponseSsp>() {
                            @Override
                            public void onSuccess(ResponseSsp data) {
                                successCekPayStatus(data);
                            }
                        });
            }
        });

        btnViewPayment = findViewById(R.id.sspdetailunfinish_btn_view_payment);
        btnViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiMain.generateMpnUrlShowWebX(context, actData.responseSsp, false, AppConstant.ACTRES_MPN_PAJAKKU);
//                ApiMain.generateMpnUrlShowWeb(context, actData.responseSsp, false, AppConstant.ACTRES_MPN_PAJAKKU);
            }
        });

        btnRefund = findViewById(R.id.sspdetailunfinish_btn_refund);
        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefundFormActivity.startAct(context, actData.responseSsp);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

//        setViewData();
    }

    @Override
    protected void onStart(){
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiver, new IntentFilter(AppConstant.ITNFILTER_TUPAINOTIF));
    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
            Intent itn = getIntent();
            ActParamSspDetailUnpaid ap = (ActParamSspDetailUnpaid) itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataSspDetailUnpaid();
            actData.ap = ap;
        }else{
            actData = (ActDataSspDetailUnpaid) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ACTDATA_KEY, actData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Activity context = this;

        switch (requestCode)
        {
//            case AppConstant.ACTRES_SSPUNPAID_DETAIL_PAY: {
//                if (resultCode == RESULT_OK) {
//                    setResult(Activity.RESULT_OK, new Intent());
//                    finish();
//                }
//                break;
//            }
            case AppConstant.ACTRES_MPN_PAJAKKU:
            case AppConstant.ACTRES_SSPUNPAID_DETAIL_PAY: {
                if (resultCode == RESULT_OK) {
                    ActRtnMpnPajakku actRtn = (ActRtnMpnPajakku) data.getSerializableExtra(AppConstant.SP_ACTIVITYRESULT);
                    if(actRtn == null){
                        actRtn = new ActRtnMpnPajakku();
                    }
//                    clickCekPayStatus(actData.responseSsp.billing.idBillingNotNull(), actRtn.institutionCode, actRtn.vaNumber);
                    ApiReqWrapMpnPajakku.cekPayStatus(this, new RequestParamConfig(), actData.ap.sspId, actData.responseSsp.billing.idBillingNotNull(),
                            actRtn.institutionCode, actRtn.vaNumber, new CommonCallback<ResponseSsp>() {
                                @Override
                                public void onSuccess(ResponseSsp data) {
                                    successCekPayStatus(data);
                                }
                            });
                }
                break;
            }
        }

    }

    @Override
    protected void setViewData(){
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;
        rpc.reloader = btnReload;
        ApiMain.getSingleSSPX(this, rpc, actData.ap.sspId,
                new com.pajakku.tupaimobile.ifc.SuccessFailCallback<ResponseSsp, ResponseDTO>() {
            @Override
            public void onSuccess(ResponseSsp data) {
                actData.responseSsp = data;
                setViewData(actData.responseSsp);
            }

            @Override
            public void onFail(ResponseDTO data) {
                httpReqErrorView();
            }
        });

    }

    private void successCekPayStatus(ResponseSsp data){
        final String payStatus = data.payment.statusNotNull();
        Utility.toast(this, payStatus);

        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                if( payStatus.equalsIgnoreCase(MPNPaymentResponse.BIL_PAID) ) {
                    removeCache(AppConstant.SP_CACHEKEY_SSPDONE, true, new CommonCallback<Void>() {
                        @Override
                        public void onSuccess(Void data) {
                            finish();
                        }
                    });
                }
                else setViewData();
            }
        });
    }

    private void httpReqErrorView(){
        btnCheckBill.setVisibility(View.GONE);
        btnCheckPayStatus.setVisibility(View.GONE);
        btnViewPayment.setVisibility(View.GONE);
        btnRefund.setVisibility(View.GONE);
    }

    private void setViewData(ResponseSsp respSsp){

        taxWPHeader.setIcon(respSsp.fetchIcon(), respSsp.taxType.codeNotNull());
        taxWPHeader.setHeadName(respSsp.name);
        taxWPHeader.setNpwp(respSsp.npwp);
        taxWPHeader.setTaxAccountPeriod(respSsp);

        valRowStatus.init( respSsp.statusShort() );

        if(respSsp.billing.idBillingNotNull().isEmpty()){
            rowBillCode.hideValidUntil();
        }else{
            rowBillCode.setValidUntil( respSsp.billing.expiredDate );
        }

//        rowBillCode.init( respSsp.billing.idBillingNotNull().isEmpty() ? respSsp.billing.statusNotNull() : respSsp.billing.idBillingNotNull() );
        rowBillCode.init( respSsp.billing.idBillingNotNull() );

        rowDetailNpwp.init( Utility.toPrettyNpwp(respSsp.npwp) );
        rowDetailRefNo.init( respSsp.referenceNoNotNull() );
        rowDetailName.init(respSsp.name);
        rowDetailAddress.init(respSsp.address);
        rowDetailKapKjs.init( respSsp.fetchKapKjs(this) );
        rowDetailTaxPeriod.init( respSsp.fetchTaxPeriod(this) );
        rowDetailSetor.init( Utility.toMoney(true, respSsp.amount) );
        if(respSsp.npwpPenyetor != null){
            if( ! respSsp.npwpPenyetor.isEmpty() && ! respSsp.npwpPenyetor.equals(respSsp.npwp) ){
                rowDetailPenyetor.init( Utility.toPrettyNpwp(respSsp.npwpPenyetor) );
                rowDetailPenyetor.setVisibility(View.VISIBLE);
            }else{
                rowDetailPenyetor.setVisibility(View.GONE);
            }
        }else{
            rowDetailPenyetor.setVisibility(View.GONE);
        }

        int visCheckBill = View.VISIBLE;
        int visProceedPay = View.VISIBLE;

        if( respSsp.billing.idBillingNotNull().isEmpty() ){
            visCheckBill = View.VISIBLE;
            visProceedPay = View.GONE;
        } else {
            visCheckBill = View.GONE;
            if( respSsp.isCanPay() ) {
                visProceedPay = View.VISIBLE;
            }else{
                visProceedPay = View.GONE;
            }
        }

        btnCheckBill.setVisibility( visCheckBill );
        btnProceedPay.setVisibility( visProceedPay );

//        btnCheckPayStatus.setVisibility( respSsp.isPayProgress() ||
//                ( ! respSsp.billing.isExpired() && respSsp.createdAtAgo(10) && !respSsp.isRefunded() ) ? View.VISIBLE : View.GONE );
        btnCheckPayStatus.setVisibility( ! respSsp.billing.isExpired() && !respSsp.isRefunded() ? View.VISIBLE : View.GONE );
        btnViewPayment.setVisibility( respSsp.isPayProgress() ? View.VISIBLE : View.GONE );

        btnRefund.setVisibility( respSsp.isShowRefundBtn() ? View.VISIBLE : View.GONE );
    }

    private void updateSspunpaidBillNumber(Sspunpaid notifSsp){
//        if(notifSsp.id != sspunpaid.id) return;
//
//        sspunpaid.taxslipresponseCode = notifSsp.taxslipresponseCode;
//        sspunpaid.idBilling = notifSsp.idBilling;
//        sspunpaid.billNumExpired = notifSsp.billNumExpired;

        setViewData();
    }

//    private void clickBotBtn(){
//        if( actData.responseSsp.isCanPay() ){
//            PaymentActivity.startAct(this, AppConstant.ACTRES_SSPUNPAID_DETAIL_PAY, actData.responseSsp);
//        }else{
//            generateBillNum();
//        }
//    }

    @Deprecated
    private void clickCekPayStatus(final String idBill, final String institutionCode, final String vaNumber){
        final Activity context = this;

        RequestParamConfig rpcHttpFirst = new RequestParamConfig();
        rpcHttpFirst.forceRequest = false;
        ApiMain.httpFirst(context, rpcHttpFirst, new SuccessFailCallback<AppStatusData, ResponseDTO>() {
            @Override
            public void onSuccess(AppStatusData appStatusData) {
                getMpnPayStatus(appStatusData, idBill, institutionCode, vaNumber);
            }

            @Override
            public void onFail(ResponseDTO data) {

            }
        });
    }

    @Deprecated
    private void getMpnPayStatus(AppStatusData appStatusData, final String idBill, final String institutionCode, final String vaNumber){
        final Activity context = this;
        ApiReqMpnPajakku.mpnGetStatus(context, new RequestParamConfig(), appStatusData,
                idBill, new SuccessFailCallback<RespMpnBillingStatus, ResponseDTO>() {
                    @Override
                    public void onSuccess(RespMpnBillingStatus data) {
                        savePayStatusToTupai(data, institutionCode, vaNumber);
                    }

                    @Override
                    public void onFail(ResponseDTO data) {
                        if(data.messageNotNull().contains("id yang ditentukan tidak ditemukan")){
                            Utility.toast(context, "Kode Billing siap dibayar");
                        }else Utility.toast(context, data.messageNotNull());
                    }
                });
    }

    @Deprecated
    private void savePayStatusToTupai(RespMpnBillingStatus respMpnBillingStatus, String institutionCode, String vaNumber){
        final Activity context = this;
        ReqCekBillPayment reqCek = new ReqCekBillPayment();
        reqCek.sspId = actData.ap.sspId;
        reqCek.institutionCode = institutionCode;
        reqCek.vaNumber = vaNumber;
        reqCek.payStatus = respMpnBillingStatus;
        ApiMain.getPayStatus(this, new RequestParamConfig(), reqCek, new CommonCallback<ResponseSsp>() {
            @Override
            public void onSuccess(ResponseSsp data) {
                final String payStatus = data.payment.statusNotNull();
                Utility.toast(context, payStatus);

                removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        if( payStatus.equalsIgnoreCase(MPNPaymentResponse.BIL_PAID) ) {
                            removeCache(AppConstant.SP_CACHEKEY_SSPDONE, true, new CommonCallback<Void>() {
                                @Override
                                public void onSuccess(Void data) {
                                    context.finish();
                                }
                            });
                        }
                        else setViewData();
                    }
                });
            }
        });
    }

//    private void showPaymentMethod(){
//        Intent i = new Intent(this, PaymentActivity.class);
//        i.putExtra(AppConstant.ITN_SSPDETAILPAY_SSPUNPAID, sspunpaid);
//        startActivityForResult(i, AppConstant.ACTRES_SSPUNPAID_DETAIL_PAY);
//    }

    private void generateBillNum(){

        ApiReqBilling.cekBillGenerate(this, new RequestParamConfig(), actData.responseSsp.id,
                actData.responseSsp.referenceNo, new CommonCallback<BillingRetryDTO>() {
            @Override
            public void onSuccess(BillingRetryDTO data) {
                successExecuteBilling(data);
            }
        });

//        requestHttpSimple(true, R.string.progressdialog_checksspstatus, true, null, true, new HttpCallbackInterfaceSimple<BillingRetryDTO>() {
//            @Override
//            public Call<BillingRetryDTO> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
//                return httpService.executeBilling(bearerAuth, "", actData.responseSsp.id, actData.responseSsp.referenceNo);
//            }
//
//            @Override
//            public void onSuccess(BillingRetryDTO response) {
//                successExecuteBilling(response);
//            }
//
//            @Override
//            public boolean onFail(ResponseError error) {
//                return true;
//            }
//        });

    }

    private void successExecuteBilling(BillingRetryDTO resp){
        String billNum = resp.billing.idBillingNotNull();

        if( billNum.isEmpty() ){
            Utility.toast(this, R.string.sspdetailunpaid_toast_nobillnumberyet);
            return;
        }

//        sspunpaid.idBilling = billNum;
//        sspunpaid.billNumExpired = resp.billing.expiredDate;
        setViewData();

        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                requestUnpaidSSP(null);  // fetch list data dari server utk disimpan di cache lokal
            }
        });

    }

    private void requestUnpaidSSP(final CommonCallback<Void> callback){
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.reqPaging.page = 1L;
        ApiMain.getSspUnpaid(this, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Sspunpaid>, ResponseDTO>() {
            @Override
            public void onSuccess(List<Sspunpaid> data) {
                if(callback != null) callback.onSuccess(null);
            }

            @Override
            public void onFail(ResponseDTO data) {
                if(callback != null) callback.onSuccess(null);

            }
        });
//        requestUnpaidSSP(1, null, null, null,
//                null, rpc, new SuccessFailCallback<List<Sspunpaid>, ResponseError>() {
//            @Override
//            public void onSuccess(List<Sspunpaid> data) {
//                if(callback != null) callback.onSuccess(null);
//            }
//
//            @Override
//            public boolean onFail(ResponseError data) {
//                if(callback != null) callback.onSuccess(null);
//                return true;
//            }
//        });
    }

    private void successPaySSP(Date bookDate, ResponseSsp data){
//        sspunpaid.ntpn = data.customerData.ntpn;
//        setViewData();
//
//        Date payDate = new Date();
//        updateTaxReceipt(bookDate, payDate, data);

        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                removeCache(AppConstant.SP_CACHEKEY_SSPDONE, true, new CommonCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        removeCache(AppConstant.SP_CACHEKEY_EMONBALANCE, true, new CommonCallback<Void>() {
                            @Override
                            public void onSuccess(Void data) {
                                Toast.makeText(SSPDetailUnpaidActivity.this, R.string.sspdetailunpaid_toast_successpayssp, Toast.LENGTH_LONG).show();
                                SSPDetailUnpaidActivity.this.finish();
                            }
                        });
                    }
                });
            }
        });
    }

    private void updateTaxReceipt(Date bookDate, Date payDate, ResponseFinnetFrame rff){
        final UpdateTaxReceiptDTO taxReceipt = new UpdateTaxReceiptDTO();
        taxReceipt.referenceNo = rff.traxId;
        taxReceipt.ntpn = rff.customerData.ntpn;
        taxReceipt.ntb = rff.traxId;
        taxReceipt.bookDate = Utility.dateToString(bookDate);
        taxReceipt.paymentDateTime = Utility.dateToString(payDate);
        taxReceipt.taxSlipsId = actData.ap.sspId;
        requestHttpSimple(true, R.string.progressdialog_saventpn, true, null, true, new HttpCallbackInterfaceSimple<ResponseBody>() {
            @Override
            public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.updateTaxReceipt(bearerAuth, taxReceipt);
            }

            @Override
            public void onSuccess(ResponseBody response) {
                successUpdateTaxReceipt();
            }

            @Override
            public boolean onFail(ResponseError error) {
                SSPDetailUnpaidActivity.this.finish();
                return true;
            }
        });
    }

    private void successUpdateTaxReceipt() {
        Toast.makeText(this, R.string.paypphsummary_toast_successpayssp, Toast.LENGTH_LONG).show();
        removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                removeCache(AppConstant.SP_CACHEKEY_SSPDONE, true, new CommonCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        requestUnpaidSSP(new CommonCallback<Void>() {
                            @Override
                            public void onSuccess(Void data) {
                                SSPDetailUnpaidActivity.this.finish();
                            }
                        });
                    }
                });
            }
        });

    }

    private class NewGenerateBillNumBReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context /*application*/, Intent intent) {
            int notifType = intent.getIntExtra(AppConstant.ITN_NOTIFX_NOTIFTYPE, 0);

            if(notifType != AppFirebaseService.NOTIF_TYPE_JUSTGENERATEBILLNUM) return;

            SSPDetailUnpaidActivity act = SSPDetailUnpaidActivity.this;

            if( act.isDestroyed() || act.isFinishing() ) return;

            Sspunpaid notifSsp = (Sspunpaid)intent.getSerializableExtra(AppConstant.ITN_NOTIFSSP_SSPUNPAID);

            updateSspunpaidBillNumber(notifSsp);
        }

    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(this, getString(R.string.sspdetailunpaid_qhelptitle_data), getString(R.string.sspdetailunpaid_qhelpbody_data), (View)invisView.getParent(), invisView);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(this, getString(R.string.sspdetailunpaid_qhelptitle_botbtnpay), getString(R.string.sspdetailunpaid_qhelpbody_botbtnpay), (View) btnCheckBill.getParent(), btnCheckBill);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    public static void startAct(Activity act, int actRes, Sspunpaid sspunpaid){
        ActParamSspDetailUnpaid ap = new ActParamSspDetailUnpaid();
        ap.sspId = sspunpaid.id;

        Intent intent = new Intent(act, SSPDetailUnpaidActivity.class);
        intent.putExtra(ACTDATA_KEY, ap);
        act.startActivityForResult(intent, actRes);
    }
}
