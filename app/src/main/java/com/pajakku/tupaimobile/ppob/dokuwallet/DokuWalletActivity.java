package com.pajakku.tupaimobile.ppob.dokuwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ppob.pulsa.PulsaValidationActivity;

public class DokuWalletActivity extends AppCompatActivity {
Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doku_wallet);

        continueButton = (Button)findViewById(R.id.ContinueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DokuWalletValidationActivity.class);
                startActivity(i);
            }
        });

    }
}