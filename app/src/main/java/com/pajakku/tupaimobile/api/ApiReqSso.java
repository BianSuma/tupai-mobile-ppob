package com.pajakku.tupaimobile.api;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.dto.CommonDTO;
import com.pajakku.tupaimobile.model.dto.PageDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.model.dto.request.RequestResetPass;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by dul on 31/12/18.
 */

public final class ApiReqSso {

    public static void resendActivation(final Activity ctx, RequestParamConfig rpc, String email, final CommonCallback<ResponseBody> cb){
        rpc.progressTextRes = R.string.progressdialog_accesstoken;
        rpc.isRelogin = true;
        rpc.withAuth = false;

        final RequestResetPass body = new RequestResetPass();
        body.email = email;

        HttpReq.run(ctx, SharePref.SPRK_RESEND_AKTIVATION, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.urlSsoBdgApiCallMain(ctx).resendActivation(body);
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
            public void setCache(final ResponseBody d) {

            }

            @Override
            public void getCache(final CommonCallback<ResponseBody> cacheCb) {

            }
        });
    }

    private ApiReqSso(){}

}
