package com.pajakku.tupaimobile.api;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.dao.AppDatabase;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.ifc.QueryCallback;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.CommonDTO;
import com.pajakku.tupaimobile.model.dto.ReqPaging;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.billing.BillingRetryDTO;
import com.pajakku.tupaimobile.model.dto.billing.ReqCekBillGenerate;
import com.pajakku.tupaimobile.model.dto.billing.RespKjs;
import com.pajakku.tupaimobile.model.dto.request.RequestSsp;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.QueryAsyncTask;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by dul on 31/12/18.
 */

public final class ApiReqBilling {

    public static void findKjp(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<List<RespListTaxType>> cb){
        final AppDatabase appDb = AppDatabase.get(ctx);
        rpc.reqPaging.size = AppConstant.LIST_SIZE;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_LIST_KJP;
        body.json = Utility.gson().toJson(rpc.reqPaging);
        HttpReq.run(ctx, AppConstant.SP_CACHEKEY_TAXTYPE, rpc, new HttpCacheCallback<ResponseBody, List<RespListTaxType>>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(List<RespListTaxType> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public List<RespListTaxType> apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), new TypeToken<List<RespListTaxType>>(){}.getType() );
                }catch (Exception e){
                    Utility.log("2020 0820 1626 "+e.getMessage());
                    return new ArrayList<>();
                }
            }

            @Override
            public void setCache(final List<RespListTaxType> d) {

                new QueryAsyncTask<>(new QueryCallback<Void>() {
                    @Override
                    public Void onProcess() {
                        appDb.taxTypeDao().deleteAll();
                        appDb.taxTypeDao().insertAll( RespListTaxType.toTaxType(d) );
                        return null;
                    }

                    @Override
                    public void onSuccess(Void v) {

                    }
                }).execute();
            }

            @Override
            public void getCache(final CommonCallback<List<RespListTaxType>> cacheCb) {
                new QueryAsyncTask<>(new QueryCallback<List<Taxtype>>() {
                    @Override
                    public List<Taxtype> onProcess() {
                        return appDb.taxTypeDao().getAll();
                    }

                    @Override
                    public void onSuccess(List<Taxtype> data) {
                        cacheCb.onSuccess( Taxtype.toRespListTaxType(data) );
                    }
                }).execute();
            }
        });
    }

    public static void findKjsByKjpId(final Activity ctx, final RequestParamConfig rpc, String kjpId, final SuccessFailCallback<List<RespKjs>, ResponseDTO> cb){
        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_LIST_KJS;
        body.json = kjpId;
        HttpReq.run(ctx, SharePref.SPRK_GET_LIST_KJS, rpc, new HttpCacheCallback<ResponseBody, List<RespKjs>>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(List<RespKjs> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public List<RespKjs> apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), new TypeToken<List<RespKjs>>(){}.getType());
                }catch (Exception e){
                    Utility.log("2020 0820 1621 "+e.getMessage());
                    return new ArrayList<>();
                }
            }

            @Override
            public void setCache(List<RespKjs> d) {

            }

            @Override
            public void getCache(CommonCallback<List<RespKjs>> cacheCb) {

            }
        });
    }

    @Deprecated
    public static void getKjs(final Activity ctx, final RequestParamConfig rpc, final String kjpId, final CommonCallback<List<RespKjs>> cb){
//        rpc.progressTextRes = R.string.progressdialog_common;
        HttpReq.run(ctx, SharePref.SPRK_GET_LIST_KJS, rpc, new HttpCacheCallback<List<RespKjs>, List<RespKjs>>(){
            public Call<List<RespKjs>> requestMethod(String bearerAuth){
                return HttpReq.getUrlPrivateBilling(ctx).getKjs(bearerAuth, kjpId);
            }

            @Override
            public void onSuccess(List<RespKjs> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public List<RespKjs> apiConvertResponse(List<RespKjs> d) {
                return d;
            }

            @Override
            public void setCache(List<RespKjs> d) {

            }

            @Override
            public void getCache(CommonCallback<List<RespKjs>> cacheCb) {

            }
        });
    }

    public static void cekBillGenerate(final Activity ctx, final RequestParamConfig rpc,
                                       Long sspId, String refCode, final CommonCallback<BillingRetryDTO> cb){
        rpc.progressTextRes = R.string.progressdialog_checksspstatus;
        rpc.forceRequest = true;
        rpc.isRelogin = true;

        ReqCekBillGenerate reqDto = new ReqCekBillGenerate();
        reqDto.sspId = sspId;
        reqDto.refCode = refCode;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_CEK_BILL_GENERATE;
        body.json = Utility.gson().toJson(reqDto);
        HttpReq.run(ctx, SharePref.SPRK_CEK_BILL_GENERATE, rpc, new HttpCacheCallback<ResponseBody, BillingRetryDTO>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(BillingRetryDTO data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public BillingRetryDTO apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), BillingRetryDTO.class);
                }catch (Exception e){
                    Utility.logErr("2020 0821 1914 "+e.getMessage());
                    return new BillingRetryDTO();
                }
            }

            @Override
            public void setCache(BillingRetryDTO d) {

            }

            @Override
            public void getCache(CommonCallback<BillingRetryDTO> cacheCb) {

            }
        });
    }

    public static void sspAdd(final Activity ctx, RequestParamConfig rpc, RequestSsp reqDto,
                             final CommonCallback<RequestSsp> cb){
        rpc.progressTextRes = R.string.progressdialog_createssp;
        rpc.forceRequest = true;
        rpc.isRelogin = true;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_SSP_ADD;
        body.json = Utility.gson().toJson(reqDto);
        HttpReq.run(ctx, SharePref.SPRK_SSP_ADD, rpc, new HttpCacheCallback<ResponseBody, RequestSsp>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common( bearerAuth, body);
            }

            @Override
            public void onSuccess(RequestSsp data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
            }

            @Override
            public RequestSsp apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), RequestSsp.class);
                }catch (Exception e){
                    Utility.logErr("2020 0821 2037 "+e.getMessage());
                    return new RequestSsp();
                }
            }

            @Override
            public void setCache(RequestSsp d) {

            }

            @Override
            public void getCache(CommonCallback<RequestSsp> cacheCb) {

            }
        });
    }

    private ApiReqBilling(){}

}
