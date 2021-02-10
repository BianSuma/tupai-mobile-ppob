package com.pajakku.tupaimobile.activity;

import com.google.gson.reflect.TypeToken;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.Sspdone;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.util.ApiHttpCallbackInterface;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterface;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.SuccessFailCallback;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by dul on 17/01/19.
 */

public class RepositoryActivity extends BaseActivity {

    @Deprecated
    public void getStatusData(RequestParamConfig rpc, final CommonCallback<AppStatusData> callback){
        requestHttpSimple(true, 0, rpc.forceRequest, AppConstant.SP_CACHEKEY_FIRST, false, new HttpCallbackInterfaceSimple<AppStatusData>() {
            @Override
            public Call<AppStatusData> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.getStatusData(bearerAuth);
            }

            @Override
            public void onSuccess(AppStatusData response) {
                callback.onSuccess( response );
            }

            @Override
            public boolean onFail(ResponseError error) {
                AppStatusData asd = new AppStatusData();
                asd.isOutdateVersion = false;
                callback.onSuccess(asd);
                return false;
            }
        });
    }

    public void repoSingleSSPUnpaid(final long id, final CommonCallback<Sspunpaid> callback){
        executeQueryProcessSuccess(new ListenerQuerySuccess<Sspunpaid>() {
            @Override
            public Sspunpaid onProcess() {
                return appDatabase.sspunpaidDao().getById(id);
            }

            @Override
            public void onSuccess(Sspunpaid result) {
                callback.onSuccess(result);
            }
        });
    }

    public void repoSingleSSPDone(final long id, final CommonCallback<Sspdone> callback){
        executeQueryProcessSuccess(new ListenerQuerySuccess<Sspdone>() {
            @Override
            public Sspdone onProcess() {
                return appDatabase.sspdoneDao().getById(id);
            }

            @Override
            public void onSuccess(Sspdone result) {
                callback.onSuccess(result);
            }
        });
    }

//    public void requestSingleSSP(final long id, final CommonCallback<ResponseSsp> callback){
//        requestHttpSimple(true, R.string.progressdialog_loadssp, true, null, true, new HttpCallbackInterfaceSimple<ResponseSsp>() {
//            @Override
//            public Call<ResponseSsp> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
//                return httpService.getSingleSSP(bearerAuth, id);
//            }
//
//            @Override
//            public void onSuccess(ResponseSsp response) {
//                callback.onSuccess(response);
//            }
//
//            @Override
//            public boolean onFail(ResponseError error) {
//                return true;
//            }
//        });
//    }

    public void requestGetBalance(boolean showActivate, boolean isRelogin, final SuccessFailCallback<ResponseFinnet, ResponseError> callback){
        requestFinnet(0, AppConstant.SP_CACHEKEY_EMONBALANCE, showActivate, isRelogin, new HttpCallbackInterfaceSimple<ResponseFinnet>() {
            @Override
            public Call<ResponseFinnet> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.mcBalance(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseFinnet response) {
                callback.onSuccess(response);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return callback.onFail(error);
            }
        });
    }

