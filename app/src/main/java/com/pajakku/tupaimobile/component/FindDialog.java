package com.pajakku.tupaimobile.component;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.TopupValueSpinAdapter;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 26/12/18.
 */

@Deprecated
public final class FindDialog {

    private static final String fieldValues[] = {"name","npwp","year"};

    public static void show(AppCompatActivity activity, final CommonCallback<FilterParam> listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.ConfirmDialogTheme);
        builder.setTitle(R.string.comp_finddialog_title);

        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_find, null);

        List<String> spinArr = new ArrayList<>();
        spinArr.add(activity.getString(R.string.comp_finddialog_valfilter_name));
        spinArr.add(activity.getString(R.string.comp_finddialog_valfilter_npwp));
        spinArr.add(activity.getString(R.string.comp_finddialog_valfilter_year));
        TopupValueSpinAdapter spinAdapter = new TopupValueSpinAdapter(activity, R.string.comp_finddialog_inp_field, R.layout.row_spin_item, spinArr);
        final Spinner spinField = v.findViewById(R.id.dialogfind_spin_field);
        spinField.setAdapter(spinAdapter);

        final AppEditText inpQuery = v.findViewById(R.id.dialogfind_inp_query);
        if(inpQuery == null) throw new RuntimeException();

        builder.setView( v );

        builder.setPositiveButton(R.string.comp_finddialog_btn_find, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String q = inpQuery.getTextNotNull();

                FilterParam fdp = new FilterParam();
                fdp.field = fieldValues[ spinField.getSelectedItemPosition() ];
                fdp.query = (q.length() > 0 ? q : null);
                listener.onSuccess(fdp);
            }
        });
        builder.setNegativeButton(R.string.global_dialogprogress_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private FindDialog(){}
}
