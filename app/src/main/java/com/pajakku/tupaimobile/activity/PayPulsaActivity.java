package com.pajakku.tupaimobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.list.PulsaProductAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.TopupWidget;
import com.pajakku.tupaimobile.model.dto.PulsaProduct;

import java.util.ArrayList;
import java.util.List;

public class PayPulsaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_pay_pulsa);

        AppActionBar actionBar = findViewById(R.id.paypulsa_actionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PayPulsaActivity.this.finish();
            }
        }, null);

        TopupWidget topupWidget = findViewById(R.id.paypulsa_topupwidget);
        topupWidget.init(300000);

        List<PulsaProduct> pulsaProductList = new ArrayList<>();
        for(int i=0; i<20; i++) pulsaProductList.add(new PulsaProduct("Pulsa 1"+i+"0000"));
        PulsaProductAdapter pulsaProductAdapter = new PulsaProductAdapter(this, pulsaProductList);
        ListView listView = findViewById(R.id.paypulsa_listview);
        listView.setAdapter(pulsaProductAdapter);
    }
}
