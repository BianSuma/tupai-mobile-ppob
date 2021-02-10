package com.pajakku.tupaimobile.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 26/12/18.
 */

@Deprecated
public final class PayPPhCreateWPDialog {
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
            dialog.setContentView(R.layout.dialog_paypph_createwp);

            DialogTopBar topBar = dialog.findViewById(R.id.dialogpaypphcreatewp_actionbar);
            topBar.init(null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayPPhCreateWPDialog.hide();
                }
            });

            LinearLayout btnInquiry = dialog.findViewById(R.id.dialogpaypphcreatewp_btn_inquiry);
            btnInquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Button btnSave = dialog.findViewById(R.id.dialogpaypphcreatewp_btn_save);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onClickClose();
                    PayPPhCreateWPDialog.hide();
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

    private PayPPhCreateWPDialog(){}
}
