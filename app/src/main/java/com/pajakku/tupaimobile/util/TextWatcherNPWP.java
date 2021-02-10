package com.pajakku.tupaimobile.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by dul on 27/03/19.
 */

public class TextWatcherNPWP implements TextWatcher {
        private EditText editText;
        private boolean isAuto = false;

        public TextWatcherNPWP(EditText et){
                editText = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
                if(isAuto){
                        isAuto = false;
                        editText.setSelection(s.length());
                        return;
                }

                isAuto = true;
                editText.setText( Utility.toPrettyNpwp(s.toString().replaceAll("\\D", "")) );
        }
}
