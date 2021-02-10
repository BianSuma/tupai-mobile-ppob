package com.pajakku.tupaimobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.TopupWidget;

public class PayTagihanPLNActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tagihan_pln);

        AppActionBar appActionBar = findViewById(R.id.paytagihanpln_appactionbar);
        appActionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayTagihanPLNActivity.this.finish();
            }
        }, null);

        TopupWidget topupWidget = findViewById(R.id.paytagihanpln_topupwidget);
        topupWidget.init(50000);
    }
}
