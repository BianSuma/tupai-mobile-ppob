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
import com.pajakku.tupaimobile.model.dto.PageDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.billing.BillingRetryDTO;
import com.pajakku.tupaimobile.model.dto.billing.ReqCekBillGenerate;
import com.pajakku.tupaimobile.model.dto.billing.RespKjs;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.UploadedKtp;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;
import com.pajakku.tupaimobile.model.dto.request.RequestSsp;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.HttpReq;
import com.pajakku.tupaimobile.util.QueryAsyncTask;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by dul on 31/12/18.
 */

public final class ApiReqEreg {

    public static void kodeWilayah(final Activity ctx, final RequestParamConfig rpc, String findKey, final CommonCallback<PageDTO<WilayahDTO>> cb){
//        final AppDatabase appDb = AppDatabase.get(ctx);
        rpc.reqPaging.query = findKey;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_EREG_KODE_WILAYAH;
        body.json = Utility.gson().toJson(rpc.reqPaging);
        HttpReq.run(ctx, SharePref.SPRK_EREG_KODEWILAYAH, rpc, new HttpCacheCallback<ResponseBody, PageDTO<WilayahDTO>>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(PageDTO<WilayahDTO> data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public PageDTO<WilayahDTO> apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), new TypeToken<PageDTO<WilayahDTO>>(){}.getType() );
                }catch (Exception e){
                    Utility.log("2020 0904 1914 "+e.getMessage());
                    return new PageDTO<>();
                }
            }

            @Override
            public void setCache(final PageDTO<WilayahDTO> d) {

            }

            @Override
            public void getCache(final CommonCallback<PageDTO<WilayahDTO>> cacheCb) {

            }
        });
    }

    public static void validasi1(final Activity ctx, final RequestParamConfig rpc, ReqValidasi1 reqValidasi1, final CommonCallback<ResponseBody> cb){
//        final AppDatabase appDb = AppDatabase.get(ctx);
//        rpc.reqPaging.size = AppConstant.LIST_SIZE;
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_EREG_VALIDASI_1;
        body.json = Utility.gson().toJson(reqValidasi1);
        HttpReq.run(ctx, SharePref.SPRK_EREG_VALIDASI_1, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
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
//                    return Utility.gson().fromJson(d.string(), ResponseDTO.class );
//                }catch (Exception e){
//                    Utility.log("2020 0902 1812 "+e.getMessage());
//                    return new ResponseDTO();
//                }
            }

            @Override
            public void setCache(final ResponseBody d) {

            }

            @Override
            public void getCache(final CommonCallback<ResponseBody> cacheCb) {

            }
        });
    }

    public static void updateKelengkapan(final Activity ctx, final RequestParamConfig rpc, EregDataByEmail dto, final CommonCallback<ResponseBody> cb){
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_EREG_UPDATE_KELENGKAPAN;
        body.json = Utility.gson().toJson(dto);
        HttpReq.run(ctx, SharePref.SPRK_EREG_UPDATE_KELENGKAPAN, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
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
//                    return Utility.gson().fromJson(d.string(), ResponseDTO.class );
//                }catch (Exception e){
//                    Utility.log("2020 0902 1812 "+e.getMessage());
//                    return new ResponseDTO();
//                }
            }

            @Override
            public void setCache(final ResponseBody d) {

            }

            @Override
            public void getCache(final CommonCallback<ResponseBody> cacheCb) {

            }
        });
    }

    public static void submitWp(final Activity ctx, final RequestParamConfig rpc, String email, final CommonCallback<ResponseDTO> cb){
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_EREG_SUBMITWP;
        body.json = email;
        HttpReq.run(ctx, SharePref.SPRK_EREG_SUBMITWP, rpc, new HttpCacheCallback<ResponseBody, ResponseDTO>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(ResponseDTO data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public ResponseDTO apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), ResponseDTO.class );
                }catch (Exception e){
                    Utility.log("2020 0902 1812 "+e.getMessage());
                    return new ResponseDTO();
                }
            }

            @Override
            public void setCache(final ResponseDTO d) {

            }

            @Override
            public void getCache(final CommonCallback<ResponseDTO> cacheCb) {

            }
        });
    }

    public static void getDataByEmail(final Activity ctx, final RequestParamConfig rpc, String email, final SuccessFailCallback<EregDataByEmail, ResponseDTO> cb){
        rpc.isRelogin = true;
        rpc.progressTextRes = R.string.progressdialog_common;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_EREG_DATA_BY_EMAIL;
        body.json = email;
        HttpReq.run(ctx, SharePref.SPRK_EREG_DATA_BY_EMAIL, rpc, new HttpCacheCallback<ResponseBody, EregDataByEmail>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).common(bearerAuth, body);
            }

            @Override
            public void onSuccess(EregDataByEmail data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){
                cb.onFail(error);
            }

            @Override
            public EregDataByEmail apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), EregDataByEmail.class );
                }catch (Exception e){
                    Utility.log("2020 0904 1116 "+e.getMessage());
                    return new EregDataByEmail();
                }
            }

            @Override
            public void setCache(final EregDataByEmail d) {

            }

            @Override
            public void getCache(final CommonCallback<EregDataByEmail> cacheCb) {

            }
        });
    }

    public static void getListData(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<ResponseBody> cb){
//        final AppDatabase appDb = AppDatabase.get(ctx);
//        rpc.reqPaging.query = findKey;

        final CommonDTO body = new CommonDTO();
        body.code = CommonDTO.CODE_EREG_LIST_DATA;
        body.json = Utility.gson().toJson(rpc.reqPaging);
        HttpReq.run(ctx, SharePref.SPRK_EREG_LISTDATA, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
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
//                    return Utility.gson().fromJson(d.string(), new TypeToken<ResponseBody>(){}.getType() );
//                }catch (Exception e){
//                    Utility.log("2020 0904 1914 "+e.getMessage());
//                    return new PageDTO<>();
//                }
            }

            @Override
            public void setCache(final ResponseBody d) {

            }

            @Override
            public void getCache(final CommonCallback<ResponseBody> cacheCb) {

            }
        });
    }

    public static void getLog(final Activity ctx, final RequestParamConfig rpc, final CommonCallback<ResponseBody> cb){
//        final AppDatabase appDb = AppDatabase.get(ctx);
//        rpc.reqPaging.size = AppConstant.LIST_SIZE;'
        rpc.progressTextRes = R.string.progressdialog_common;
        rpc.isRelogin = true;

        final MultipartBody.Part partCode = MultipartBody.Part.createFormData(HttpReq.PARAM_CODE, Integer.toString(CommonDTO.CODE_EREG_LOG));
//        final MultipartBody.Part partFile = Utility.pathToPartFile("file", filePath);
//        final MultipartBody.Part partJson = MultipartBody.Part.createFormData("json", email);

        HttpReq.run(ctx, SharePref.SPRK_EREG_LOG, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).commonUpload(bearerAuth, partCode, null, null);
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
//                    return Utility.gson().fromJson(d.string(), UploadedKtp.class );
//                }catch (Exception e){
//                    Utility.log("2020 1008 1745 "+e.getMessage());
//                    return new UploadedKtp();
//                }
            }

            @Override
            public void setCache(final ResponseBody d) {

            }

            @Override
            public void getCache(final CommonCallback<ResponseBody> cacheCb) {

            }
        });
    }

    private ApiReqEreg(){}

}
