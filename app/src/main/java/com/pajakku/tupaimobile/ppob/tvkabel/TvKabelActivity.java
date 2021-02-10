package com.pajakku.tupaimobile.ppob.tvkabel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ppob.pulsa.PulsaValidationActivity;

public class TvKabelActivity extends AppCompatActivity {
Button continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_kabel);
        final Spinner list = findViewById(R.id.ProductSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tv_kabel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);

        continueButton = (Button)findViewById(R.id.ContinueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TvKabelValidationActivity.class);
                startActivity(i);
            }
        });

    }
}