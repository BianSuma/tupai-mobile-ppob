package com.pajakku.tupaimobile.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 26/12/18.
 */

public final class InputNTPNDialog {
//    public static AppProgressDialog customProgress = null;
    private static Dialog dialog = null;

//    public static AppProgressDialog getInstance() {
//        if (customProgress == null) {
//            customProgress = new AppProgressDialog();
//        }
//        return customProgress;
//    }

    public static void show(Context context, final AppListener listener)
    {
        if(dialog == null) {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.setContentView(R.layout.dialog_input_ntpn);

            DialogTopBar topBar = dialog.findViewById(R.id.inputntpn_actionbar);
            topBar.init(null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputNTPNDialog.hide();
                }
            });

            Button btnNtpn = dialog.findViewById(R.id.dialoginpntpn_btn_ntpn);
            btnNtpn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onClickClose();
                    InputNTPNDialog.hide();
                }
            });

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
        }

        dialog.show();
    }

    public static void hide() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    public interface AppListener {
        void onClickClose();
    }

    private InputNTPNDialog(){}
}
