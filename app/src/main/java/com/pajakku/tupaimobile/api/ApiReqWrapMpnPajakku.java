package com.pajakku.tupaimobile.api;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.dao.AppDatabase;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.ifc.QueryCallback;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.CommonDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.billing.BillingRetryDTO;
import com.pajakku.tupaimobile.model.dto.billing.ReqCekBillGenerate;
import com.pajakku.tupaimobile.model.dto.billing.RespKjs;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqCekBillPayment;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.RespMpnBillingStatus;
import com.pajakku.tupaimobile.model.dto.request.RequestSsp;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.MPNPaymentResponse;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.QueryAsyncTask;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by dul on 31/12/18.
 */

public final class ApiReqWrapMpnPajakku {

    // ---------- CEK PAY STATUS

    public static void cekPayStatus(final Activity act, RequestParamConfig rpcHttpFirst, final Long sspId, final String idBill, final String institutionCode, final String vaNumber, final CommonCallback<ResponseSsp> cb){

        rpcHttpFirst.forceRequest = false;
        ApiMain.httpFirst(act, rpcHttpFirst, new SuccessFailCallback<AppStatusData, ResponseDTO>() {
            @Override
            public void onSuccess(AppStatusData appStatusData) {
                getMpnPayStatus(act, sspId, appStatusData, idBill, institutionCode, vaNumber, cb);
            }

            @Override
            public void onFail(ResponseDTO data) {

            }
        });
    }

    private static void getMpnPayStatus(final Activity act, final Long sspId, AppStatusData appStatusData, final String idBill, final String institutionCode, final String vaNumber, final CommonCallback<ResponseSsp> cb){
        ApiReqMpnPajakku.mpnGetStatus(act, new RequestParamConfig(), appStatusData,
                idBill, new SuccessFailCallback<RespMpnBillingStatus, ResponseDTO>() {
                    @Override
                    public void onSuccess(RespMpnBillingStatus data) {
                        savePayStatusToTupai(act, sspId, data, institutionCode, vaNumber, cb);
                    }

                    @Override
                    public void onFail(ResponseDTO data) {
                        if(data.messageNotNull().contains("id yang ditentukan tidak ditemukan")){
                            Utility.toast(act, "Kode Billing siap dibayar");
                        }else Utility.toast(act, data.messageNotNull());
                    }
                });
    }

    private static void savePayStatusToTupai(final Activity act, Long sspId, RespMpnBillingStatus respMpnBillingStatus, String institutionCode, String vaNumber, final CommonCallback<ResponseSsp> cb){
        ReqCekBillPayment reqCek = new ReqCekBillPayment();
        reqCek.sspId = sspId;
        reqCek.institutionCode = institutionCode;
        reqCek.vaNumber = vaNumber;
        reqCek.payStatus = respMpnBillingStatus;
        ApiMain.getPayStatus(act, new RequestParamConfig(), reqCek, new CommonCallback<ResponseSsp>() {
            @Override
            public void onSuccess(ResponseSsp data) {
//                final String payStatus = data.payment.statusNotNull();
//                Utility.toast(act, payStatus);

                cb.onSuccess(data);

//                removeCache(AppConstant.SP_CACHEKEY_SSPUNPAID, true, new CommonCallback<Void>() {
//                    @Override
//                    public void onSuccess(Void data) {
//                        if( payStatus.equalsIgnoreCase(MPNPaymentResponse.BIL_PAID) ) {
//                            removeCache(AppConstant.SP_CACHEKEY_SSPDONE, true, new CommonCallback<Void>() {
//                                @Override
//                                public void onSuccess(Void data) {
//                                    context.finish();
//                                }
//                            });
//                        }
//                        else setViewData();
//                    }
//                });
            }
        });
    }

    // -----------------

    private ApiReqWrapMpnPajakku(){}

}
