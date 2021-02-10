package com.pajakku.tupaimobile.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pajakku.tupaimobile.component.AppEditText;

/**
 * Created by dul on 27/03/19.
 */

public class TextWatcherMoney implements TextWatcher {
        private EditText editText;
        private AppEditText terbilang;
        private boolean isAuto = false;

        public TextWatcherMoney(EditText et, AppEditText terbilang){
                editText = et;
                this.terbilang = terbilang;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
                String str;

                if(isAuto){
                        isAuto = false;
                        editText.setSelection(s.length());
                        if(s.length() > 0) {
                                str = s.toString().replaceAll("\\D", "");
                                if(terbilang != null) terbilang.setText( Utility.terbilang(Long.valueOf(str)) + "Rupiah" );
                        }
                        else {
                                if(terbilang != null) terbilang.setText("");
                        }
                        return;
                }

                str = s.toString().replaceAll("\\D", "");

                if(str.length() <= 0 ){
                        isAuto = true;
                        editText.setText("");
                        return;
                }
                long l = Long.parseLong(str);
                isAuto = true;
                editText.setText( Utility.toMoney(false, l) );
        }
}
