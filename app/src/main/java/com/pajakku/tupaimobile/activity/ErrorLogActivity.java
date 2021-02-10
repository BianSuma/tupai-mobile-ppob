package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.api.ApiReqTest;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.util.CommonCallback;

import okhttp3.ResponseBody;

public class ErrorLogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_errorlog);

//        findViewById(R.id.devact_btn_api).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                CommonDTO body = new CommonDTO();
////                body.code = CommonDTO.CODE_GENERATE_MPN_URL;
////                ApiMain.common(context, new RequestParamConfig(), body, new CommonCallback<CommonDTO>() {
////                    @Override
////                    public void onSuccess(CommonDTO data) {
////
////                    }
////                });
//                ApiReqTest.testAuth(context, new RequestParamConfig(), new CommonCallback<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//
//                    }
//                });
//            }
//        });
    }

    public static void startAct(Activity act){
        Intent itn = new Intent(act, ErrorLogActivity.class);
        act.startActivity(itn);
    }
}