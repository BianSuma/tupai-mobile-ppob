package com.pajakku.tupaimobile.ppob.pulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;

public class PulsaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button continueButton;
    private EditText nohp;
    private Spinner list , harga;
    View bitMap;
    // private SpinnerAdapter spinnerAdapter;
    private ImageView image;
    //    private LinearLayout optionImage;
    String []listProvider = {"","TELKOMSEL","INDOSAT","TRI","AXIS","SMARTFREN","XL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa);

        nohp = (EditText) findViewById(R.id.PhoneNumberEditText);
        image = (ImageView) findViewById(R.id.LogoImageView);
        continueButton = (Button)findViewById(R.id.ContinueButton);
        list = findViewById(R.id.ProductSpinner);
        harga = findViewById(R.id.HargaSpinner);

        // LinearLayout.LayoutParams optionImage = new LinearLayout.LayoutParams(-1,398);
//        MarginLayoutParams.MATCH_PARENT = (MarginLayoutParams)optionImage;
        //int size = 20;



        //spinnerAdapter = (Spinner)findViewById(R.id.ProductSpinner);

//        list.setOnItemSelectedListener(this);
//        ArrayAdapter ad = new ArrayAdapter(
//                this,
//                android.R.layout.simple_spinner_item,
//                listProvider);
//        list.setAdapter(ad);

//        ArrayAdapter <CharSequence> ad2 = new ArrayAdapter.createFromResource(this, android.R.layout.simple_spinner_item,
//                listProvider);
//        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        list.setAdapter(ad2);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.daftar_provider, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);
        list.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.daftar_harga, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        harga.setAdapter(adapter2);

        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String getProvider = String.valueOf(list.getSelectedItem());
                if (getProvider == "TELKOMSEL")
                    image.setImageResource(R.drawable.telkomsel);
                else if (getProvider== "INDOSAT")
                    image.setImageResource(R.drawable.indosat);
                else if (getProvider== "TRI")
                    image.setImageResource(R.drawable.three);
                else if (getProvider== "AXIS")
                    image.setImageResource(R.drawable.axis);
                else if (getProvider== "XL")
                    image.setImageResource(R.drawable.xl);
                else if (getProvider== "SMARTFREN")
                    image.setImageResource(R.drawable.smartfren);
                else
                    image.setImageResource(R.mipmap.ic_launcher_foreground);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNomorHp = nohp.getText().toString();
                if(!getNomorHp.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Nomor HP tidak boleh kosong", Toast.LENGTH_LONG).show();

                }
                else if(getNomorHp.length() < 10 ){
                    Toast.makeText(getApplicationContext(), "Nomor HP kurang dari 10 digit", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent i = new Intent(getApplicationContext(),PulsaValidationActivity.class);
                    startActivity(i);
                }
            }
        });





    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}