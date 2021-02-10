package com.pajakku.tupaimobile.api;

import android.app.Activity;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.model.dto.CommonDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.MpnPajakkuUrlDTO;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by dul on 31/12/18.
 */

@Deprecated
public final class ApiReqTest {

    @Deprecated  // @test
    public static void common(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<ResponseBody> cb){
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;
        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_TEST;
        HttpReq.run(ctx, SharePref.SPRK_TEST, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
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
//                try {
//                    return Utility.gson().fromJson(d.string(), ResponseBody.class);
//                }catch (Exception e){
//                    Utility.log("2020 0819 1329 "+e.getMessage());
//                    return new ResponseBody();
//                }
            }

            @Override
            public void setCache(ResponseBody d) {

            }

            @Override
            public void getCache(CommonCallback<ResponseBody> cacheCb) {

            }
        });
    }

    public static void testAuth(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<ResponseBody> cb){
        rpc.isRelogin = true;
//        rpc.progressTextRes = R.string.progressdialog_common;
        HttpReq.run(ctx, SharePref.SPRK_TEST, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getUrlApiBdgApiCallTest(ctx).testAuth(bearerAuth);
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

    private ApiReqTest(){}

}
