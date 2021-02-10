package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppTextView;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConf;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SharePref;
import com.pajakku.tupaimobile.util.Utility;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_about);

        AppTextView tvVer = findViewById(R.id.about_label_version);
        tvVer.setText( getString(R.string.about_label_version, getPInfo().versionName) );

        tvVer = findViewById(R.id.about_label_versioncode);
        tvVer.setText( getString(R.string.about_label_versioncode, getVersionCode()) );

        tvVer = findViewById(R.id.about_label_builddate);
        tvVer.setText( getString(R.string.about_label_builddate, AppConf.APP_BUILD_DATE) );

        findViewById(R.id.about_btn_del_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showConfirmationDialog(context, "Data di server tetap tersedia. Hapus cache?", new CommonCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        SharedPreferences.Editor editor = SharePref.getInstance(context).edit();
                        editor.clear();
                        editor.commit();
                        context.finish();
                    }
                });
            }
        });

        // @test
        if(!AppConf.NO_DEV_MARK) {
            View devBtn = findViewById(R.id.about_btn_dev);
            devBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DevActivity.startAct(context);
                }
            });
            devBtn.setVisibility(View.VISIBLE);
        }
    }
}
