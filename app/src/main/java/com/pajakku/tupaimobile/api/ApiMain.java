package com.pajakku.tupaimobile.api;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.reflect.TypeToken;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.DeepLinkHandlerActivity;
import com.pajakku.tupaimobile.activity.MPNPajakkuActivity;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.CommonDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.MpnPajakkuUrlDTO;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqCekBillPayment;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqRefund;
import com.pajakku.tupaimobile.model.dto.request.FirebaseToken;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by dul on 31/12/18.
 */

public final class ApiMain {

    @Deprecated  // tdk akan dipakai
    public static void generateMpnUrl(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<MpnPajakkuUrlDTO> cb){
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;
        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_GENERATE_MPN_URL;
        HttpReq.run(ctx, SharePref.SPRK_GENERATE_MPN_URL, rpc, new HttpCacheCallback<ResponseBody, MpnPajakkuUrlDTO>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(MpnPajakkuUrlDTO data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public MpnPajakkuUrlDTO apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), MpnPajakkuUrlDTO.class);
                }catch (Exception e){
                    Utility.log("2020 0806 1424 "+e.getMessage());
                    return new MpnPajakkuUrlDTO();
                }
            }

            @Override
            public void setCache(MpnPajakkuUrlDTO d) {

            }

            @Override
            public void getCache(CommonCallback<MpnPajakkuUrlDTO> cacheCb) {

            }
        });
    }

    @Deprecated  // generateMpnUrlShowWebX
    public static void generateMpnUrlShowWeb(final Activity act, final ResponseSsp respSsp, final boolean isWithIdBill, final int actRes){
        RequestParamConfig rpc = new RequestParamConfig();
        ApiMain.generateMpnUrl(act, rpc, new CommonCallback<MpnPajakkuUrlDTO>() {
            @Override
            public void onSuccess(MpnPajakkuUrlDTO data) {
                String androidUri = act.getString(R.string.deeplink_scheme) + AppConstant.DEEPLINK_SCHEME_SEPARATOR + act.getString(R.string.deeplink_host) +
                        "?"+ DeepLinkHandlerActivity.DEEPLINK_PARAM_SSPID+"="+respSsp.id;
                String paramIdBill = "";
                if(isWithIdBill) paramIdBill = "&billingCode="+ respSsp.billing.idBillingNotNull();
                String mpnUrl = data.accessKey +
                        paramIdBill +
//                        "&widgetClientId=O5Xl2AWpSi7Lt6ogdRf5qVYzTu0I4yIrXkpf68dY"+
                        "&androidUri="+androidUri;
                Utility.log("url "+mpnUrl);
                MPNPajakkuActivity.startAct(act, mpnUrl, actRes, null);
//                        Utility.gotoWebsite(context, mpnUrl);
            }
        });
    }

    public static void generateMpnUrlShowWebX(final Activity act, final ResponseSsp respSsp,
                                              final boolean isWithIdBill, final int actRes){
            RequestParamConfig rpc = new RequestParamConfig();
            rpc.forceRequest = false;
            httpFirst(act, rpc, new SuccessFailCallback<AppStatusData, ResponseDTO>() {
                @Override
                public void onSuccess(AppStatusData appStatusData) {

                    String authToken = SharePref.getStr(act, AppConstant.SP_AUTH_TOKEN);
                    Uri androiUri = new Uri.Builder()
                            .scheme( act.getString(R.string.deeplink_scheme) )
                            .authority( act.getString(R.string.deeplink_host) )
                            .appendQueryParameter(DeepLinkHandlerActivity.DEEPLINK_PARAM_SSPID, Long.toString(respSsp.id) )
                            .build();
                    Utility.log("androidUri "+androiUri.toString());
                    Uri.Builder uriBuilder = Uri.parse( appStatusData.api.mpnPajakkuWidgetNotNull() )
                            .buildUpon()
                            .appendQueryParameter(MPNPajakkuActivity.HTTPQUERY_ACCESSTOKEN, authToken)
                            .appendQueryParameter(MPNPajakkuActivity.HTTPQUERY_ANDROID_URI, androiUri.toString())
                            .appendQueryParameter(MPNPajakkuActivity.HTTPQUERY_WIDGET_CLIENT_ID, appStatusData.api.mpnPajakkuClientIdNotNull());

                    if(isWithIdBill) uriBuilder = uriBuilder.appendQueryParameter(MPNPajakkuActivity.HTTPQUERY_BILLCODE, respSsp.billing.idBillingNotNull());

                    MPNPajakkuActivity.startAct(act, uriBuilder.build().toString(), actRes, appStatusData);
                }

                @Override
                public void onFail(ResponseDTO data) {

                }
            });

    }

    public static void getPayStatus(final Activity ctx, final RequestParamConfig rpc, ReqCekBillPayment reqCekBillPay, final CommonCallback<ResponseSsp> cb){
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;
        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_STATUS_BILLING;
        body.json = Utility.gson().toJson(reqCekBillPay);
        HttpReq.run(ctx, SharePref.SPRK_GET_PAY_BILLING_STATUS, rpc, new HttpCacheCallback<ResponseBody, ResponseSsp>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(ResponseSsp data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public ResponseSsp apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), ResponseSsp.class);
                }catch (Exception e){
                    Utility.log("2020_0806_1433 "+e.getMessage());
                    return new ResponseSsp();
                }
            }

            @Override
            public void setCache(ResponseSsp d) {

            }

            @Override
            public void getCache(CommonCallback<ResponseSsp> cacheCb) {

            }
        });
    }

    @Deprecated
    public static void mpnPajakkuRefund(final Activity ctx, final RequestParamConfig rpc, ReqRefund reqRefund, final CommonCallback<ResponseSsp> cb){
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;
        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_MPN_REFUND;
        body.json = Utility.gson().toJson(reqRefund);
        HttpReq.run(ctx, SharePref.SPRK_MPN_REFUND, rpc, new HttpCacheCallback<ResponseBody, ResponseSsp>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(ResponseSsp data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public ResponseSsp apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), ResponseSsp.class);
                }catch (Exception e){
                    Utility.log("2020 0811 1337 "+e.getMessage());
                    return new ResponseSsp();
                }
            }

            @Override
            public void setCache(ResponseSsp d) {

            }

            @Override
            public void getCache(CommonCallback<ResponseSsp> cacheCb) {

            }
        });
    }

