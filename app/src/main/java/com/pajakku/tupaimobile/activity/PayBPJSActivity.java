package com.pajakku.tupaimobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.TopupWidget;

public class PayBPJSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bpjs);

        AppActionBar actionBar = findViewById(R.id.paybpjs_actionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayBPJSActivity.this.finish();
            }
        }, null);

        TopupWidget topupWidget = findViewById(R.id.paybpjs_topupwidget);
        topupWidget.init(300000);
    }
}