//    @Deprecated  // requestWPList
//    public void requestWajibpajak(final long page, final FilterParam filterParam, boolean isRelogin, RequestParamConfig rpc, final SuccessFailCallback<List<Wajibpajak>, ResponseError> callback){
//        requestHttpSimple(true, 0, true, (rpc.isCache?AppConstant.SP_CACHEKEY_WPLIST:null), isRelogin, new HttpCallbackInterfaceSimple<List<Wajibpajak>>() {
//            @Override
//            public Call<List<Wajibpajak>> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
//                return httpService.getWPList(bearerAuth, page, AppConstant.LIST_SIZE, filterParam.field, filterParam.query, filterParam.order, filterParam.column);
//            }
//
//            @Override
//            public void onSuccess(List<Wajibpajak> response) {
//                callback.onSuccess(response);
//            }
//
//            @Override
//            public boolean onFail(ResponseError error) {
//                return callback.onFail(error);
//            }
//        });
//    }

    @Deprecated
    public void requestWPList(final long page, final FilterParam filterParam, RequestParamConfig rpc, final SuccessFailCallback<List<Wajibpajak>, ResponseError> callback){
        requestHttpSimple(true, 0, rpc.forceRequest, (rpc.isCache?AppConstant.SP_CACHEKEY_WPLIST:null),
                rpc.isRelogin, new HttpCallbackInterfaceSimple<List<Wajibpajak>>() {
            @Override
            public Call<List<Wajibpajak>> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.getWPList(bearerAuth, page, AppConstant.LIST_SIZE,
                        filterParam.field, filterParam.query, filterParam.order, filterParam.column);
            }

            @Override
            public void onSuccess(List<Wajibpajak> response) {
                callback.onSuccess(response);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return callback.onFail(error);
            }
        });
    }

    public void requestTaxSlipTypeList(int progressText, final long taxTypeId, final long page, final SuccessFailCallback<List<Kjs>,ResponseError> callback){

        requestHttpStream(progressText, true, AppConstant.SP_CACHEKEY_STREAMKJS, new TypeToken<List<Kjs>>(){}.getType(), true, new HttpCallbackInterface<Kjs,Kjs>() {
            @Override
            public Call<Kjs> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return null;
            }
            @Override
            public Call<ResponseBody> requestMethodStream(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.getTaxSlipType(bearerAuth, taxTypeId, page, 700);
            }

            @Override
            public void onSuccess(Kjs response) {
                // nothing
            }
            @Override
            public void onSuccessStream(List<Kjs> response) {
                callback.onSuccess(response);
            }

            @Override
            public boolean onFail(ResponseError error) {
                return callback.onFail(error);
            }
        });
    }

    @Deprecated
    public void requestUnpaidSSP(final long page, final String field, final String query, final String order, final String column, RequestParamConfig reqParam, final SuccessFailCallback<List<Sspunpaid>, ResponseError> callback){

        reqParam.cacheKey = AppConstant.SP_CACHEKEY_SSPUNPAID;
        reqParam.progressTextRes = 0;

        final RequestParamConfig rpc = reqParam;

        apiRequestHttp(rpc, new ApiHttpCallbackInterface<List<ResponseSsp>, List<Sspunpaid>>() {
            @Override
            public Call<List<ResponseSsp>> requestMethod(Http httpService, String bearerAuth) {
//                return httpService.getSspList(bearerAuth, (order!=null?order:"DESC"), column, page, AppConstant.LIST_SIZE, field, query, false);
                return HttpReq.getUrlApiBdgApiCallMain(RepositoryActivity.this).getSspList(bearerAuth, (order!=null?order:"DESC"), column, page,
                        AppConstant.LIST_SIZE, field, query, false);
            }

            @Override
            public void onSuccess(List<Sspunpaid> response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFail(ResponseError error) {
                callback.onFail(error);
            }
        });

    }

    @Deprecated
    public void requestSSPDone(final long page, final String field, final String query, final String order, final String column, RequestParamConfig rpc, final SuccessFailCallback<List<Sspdone>, ResponseError> callback){

        rpc.cacheKey = AppConstant.SP_CACHEKEY_SSPDONE;
        rpc.progressTextRes = 0;

        apiRequestHttp(rpc, new ApiHttpCallbackInterface<List<ResponseSsp>, List<Sspdone>>() {
            @Override
            public Call<List<ResponseSsp>> requestMethod(Http httpService, String bearerAuth) {
//                return httpService.getSspList(bearerAuth, (order!=null?order:"DESC"), column, page, AppConstant.LIST_SIZE, field, query, true);
                return HttpReq.getUrlApiBdgApiCallMain(RepositoryActivity.this).getSspList(bearerAuth, (order!=null?order:"DESC"),
                        column, page, AppConstant.LIST_SIZE, field, query, true);
            }

            @Override
            public void onSuccess(List<Sspdone> response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFail(ResponseError error) {
                callback.onFail(error);
            }
        });

    }

}
