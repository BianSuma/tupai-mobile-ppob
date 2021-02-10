package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.pajakku.tupaimobile.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(isShowSplash()){
            playSplash();
        }else{
            gotoMain();
        }
    }

    private void playSplash(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMain();
            }
        }, 2000);
    }

    // TODO: fix
//    private void gotoFront(){
//        Intent intent = new Intent(this, FrontActivity.class);
//        startActivity(intent);
//        finish();
//    }

    private void gotoMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
