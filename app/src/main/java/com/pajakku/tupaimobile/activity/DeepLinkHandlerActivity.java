package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppTextView;
import com.pajakku.tupaimobile.model.actdata.ActDataDeepLinkHandler;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.Utility;

public class DeepLinkHandlerActivity extends BaseActivity {

    private static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_DEEPLINKHANDLER;

    public static final String DEEPLINK_PARAM_SSPID = "xp1";
    public static final String DEEPLINK_PARAM_INSTITUTION = "institutionCode";
    public static final String DEEPLINK_PARAM_VA = "vaNo";

    public ActDataDeepLinkHandler actData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_deeplinkhandler);

        initData(savedInstanceState);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ACTDATA_KEY, actData);
    }


    private void initData(Bundle savedInstanceState){

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            String action = intent.getAction();  // android.intent.action.VIEW
            Uri uri = intent.getData();  // app://com.nebula.sobatpajak.intent/command_intent&id=99
            String billIdLongStr = uri.getQueryParameter(DEEPLINK_PARAM_SSPID);
            if(billIdLongStr == null) billIdLongStr = "0";
            Utility.log(action + " __ "+uri.toString());

            actData = new ActDataDeepLinkHandler();
//            actData.billIdLong = Long.parseLong(billIdLongStr);
        }else{
            actData = (ActDataDeepLinkHandler) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }
}
