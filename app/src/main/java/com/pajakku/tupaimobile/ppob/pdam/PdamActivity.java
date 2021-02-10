package com.pajakku.tupaimobile.ppob.pdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ppob.pulsa.PulsaValidationActivity;
import com.pajakku.tupaimobile.ppob.tokenpostpaid.TokenPostPaidValidationActivity;

public class PdamActivity extends AppCompatActivity {
Button continueButton;
Spinner list;
    EditText noHp , idPelanggan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdam);
        list = findViewById(R.id.ProductSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.daftar_pdam, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);

        continueButton = (Button)findViewById(R.id.ContinueButton);
        noHp = (EditText)findViewById(R.id.PhoneNumberEditText);
        idPelanggan = (EditText)findViewById(R.id.CustomerID);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getNomorHp = noHp.getText().toString();
                String getIdPelanggan = idPelanggan.getText().toString();
                if(!getNomorHp.isEmpty() && !getIdPelanggan.isEmpty()) {
                    if (getNomorHp.length() <= 10) {
                        Toast.makeText(getApplicationContext(), "No Hp kurang dari 10 digit", Toast.LENGTH_LONG).show();
                    }
                    else if(getIdPelanggan.length()<=10){
                        Toast.makeText(getApplicationContext(), "Id Pelanggan salah", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent i = new Intent(getApplicationContext(), PdamValidationActivity.class);
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