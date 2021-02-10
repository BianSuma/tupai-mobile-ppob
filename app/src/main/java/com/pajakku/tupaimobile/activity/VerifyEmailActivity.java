package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;

@Deprecated
public class VerifyEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        AppActionBar actionBar = findViewById(R.id.verifyemail_actionbar);
        actionBar.init(0, null, null);

        Button btnSend = findViewById(R.id.verifyemail_btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSend();
            }
        });
    }

//    private void clickBack(){
//        finish();
//    }

    private void clickSend(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
