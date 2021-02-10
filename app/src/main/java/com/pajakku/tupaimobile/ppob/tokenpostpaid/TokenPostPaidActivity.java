package com.pajakku.tupaimobile.ppob.tokenpostpaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.googlecode.mp4parser.authoring.Edit;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ppob.pulsa.PulsaValidationActivity;

public class TokenPostPaidActivity extends AppCompatActivity {
Button continueButton;
Spinner list;
EditText noHp , noMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_post_paid);
        list = (Spinner)findViewById(R.id.ProductSpinner);
        noHp = (EditText)findViewById(R.id.PhoneNumberEditText);
        noMeter = (EditText)findViewById(R.id.TokenNumberEditText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.daftar_harga, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);
//        list.setOnItemSelectedListener(this);

        continueButton = (Button)findViewById(R.id.ContinueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNomorHp = noHp.getText().toString();
                String getNomorMeter = noMeter.getText().toString();
                if(!getNomorHp.isEmpty() && !getNomorMeter.isEmpty()) {
                    if (getNomorHp.length() <= 10) {
                        Toast.makeText(getApplicationContext(), "No Hp kurang dari 10 digit", Toast.LENGTH_LONG).show();
                    }
                    else if(getNomorMeter.length()<=10){
                        Toast.makeText(getApplicationContext(), "No Meteran salah", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent i = new Intent(getApplicationContext(), TokenPostPaidValidationActivity.class);
                        startActivity(i);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "No Hp atau No Meteran tidak boleh kosong", Toast.LENGTH_LONG).show();
                }



        }

    });
}
}
