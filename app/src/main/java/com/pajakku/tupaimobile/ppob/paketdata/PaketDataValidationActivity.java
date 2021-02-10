package com.pajakku.tupaimobile.ppob.paketdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pajakku.tupaimobile.R;

public class PaketDataValidationActivity extends AppCompatActivity {
    Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_data_validation);
        backbtn = (Button)findViewById(R.id.BackButton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}