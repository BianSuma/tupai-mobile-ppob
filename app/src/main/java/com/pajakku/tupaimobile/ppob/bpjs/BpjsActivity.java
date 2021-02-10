package com.pajakku.tupaimobile.ppob.bpjs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ppob.pulsa.PulsaValidationActivity;

public class BpjsActivity extends AppCompatActivity {
Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpjs);

        final Spinner list = findViewById(R.id.ProductSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.daftar_bpjs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);

        continueButton = (Button)findViewById(R.id.ContinueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BpjsValidationActivity.class);
                startActivity(i);
            }
        });

    }
}