package com.pajakku.tupaimobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.TopupValueSpinAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.TopupWidget;

import java.util.ArrayList;
import java.util.List;

public class PayTokenPLNActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_token_pln);

        AppActionBar actionBar = findViewById(R.id.paytokenpln_appactionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayTokenPLNActivity.this.finish();
            }
        }, null);

        TopupWidget topupWidget = findViewById(R.id.paytokenpln_topupwidget);
        topupWidget.init(300000);

        List<String> spinArr = new ArrayList<>();
        spinArr.add("Rp 50.000");
        spinArr.add("Rp 100.000");
        TopupValueSpinAdapter adapter = new TopupValueSpinAdapter(this, R.string.paytokenpln_hint_tokenamount, R.layout.row_spin_item, spinArr);
        Spinner spinTopup = findViewById(R.id.paytokenpln_spin_nominal);
        spinTopup.setAdapter(adapter);
    }
}
