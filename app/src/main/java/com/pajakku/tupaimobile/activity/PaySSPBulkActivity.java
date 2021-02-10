package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.list.SSPBulkAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;

public class PaySSPBulkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_sspbulk);

        AppActionBar actionBar = findViewById(R.id.paysspbulk_actionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PaySSPBulkActivity.this.finish();
            }
        }, null);

        Intent itn = getIntent();

        long total = 0;
        ArrayList<Sspunpaid> selectedList = (ArrayList<Sspunpaid>) itn.getSerializableExtra(AppConstant.ITN_FRAGSSP_BULK);
        for(Sspunpaid s: selectedList){
            total += s.amount;
        }

        SSPBulkAdapter adapterList = new SSPBulkAdapter(this);
        adapterList.addAll(selectedList);

        ListView listView = findViewById(R.id.paysspbulk_listview);
        listView.setAdapter(adapterList);

        TextView tvBulkTotal = findViewById(R.id.paysspbulk_val_total);
        tvBulkTotal.setText( Utility.toMoney(true, total) );
    }
}
