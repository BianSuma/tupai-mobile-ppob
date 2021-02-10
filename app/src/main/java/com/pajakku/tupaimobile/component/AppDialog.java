package com.pajakku.tupaimobile.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;

/**
 * Created by dul on 26/12/18.
 */

public final class AppDialog {
//    public static AppProgressDialog customProgress = null;
    private static Dialog mDialog = null;

//    public static AppProgressDialog getInstance() {
//        if (customProgress == null) {
//            customProgress = new AppProgressDialog();
//        }
//        return customProgress;
//    }

    public static void show(Context context, int msgRes, String topupAmount, final AppListener listener)
    {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_info);

        TextView labelTop = (TextView) mDialog.findViewById(R.id.dialoginfo_label_top);
        labelTop.setText(msgRes);

        TextView tvAmount = (TextView) mDialog.findViewById(R.id.dialoginfo_var_topupamount);
        tvAmount.setText(topupAmount);

        Button btnClose = mDialog.findViewById(R.id.dialoginfo_btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.onClickClose();
                AppDialog.hide();
            }
        });

        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public static void hide() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


    public interface AppListener {
        void onClickClose();
    }

    private AppDialog(){}
}