//    public static void getKjp(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<List<RespListTaxType>> cb){
//        ReqPaging rp = new ReqPaging();
//        rp.page = rpc.page;
//        rp.size = AppConstant.LIST_SIZE;
//
//        final CommonDTO body = new CommonDTO();
//        body.code = CommonDTO.CODE_LIST_KJP;
//        body.json = Utility.gson().toJson(rp);
//        HttpReq.run(ctx, SharePref.SPRK_GET_LIST_TAX_TYPE, rpc, new HttpCacheCallback<ResponseBody, List<RespListTaxType>>(){
//            public Call<ResponseBody> requestMethod(String bearerAuth){
//                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
//            }
//
//            @Override
//            public void onSuccess(List<RespListTaxType> data) {
//                cb.onSuccess(data);
//            }
//
//            @Override
//            public void onFail(ResponseDTO error){
//
//            }
//
//            @Override
//            public List<RespListTaxType> apiConvertResponse(ResponseBody d) {
//                try {
//                    return Utility.gson().fromJson(d.string(), new TypeToken<List<RespListTaxType>>(){}.getType() );
//                }catch (Exception e){
//                    Utility.log("2020_0819_1538 "+e.getMessage());
//                    return new ArrayList<>();
//                }
//            }
//
//            @Override
//            public void setCache(List<RespListTaxType> d) {
//
//            }
//
//            @Override
//            public void getCache(CommonCallback<List<RespListTaxType>> cacheCb) {
//
//            }
//        });
//    }

    // getKjp
    @Deprecated
    public static void getTaxType(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<List<RespListTaxType>> cb){
//        rpc.progressTextRes = R.string.progressdialog_common;
        HttpReq.run(ctx, SharePref.SPRK_GET_LIST_TAX_TYPE, rpc, new HttpCacheCallback<List<RespListTaxType>, List<RespListTaxType>>(){
            public Call<List<RespListTaxType>> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).getTaxType(bearerAuth, rpc.reqPaging.page, AppConstant.LIST_SIZE);
            }

            @Override
            public void onSuccess(List<RespListTaxType> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public List<RespListTaxType> apiConvertResponse(List<RespListTaxType> d) {
                return d;
            }

            @Override
            public void setCache(List<RespListTaxType> d) {

            }

            @Override
            public void getCache(CommonCallback<List<RespListTaxType>> cacheCb) {

            }
        });
    }

    @Deprecated  // getSingleSSPX
    public static void getSingleSSP(final Activity ctx, final RequestParamConfig rpc, final Long sspId,
                                    final SuccessFailCallback
            <ResponseSsp, ResponseDTO> cb){
//        rpc.progressTextRes = R.string.progressdialog_common;
        HttpReq.run(ctx, SharePref.SPRK_SINGLE_SSP, rpc, new HttpCacheCallback<ResponseSsp, ResponseSsp>(){
            public Call<ResponseSsp> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).getSingleSSP(bearerAuth, sspId);
            }

            @Override
            public void onSuccess(ResponseSsp data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public ResponseSsp apiConvertResponse(ResponseSsp d) {
                return d;
            }

            @Override
            public void setCache(ResponseSsp d) {

            }

            @Override
            public void getCache(CommonCallback<ResponseSsp> cacheCb) {

            }
        });
    }

    public static void httpFirst(final Activity ctx, final RequestParamConfig rpc, final SuccessFailCallback<AppStatusData, ResponseDTO> cb){
        final SharedPreferences sp = SharePref.getInstance(ctx);
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;
        rpc.keepdataDuration = AppConstant.MS_KEEPDATA_HOUR;
        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_FIRST;
        HttpReq.run(ctx, AppConstant.SP_CACHEKEY_FIRST, rpc, new HttpCacheCallback<ResponseBody, AppStatusData>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(AppStatusData data) {
                data.setNulled(ctx);
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
//                AppStatusData asd = new AppStatusData();
//                asd.isOutdateVersion = false;
//                cb.onSuccess(asd);
            }

            @Override
            public AppStatusData apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), AppStatusData.class);
                }catch (Exception e){
                    Utility.log("2020 0820 1607 "+e.getMessage());
                    return new AppStatusData();
                }
            }

            @Override
            public void setCache(AppStatusData d) {
                SharedPreferences.Editor ed = sp.edit();
                ed.putString(AppConstant.SP_CACHESTATUSDATA_CLIENTVERSION, d.clientVersion);
                ed.putBoolean(AppConstant.SP_CACHESTATUSDATA_ISOUTDATEVERSION, d.isOutdateVersion );
                ed.putString(AppConstant.SP_CACHESTATUSDATA_LASTCLIENTVERSION, d.lastClientVersion);
                ed.putString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUWIDGET, d.api.mpnPajakkuWidget);
                ed.putString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUCLIENTID, d.api.mpnPajakkuClientId);
                ed.putString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUHOST, d.api.mpnPajakkuHost);
                ed.putString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUBASE, d.api.mpnPajakkuBase);
                ed.putString(AppConstant.SP_CACHESTATUSDATA_MPNWIDGET_USER, d.mpnWidgetUser);
                ed.putString(AppConstant.SP_CACHESTATUSDATA_MPNWIDGET_PASS, d.mpnWidgetPass);
                ed.putString(AppConstant.SP_CACHESTATUSDATA_API_EREGHOST, d.api.eregHost);
                ed.commit();
            }

            @Override
            public void getCache(CommonCallback<AppStatusData> cacheCb) {
                String currVer = Utility.getPInfo(ctx).versionName;
                AppStatusData d = new AppStatusData();
                d.clientVersion = sp.getString(AppConstant.SP_CACHESTATUSDATA_CLIENTVERSION, currVer);
                d.isOutdateVersion = sp.getBoolean(AppConstant.SP_CACHESTATUSDATA_ISOUTDATEVERSION, false);
                d.lastClientVersion = sp.getString(AppConstant.SP_CACHESTATUSDATA_LASTCLIENTVERSION, currVer);
                d.api.mpnPajakkuWidget = sp.getString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUWIDGET, "");
                d.api.mpnPajakkuClientId = sp.getString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUCLIENTID, "");
                d.api.mpnPajakkuHost = sp.getString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUHOST, "");
                d.api.mpnPajakkuBase = sp.getString(AppConstant.SP_CACHESTATUSDATA_API_MPNPAJAKKUBASE, "");
                d.mpnWidgetUser = sp.getString(AppConstant.SP_CACHESTATUSDATA_MPNWIDGET_USER, "");
                d.mpnWidgetPass = sp.getString(AppConstant.SP_CACHESTATUSDATA_MPNWIDGET_PASS, "");
                d.api.eregHost = sp.getString(AppConstant.SP_CACHESTATUSDATA_API_EREGHOST, "");
                if(cacheCb != null) cacheCb.onSuccess(d);
            }
        });
    }

    public static void getSspUnpaid(final Activity ctx, final RequestParamConfig rpc,
                                    final SuccessFailCallback<List<Sspunpaid>,ResponseDTO> cb){
        rpc.reqPaging.size = AppConstant.LIST_SIZE;
        rpc.reqPaging.order = (rpc.reqPaging.order!=null?rpc.reqPaging.order:"DESC");
        rpc.reqPaging.isPaid = false;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_SSP_LIST;
        body.json = Utility.gson().toJson(rpc.reqPaging);
        HttpReq.run(ctx, AppConstant.SP_CACHEKEY_SSPUNPAID, rpc, new HttpCacheCallback<ResponseBody, List<Sspunpaid>>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(List<Sspunpaid> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public List<Sspunpaid> apiConvertResponse(ResponseBody d) {
                try {
                    List<ResponseSsp> ssps = Utility.gson()
                            .fromJson(d.string(), new TypeToken<List<ResponseSsp>>(){}.getType() );
                    return Sspunpaid.getInstanceList(ssps);
                }catch (Exception e){
                    Utility.log("2020 0820 1655 "+e.getMessage());
                    return new ArrayList<>();
                }
            }

            @Override
            public void setCache(List<Sspunpaid> d) {

            }

            @Override
            public void getCache(CommonCallback<List<Sspunpaid>> cacheCb) {

            }
        });
    }

    public static void getSspDone(final Activity ctx, final RequestParamConfig rpc,
                                    final SuccessFailCallback<List<Sspdone>,ResponseDTO> cb){
        rpc.reqPaging.size = AppConstant.LIST_SIZE;
        rpc.reqPaging.order = (rpc.reqPaging.order!=null?rpc.reqPaging.order:"DESC");
        rpc.reqPaging.isPaid = true;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_SSP_LIST;
        body.json = Utility.gson().toJson(rpc.reqPaging);
        HttpReq.run(ctx, AppConstant.SP_CACHEKEY_SSPDONE, rpc, new HttpCacheCallback<ResponseBody, List<Sspdone>>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(List<Sspdone> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public List<Sspdone> apiConvertResponse(ResponseBody d) {
                try {
                    List<ResponseSsp> ssps = Utility.gson()
                            .fromJson(d.string(), new TypeToken<List<ResponseSsp>>(){}.getType() );
                    return Sspdone.getInstanceListSspdone(ssps);
                }catch (Exception e){
                    Utility.log("2020 0820 1707 "+e.getMessage());
                    return new ArrayList<>();
                }
            }

            @Override
            public void setCache(List<Sspdone> d) {

            }

            @Override
            public void getCache(CommonCallback<List<Sspdone>> cacheCb) {

            }
        });
    }

    public static void saveFCMTokenToServer(final Activity ctx, final String bearerAuthSpace,
                                            final String fcmToken){
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.withAuth = bearerAuthSpace == null;
        rpc.forceRequest = true;
        rpc.isRelogin = true;

        FirebaseToken ft = new FirebaseToken();
        ft.deviceId = fcmToken;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_FCM;
        body.json = Utility.gson().toJson(ft);
        HttpReq.run(ctx, SharePref.SPRK_FCM, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common( (bearerAuthSpace!=null?bearerAuthSpace:bearerAuth), body);
            }

            @Override
            public void onSuccess(ResponseBody data) {
                SharePref.setStr(ctx, AppConstant.SP_FCM_TOKEN, fcmToken);
            }

            @Override
            public void onFail(ResponseDTO error){
                Utility.log("Fail update firebase token: " + (error!=null?error.error_description:"") );
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

    public static void wpAdd(final Activity ctx, RequestParamConfig rpc, Wajibpajak wp,
                             final CommonCallback<Wajibpajak> cb){
        rpc.progressTextRes = R.string.progressdialog_savewp;
        rpc.forceRequest = true;
        rpc.isRelogin = true;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_WP_ADD;
        body.json = Utility.gson().toJson(wp);
        HttpReq.run(ctx, SharePref.SPRK_WP_ADD, rpc, new HttpCacheCallback<ResponseBody, Wajibpajak>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common( bearerAuth, body);
            }

            @Override
            public void onSuccess(Wajibpajak data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
            }

            @Override
            public Wajibpajak apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), Wajibpajak.class);
                }catch (Exception e){
                    Utility.log("2020 0821 0752 "+e.getMessage());
                    return new Wajibpajak();
                }
            }

            @Override
            public void setCache(Wajibpajak d) {

            }

            @Override
            public void getCache(CommonCallback<Wajibpajak> cacheCb) {

            }
        });
    }

    public static void requestWPList(final Activity ctx, final RequestParamConfig rpc,
                                  final SuccessFailCallback<List<Wajibpajak>,ResponseDTO> cb){
        rpc.reqPaging.size = AppConstant.LIST_SIZE;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_WP_LIST;
        body.json = Utility.gson().toJson(rpc.reqPaging);
        HttpReq.run(ctx, AppConstant.SP_CACHEKEY_WPLIST, rpc, new HttpCacheCallback<ResponseBody, List<Wajibpajak>>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(List<Wajibpajak> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public List<Wajibpajak> apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson()
                            .fromJson(d.string(), new TypeToken<List<Wajibpajak>>(){}.getType() );
                }catch (Exception e){
                    Utility.log("2020 0821 0738 "+e.getMessage());
                    return new ArrayList<>();
                }
            }

            @Override
            public void setCache(List<Wajibpajak> d) {

            }

            @Override
            public void getCache(CommonCallback<List<Wajibpajak>> cacheCb) {

            }
        });
    }

    public static void wpDel(final Activity ctx, final RequestParamConfig rpc,
                                     final Long wpId, final CommonCallback<ResponseBody> cb){

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_WP_DEL;
        body.json = wpId.toString();
        HttpReq.run(ctx, SharePref.SPRK_WP_DEL, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common( bearerAuth, body);
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

    public static void wpEdit(final Activity ctx, RequestParamConfig rpc, Wajibpajak wp,
                             final CommonCallback<Wajibpajak> cb){
        rpc.progressTextRes = R.string.progressdialog_savewp;
        rpc.forceRequest = true;
        rpc.isRelogin = true;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_WP_EDIT;
        body.json = Utility.gson().toJson(wp);
        HttpReq.run(ctx, SharePref.SPRK_WP_EDIT, rpc, new HttpCacheCallback<ResponseBody, Wajibpajak>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common( bearerAuth, body);
            }

            @Override
            public void onSuccess(Wajibpajak data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
            }

            @Override
            public Wajibpajak apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), Wajibpajak.class);
                }catch (Exception e){
                    Utility.log("2020 0821 1404 "+e.getMessage());
                    return new Wajibpajak();
                }
            }

            @Override
            public void setCache(Wajibpajak d) {

            }

            @Override
            public void getCache(CommonCallback<Wajibpajak> cacheCb) {

            }
        });
    }

    public static void getSingleSSPX(final Activity ctx, final RequestParamConfig rpc,
                                     final Long sspId, final SuccessFailCallback<ResponseSsp, ResponseDTO> cb){

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_SSP_ONE;
        body.json = sspId.toString();
        HttpReq.run(ctx, SharePref.SPRK_SINGLE_SSP, rpc, new HttpCacheCallback<ResponseBody, ResponseSsp>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common( bearerAuth, body);
            }

            @Override
            public void onSuccess(ResponseSsp data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public ResponseSsp apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), ResponseSsp.class);
                }catch (Exception e){
                    Utility.log("2020 0820 2309 "+e.getMessage());
                    return new ResponseSsp();
                }
            }

            @Override
            public void setCache(ResponseSsp d) {

            }

            @Override
            public void getCache(CommonCallback<ResponseSsp> cacheCb) {

            }
        });
    }

    public static void sspDel(final Activity ctx, final RequestParamConfig rpc,
                             final Long id, final CommonCallback<ResponseBody> cb){
        rpc.progressTextRes = R.string.progressdialog_delssp;
        rpc.forceRequest = true;
        rpc.isRelogin = true;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_SSP_DEL;
        body.json = id.toString();
        HttpReq.run(ctx, SharePref.SPRK_SSP_DEL, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common( bearerAuth, body);
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

//    public static void sobDaftarGetDataByEmail(final Activity ctx, final RequestParamConfig rpc, final String email,
//                                               final CommonCallback<SobDaftarListDataDTO> cb){
//        HttpReq.run(ctx, SharePref.SPRK_GET_S_DAFTAR_ONE_BY_EMAIL, rpc, new HttpCacheCallback<SobDaftarListDataDTO, SobDaftarListDataDTO>(){
//            public Call<SobDaftarListDataDTO> requestMethod(String bearerAuth){
//                return HttpReq.getApiCallSobatDaftar(ctx).sobDaftarGetDataByEmail(bearerAuth, email);
//            }
//
//            @Override
//            public void onSuccess(SobDaftarListDataDTO data) {
//                cb.onSuccess(data);
//            }
//
//            @Override
//            public void onFail(ResponseDTO error){
//
//            }
//
//            @Override
//            public SobDaftarListDataDTO apiConvertResponse(SobDaftarListDataDTO d) {
//                return d;
//            }
//
//            @Override
//            public void setCache(SobDaftarListDataDTO d) {
//
//            }
//
//            @Override
//            public void getCache(CommonCallback<SobDaftarListDataDTO> cacheCb) {
//
//            }
//        });
//    }
//
//    public static void getAllKodeWilayah(final Activity ctx, final RequestParamConfig rpc, final FilterParam fp,
//                                         final String findKey, final CommonCallback<PageDTO<KodeWilayahDTO>> cb){
//        if(findKey == null) return;
//        HttpReq.run(ctx, SharePref.SPRK_GET_SOB_DAFTAR_LIST_KODE_WILAYAH, rpc, new HttpCacheCallback<PageDTO<KodeWilayahDTO>, PageDTO<KodeWilayahDTO>>(){
//            public Call<PageDTO<KodeWilayahDTO>> requestMethod(String bearerAuth){
//                return HttpReq.getApiCallSobatDaftar(ctx).getAllKodeWilayah(bearerAuth, "id", -1, AppConstant.LIST_SIZE,
//                        "namaProp", findKey, fp.page);
//            }
//
//            @Override
//            public void onSuccess(PageDTO<KodeWilayahDTO> data) {
//                cb.onSuccess(data);
//            }
//
//            @Override
//            public void onFail(ResponseDTO error){
//
//            }
//
//            @Override
//            public PageDTO<KodeWilayahDTO> apiConvertResponse(PageDTO<KodeWilayahDTO> d) {
//                return d;
//            }
//
//            @Override
//            public void setCache(PageDTO<KodeWilayahDTO> d) {
//
//            }
//
//            @Override
//            public void getCache(CommonCallback<PageDTO<KodeWilayahDTO>> cacheCb) {
//
//            }
//        });
//    }

    private ApiMain(){}

}
