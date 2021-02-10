package com.pajakku.tupaimobile.util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 13/02/19.
 */

public class Validator {
    private List<AppEditText> edits = new ArrayList<>();


    public void addEdit(AppEditText edit){
        edits.add(edit);
    }

    public boolean check(Context context){

        boolean isValid = true;
        String errorString;
        String str;
        EditText editText;
        for(AppEditText e : edits){
            if(e.getVisibility() != View.VISIBLE) continue;
            editText = e.getEditText();

            if(e.editTextType == AppEditText.EDITTEXTTYPE_MONEY) {
                str = e.getMoneyNotNull();
            }else if(e.editTextType == AppEditText.EDITTEXTTYPE_NPWP || e.editTextType == AppEditText.EDITTEXTTYPE_NO_SK || e.editTextType == AppEditText.EDITTEXTTYPE_NOP){
                str = e.getOnlyNumberNotNull();
            }else {
                str = editText.getText().toString().trim();
                e.setText(str);
            }

            errorString = null;
            if( e.constraintMandatory ){
                if( str.length() == 0 ){
                    errorString = context.getString(R.string.util_validator_mandatory);
                }
            }

            if(e.constraintMin != -1){
                if(str.length() > 0) {
                    if (e.constraintMin > str.length()) {
                        errorString = context.getString(R.string.util_validator_minimum, e.constraintMin);
                    }
                }
            }

            if(e.constraintMax != -1){
                if(e.constraintMax < str.length()){
                    errorString = context.getString(R.string.util_validator_maximum, e.constraintMax);
                }
            }

            if(e.confirmSourceField != null){
                String confirmStr = e.confirmSourceField.getEditText().getText().toString().trim();
                if(!confirmStr.equals(str)){
                    errorString = context.getString(R.string.util_validator_mismatchconfirm);
                }
            }

            if(e.constraintNoZero){
                if(str.equals("0")){
                    errorString = context.getString(R.string.util_validator_nozeromoney);
                }
            }

            if(errorString != null) {
                isValid = false;
            }
            editText.setError( errorString );
        }

        if(!isValid) Toast.makeText(context, context.getString(R.string.util_validator_invalidinput), Toast.LENGTH_SHORT).show();

        return isValid;
    }
}
