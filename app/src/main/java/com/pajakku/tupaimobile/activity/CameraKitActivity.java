package com.pajakku.tupaimobile.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.VideoResult;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.actdata.ActDataCameraKit;
import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.DateFunc;
import com.pajakku.tupaimobile.util.Utility;

import java.io.File;

public class CameraKitActivity extends BaseActivity {

    private static final String ACTDATA_KEY = AppConstant.SP_ACTDATA_CAMERA_KIT;

    private ActDataCameraKit actData;

    private CameraView cameraView;
//    private CameraKitView cameraKitView;
//    private Camera camera;
//    private SurfaceView surfaceView;

    private AppCompatTextView lblTickSec;
    private Handler handler;
    private Runnable runnable;
    private long tickSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity context = this;
        setContentView(R.layout.activity_camerakit);

        initData(savedInstanceState);

        cameraView = findViewById(R.id.camerakit_inp_cameraview);
        cameraView.setLifecycleOwner(this);
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                stopTick();
                File file = result.getFile();
                if( context.isFinishing() || context.isDestroyed() || !actData.isResume){
                    file.delete();
                    return;
                }
                String path = file.getAbsolutePath();
                Utility.toast(context, "File video "+path);

                HolderCommon1Val holder = new HolderCommon1Val();
                holder.object = holder.str = path;

                Intent itn = new Intent();
                itn.putExtra(AppConstant.SP_ACTIVITYRESULT, holder);
                setResult(Activity.RESULT_OK, itn);
                finish();
            }

        });

        handler = new Handler();

        lblTickSec = findViewById(R.id.camerakit_lbl_ticksec);

        setBotBtn(R.id.camerakit_btn_record);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        cameraKitView.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        actData.isResume = true;

        if( ! CameraUtils.hasCameras(this) ){
            Utility.toast(this, "Perangkat tidak mendukung kamera.");
            finish();
            return;
        }

        Utility.verifyStoragePermissions(this, AppConstant.ACTRES_COMMON);

        String botBtnLbl = "";
        if(cameraView.isTakingVideo()){
            botBtnLbl = "Berhenti Perekaman";
        }else{
            botBtnLbl = "Rekam";
        }
        btnBot.setText(botBtnLbl);

        checkInput(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        actData.isResume = false;
    }

    @Override
    protected void onStop() {
//        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//
        outState.putSerializable(ACTDATA_KEY, actData);
    }

    private void initData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
//            Intent itn = getIntent();
//            ActParamSobDaftarNpwpBiometrik ap = (ActParamSobDaftarNpwpBiometrik) itn.getSerializableExtra(ACTDATA_KEY);

            actData = new ActDataCameraKit();

        }else{
            actData = (ActDataCameraKit) savedInstanceState.getSerializable(ACTDATA_KEY);
        }
    }

    @Override
    public void save() {
        String botBtnLbl = "";
        if(cameraView.isTakingVideo()){
//            actData.isRecording = false;
//            cameraKitView.stopVideo();
            cameraView.stopVideo();
            botBtnLbl = "Rekam";
            stopTick();
        }else{
//            actData.isRecording = true;
//            cameraKitView.startVideo();
            cameraView.takeVideo( new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "sobpjk"+ DateFunc.nowTimeFull() +".mp4") );
            botBtnLbl = "Berhenti Perekaman";
            startTick();
        }
        btnBot.setText(botBtnLbl);
    }

    private void startTick(){
        lblTickSec.setText(  DateFunc.msToDuration(tickSec*1000) );
        lblTickSec.setVisibility(View.VISIBLE);
        runnable = new Runnable() {
            @Override
            public void run() {
                tickSec++;
                startTick();
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void stopTick(){
        lblTickSec.setVisibility(View.GONE);
        tickSec = 0;
        if(runnable != null) handler.removeCallbacks(runnable);
    }

    public static void startAct(Activity act, int reqCode){
//        ActParamSobDaftarNpwpBiometrik ap = new ActParamSobDaftarNpwpBiometrik();
//        ap.email = email;
//        ap.npwpHeadId = npwpHeadId;

        Intent itn = new Intent(act, CameraKitActivity.class);
//        itn.putExtra(ACTDATA_KEY, ap);
        act.startActivityForResult(itn, reqCode);
    }
}
