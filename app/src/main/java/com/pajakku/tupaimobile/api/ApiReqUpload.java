package com.pajakku.tupaimobile.api;

import android.app.Activity;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ifc.HttpCacheCallback;
import com.pajakku.tupaimobile.model.dto.CommonDTO;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.ereg.UploadedKtp;
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

public final class ApiReqUpload {

    public static void eregUploadKtp(final Activity ctx, final RequestParamConfig rpc, String filePath, final CommonCallback<UploadedKtp> cb){
//        final AppDatabase appDb = AppDatabase.get(ctx);
//        rpc.reqPaging.size = AppConstant.LIST_SIZE;'
        rpc.progressTextRes = R.string.progressdialog_common;
        rpc.isRelogin = true;

        final MultipartBody.Part partCode = MultipartBody.Part.createFormData("code", Integer.toString(CommonDTO.CODE_EREG_UPLOAD_KTP));
        final MultipartBody.Part partFile = Utility.pathToPartFile("file", filePath);
//        final MultipartBody.Part partJson = MultipartBody.Part.createFormData("json", email);

        HttpReq.run(ctx, SharePref.SPRK_EREG_UPLOAD_KTP, rpc, new HttpCacheCallback<ResponseBody, UploadedKtp>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).commonUpload(bearerAuth, partCode, partFile, null);
            }

            @Override
            public void onSuccess(UploadedKtp data) {
                cb.onSuccess(data);
            }

            @Override
            public void onFail(ResponseDTO error){

            }

            @Override
            public UploadedKtp apiConvertResponse(ResponseBody d) {
                try {
                    return Utility.gson().fromJson(d.string(), UploadedKtp.class );
                }catch (Exception e){
                    Utility.log("2020 0904 1506 "+e.getMessage());
                    return new UploadedKtp();
                }
            }

            @Override
            public void setCache(final UploadedKtp d) {

            }

            @Override
            public void getCache(final CommonCallback<UploadedKtp> cacheCb) {

            }
        });
    }

    public static void eregValidasi2(final Activity ctx, final RequestParamConfig rpc, String filePath,
                                     String email, final CommonCallback<ResponseBody> cb){
//        final AppDatabase appDb = AppDatabase.get(ctx);
//        rpc.reqPaging.size = AppConstant.LIST_SIZE;'
        rpc.isRelogin = true;

        final MultipartBody.Part partCode = MultipartBody.Part.createFormData("code", Integer.toString(CommonDTO.CODE_EREG_VALIDASI_2));
        final MultipartBody.Part partFile = Utility.pathToPartFile("file", filePath);
        final MultipartBody.Part partJson = MultipartBody.Part.createFormData("json", email);

        HttpReq.run(ctx, SharePref.SPRK_EREG_VALIDASI_2, rpc, new HttpCacheCallback<ResponseBody, ResponseBody>(){
            public Call<ResponseBody> requestMethod(String bearerAuth){
                return HttpReq.getApiMain(ctx).commonUpload(bearerAuth, partCode, partFile, partJson);
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

    private ApiReqUpload(){}

}
