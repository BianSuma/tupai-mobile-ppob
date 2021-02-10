package com.pajakku.tupaimobile.api;

import android.app.Activity;
import android.net.Uri;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqMpnRefund;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.RespMpnBillingStatus;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.SharePref;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by dul on 31/12/18.
 */

public final class ApiReqMpnPajakku {

    public static void mpnGetStatus(final Activity ctx, RequestParamConfig rpc, final AppStatusData appStatusData,
                                    final String idBill, final SuccessFailCallback<RespMpnBillingStatus, ResponseDTO> cb){
        rpc.progressTextRes = R.string.progressdialog_common;
        rpc.isRelogin = true;
        rpc.forceRequest = true;
        rpc.isShowFailToast = false;

        final String uri = Uri.parse( appStatusData.api.mpnPajakkuHostNotNull() )
                .buildUpon()
                .appendPath( appStatusData.api.mpnPajakkuBaseNotNull() )
                .appendPath("mpn")
                .appendPath( idBill )
                .appendPath("payment_code")
                .build().toString();

        HttpReq.run(ctx, SharePref.SPRK_MPNPAJAKKU_PAYSTATUS, rpc, new HttpCacheCallback<RespMpnBillingStatus, RespMpnBillingStatus>(){
            public Call<RespMpnBillingStatus> requestMethod(String bearerAuth){
                return HttpReq.getApiCallMpnPajakku(ctx).mpnGetStatus(uri, bearerAuth, appStatusData.api.mpnPajakkuClientIdNotNull());
            }

            @Override
            public void onSuccess(RespMpnBillingStatus data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public RespMpnBillingStatus apiConvertResponse(RespMpnBillingStatus d) {
                return d;
            }

            @Override
            public void setCache(RespMpnBillingStatus d) {

            }

            @Override
            public void getCache(CommonCallback<RespMpnBillingStatus> cacheCb) {

            }
        });
    }

    public static void refund(final Activity ctx, RequestParamConfig rpc, final AppStatusData appStatusData,
                              final ReqMpnRefund body, final CommonCallback<ResponseBody> cb){
        rpc.progressTextRes = R.string.progressdialog_common;
        rpc.isRelogin = true;
        rpc.forceRequest = true;

        final String uri = Uri.parse( appStatusData.api.mpnPajakkuHostNotNull() )
                .buildUpon()
                .appendPath( appStatusData.api.mpnPajakkuBaseNotNull() )
                .appendPath("refund")
                .build().toString();

        HttpReq.run(ctx, SharePref.SPRK_MPNPAJAKKU_REFUND, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiCallMpnPajakku(ctx).refund(uri, bearerAuth, appStatusData.api.mpnPajakkuClientIdNotNull(), body);
            }

            @Override
            public void onSuccess(ResponseBody data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public ResponseBody apiConvertResponse(ResponseBody d) {
                return d;
            }

            @Override
            public void setCache(ResponseBody d) {

            }

            @Override
            public void getCache(CommonCallback<ResponseBody> cacheCb) {

            }
        });
    }

    private ApiReqMpnPajakku(){}

}
