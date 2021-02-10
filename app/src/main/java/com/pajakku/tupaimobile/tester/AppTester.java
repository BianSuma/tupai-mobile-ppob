package com.pajakku.tupaimobile.tester;

import android.content.Context;
import android.util.Log;

import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.billing.RespKjs;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.UploadedKtp;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConf;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.SuccessFailCallbackVoid;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

// @test
@Deprecated
public final class AppTester {

    public static boolean NO_TEST_PIECE = false;  // mark biar tdk lupa
//    public static boolean NO_TEST_VIEW = false;
//    public static boolean NO_LONG_CLICK = false;
//    public static boolean DATA_FROM_SERVER = true;

    public static String FILE_IMG = "/storage/7439-1818/ref/eregKtp.jpg";
    public static String EMAIL = "dullohmail@yahoo.com";

    public static <T> boolean request(Context ctx, String rk, SuccessFailCallback<T, ResponseDTO> cb) {
        if (AppConf.URL_TYPE != AppConf.URLTYPE_DUMMY) return false;

//        if(NO_TEST_PIECE) {
//            if (DATA_FROM_SERVER) return false;
//        }else{
//            if(DATA_FROM_SERVER && !(
//                    rk.equals(SharePref.SPRK_EREG_UPLOAD_KTP) ||
//                    rk.equals(SharePref.SPRK_EREG_VALIDASI_1) ||
//                    rk.equals(SharePref.SPRK_EREG_VALIDASI_2) ||
//                    rk.equals(SharePref.SPRK_EREG_DATA_BY_EMAIL) ||
//                    rk.equals(SharePref.SPRK_EREG_UPDATE_KELENGKAPAN) ||
//                    rk.equals(SharePref.SPRK_EREG_SUBMITWP)
//            )) return false;
//        }

        Utility.log("access dummy data");
//
//        if (AppTesterSobatGajiMaster.request(rk, cb)) return true;
//        if (AppTesterAuth.request(rk, cb)) return true;
//
//        if (AppTesterSobatSetor.request(rk, cb)) return true;
//        if (AppTesterSobatLapor.request(rk, cb)) return true;

//        if(rk.equals(SharePref.SPRK_UPD_FCM)){
//            ResponseBody d = ResponseBody.create(MediaType.get("application/json"), "{}");
//            cb.onSuccess((T)d);
//            return true;
//        }
        if(rk.equals(SharePref.SPRK_GET_LIST_TAX_TYPE)){
//            [{"createdBy":"system","createdDate":"2020-07-12 22:15","lastModifiedBy":"system","lastModifiedDate":"2020-07-12 22:15",
//                    "id":"05137740-fc98-11e9-aad5-362b9e155667","code":"411111","name":"PPh Minyak Bumi","active":true}]
            RespListTaxType d;
            List<RespListTaxType> l = new ArrayList<>();
            d = new RespListTaxType();  // --
            d.id = "99_test";
            d.code = "411128";
            d.name = "Test KJP";
            l.add(d);
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), Utility.gson().toJson(l));
            cb.onSuccess((T)r);
            return true;
        }
        if(rk.equals(SharePref.SPRK_GET_LIST_KJS)){
            RespKjs d;
            List<RespKjs> l = new ArrayList<>();
            d = new RespKjs();  // --
            d.id = "99_test";
            d.code = "420";
            d.name = "Test KJS";
            d.month1Active = true;
            d.month2Active = false;
            d.nopActive = false;
            d.noSkActive = false;
            d.subjekPajakActive = false;
            l.add(d);
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), Utility.gson().toJson(l));
            cb.onSuccess((T)r);
            return true;
        }
        if(rk.equals(SharePref.SPRK_EREG_UPLOAD_KTP)){
            UploadedKtp d = new UploadedKtp();
            d.name = "test_uploaded.jpg";
            d.origin = "test_uploaded.jpg";
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), Utility.gson().toJson(d));
            cb.onSuccess((T)r);
            return true;
        }
        if(rk.equals(SharePref.SPRK_EREG_VALIDASI_1)){
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), "{}");
            cb.onSuccess((T)r);
            return true;
        }
        if(rk.equals(SharePref.SPRK_EREG_VALIDASI_2)){
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), "{}");
            cb.onSuccess((T)r);
            return true;
        }
        if(rk.equals(SharePref.SPRK_EREG_DATA_BY_EMAIL)){
            EregDataByEmail d = new EregDataByEmail();
            d.status = "DATA SIAP DI KIRIM";
            d.wp.namaWp = "Someone WP";
            d.wp.noIDWp = "1234567890123456";
            d.wp.emailWp = "user@site.com";
            d.wp.nomorHPWp = "08987020869";
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), Utility.gson().toJson(d));
            cb.onSuccess((T)r);
            return true;
        }
        if(rk.equals(SharePref.SPRK_EREG_UPDATE_KELENGKAPAN)){
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), "{}");
            cb.onSuccess((T)r);
            return true;
        }
        if(rk.equals(SharePref.SPRK_EREG_SUBMITWP)){
            ResponseBody r = ResponseBody.create(MediaType.get("application/json"), "{}");
            cb.onSuccess((T)r);
            return true;
        }

        return false;
    }

    private AppTester(){}
}
