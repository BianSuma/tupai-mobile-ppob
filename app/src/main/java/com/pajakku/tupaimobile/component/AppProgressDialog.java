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

public final class AppProgressDialog {

    private static Dialog dialog = null;

    public static void show(Context context, int message, AppListener listener){
        show(context, context.getString(message), listener);
    }
    public static void show(Context context, String message, final AppListener listener)
    {
        hide();

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_progress);

        TextView progressText = (TextView) dialog.findViewById(R.id.dialogprogress_text);
        progressText.setText(message);

        Button btnCancel = dialog.findViewById(R.id.dialogprogress_btn_cancel);
        btnCancel.setVisibility( listener != null ? View.VISIBLE : View.GONE);
        if(listener != null) btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCancel();
                AppProgressDialog.hide();
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void hide() {
        if (dialog != null) {
            if( dialog.isShowing() ) dialog.dismiss();
            dialog = null;
        }
    }


    public interface AppListener {
        void onClickCancel();
    }

    private AppProgressDialog(){}
}
