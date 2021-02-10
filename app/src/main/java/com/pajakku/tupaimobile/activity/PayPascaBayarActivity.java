package com.pajakku.tupaimobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.TopupWidget;

public class PayPascaBayarActivity extends BaseActivity {

    private AppEditText etCustNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pasca_bayar);

        AppActionBar actionBar = findViewById(R.id.paypascabayar_appactionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPascaBayarActivity.this.finish();
            }
        }, null);

        TopupWidget topupWidget = findViewById(R.id.paypascabayar_topupwidget);
        topupWidget.init(300000);

        RadioGroup radioProvider = findViewById(R.id.paypascabayar_radiogroup_provider);
        radioProvider.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                clickRadioProvider(checkedId);
            }
        });

        if((etCustNum = findViewById(R.id.paypascabayar_inp_customernumber))==null) throw new RuntimeException();
    }

    private void clickRadioProvider(int checkedId){
        int rightIcon;
        switch (checkedId){
            case R.id.paypascabayar_radio_telkom:
                rightIcon = R.drawable.ic_paypascabayar_small_telkom;
                break;
            case R.id.paypascabayar_radio_indihome:
                rightIcon = R.drawable.ic_paypascabayar_small_indihome;
                break;
            case R.id.paypascabayar_radio_finpay:
                rightIcon = R.drawable.ic_paypascabayar_small_finpay;
                break;
            case R.id.paypascabayar_radio_kartuhalo:
                rightIcon = R.drawable.ic_paypascabayar_small_kartuhalo;
                break;
            default:
                return;
        }
        etCustNum.setRightIcon(rightIcon);
    }
}
