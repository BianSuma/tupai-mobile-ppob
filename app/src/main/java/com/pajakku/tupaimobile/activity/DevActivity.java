package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiReqEreg;
import com.pajakku.tupaimobile.api.ApiReqUpload;
import com.pajakku.tupaimobile.component.AppTextView;
import com.pajakku.tupaimobile.ifc.SuccessFailCallback;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.ereg.EregDataByEmail;
import com.pajakku.tupaimobile.model.dto.ereg.ReqValidasi1;
import com.pajakku.tupaimobile.model.dto.ereg.UploadedKtp;
import com.pajakku.tupaimobile.model.dto.response.ResponseMe;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

import okhttp3.ResponseBody;

public class DevActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_dev);

        findViewById(R.id.devact_btn_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ApiReqEreg.getLog(context, new RequestParamConfig(), new CommonCallback<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//
//                    }
//                });

//                ApiReqEreg.getListData(context, new RequestParamConfig(), new CommonCallback<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//
//                    }
//                });

                ApiReqEreg.getDataByEmail(context, new RequestParamConfig(), "shshhshsg@gnail.com", new SuccessFailCallback<EregDataByEmail, ResponseDTO>() {
                    @Override
                    public void onSuccess(EregDataByEmail data) {

                    }

                    @Override
                    public void onFail(ResponseDTO data) {

                    }
                });

//                ApiReqUpload.eregValidasi2(context, new RequestParamConfig(), AppTester.FILE_IMG, "dullohmail@yahoo.com", new CommonCallback<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//
//                    }
//                });

//                ApiReqUpload.eregUploadKtp(context, new RequestParamConfig(), "/storage/7439-1818/ref/eregKtp.jpg", new CommonCallback<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//
//                    }
//                });

//                ReqValidasi1 body = new ReqValidasi1();
//                body.nikExist = true;
//                body.nikValid = true;
//                body.jenisWp = 2;  // USAHAWAN
//                body.jenisKelamin = "L";
//                body.kategoriWp = "1";  // KATEGORIWP_OP
//                body.stsNikahWp = "1";  // nikah
//                body.namaWp = "M. Subhan Abdulloh";
//                body.emailWp = "dullohmail@yahoo.com";
//                body.kdNegaraWp = "ID";
//                body.noIDWp = "3203122910860003";
//                body.noKKWp = "3271040301180006";
//                body.tmpLahirWp = "Cianjur";
//                body.tglLahirWp = "29-10-1986";
//                body.almKTPJalan = "Loji";
//                body.almKTPNomor = "";
//                body.almKTPKdWilayah = "3271041016";
//                body.photoKtpFilename = "97a0573e-e38a-48e0-b469-d509ba6ca1dd_eregKtp.jpg";
//                body.nomorHPWp = "08987020869";
//                body.domisili.almDomisiliBlok = "";
//                body.domisili.almDomisiliJalan = "Loji";
//                body.domisili.almDomisiliRT = "2";
//                body.domisili.almDomisiliRW = "1";
//                ApiReqEreg.validasi1(context, new RequestParamConfig(), body, new CommonCallback<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//
//                    }
//                });

//                ApiReqEreg.kodeWilayah(context, new RequestParamConfig(), "neglasari", new CommonCallback<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//
//                    }
//                });
            }
        });
    }

    public static void startAct(Activity act){
        Intent itn = new Intent(act, DevActivity.class);
        act.startActivity(itn);
    }
}