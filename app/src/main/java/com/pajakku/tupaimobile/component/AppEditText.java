package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.util.TextWatcherMoney;
import com.pajakku.tupaimobile.util.TextWatcherNOP;
import com.pajakku.tupaimobile.util.TextWatcherNPWP;
import com.pajakku.tupaimobile.util.TextWatcherNoSK;

/**
 * Created by dul on 18/12/18.
 */

public class AppEditText extends TextInputLayout {

    public static int EDITTEXTTYPE_NPWP = 1;
    public static int EDITTEXTTYPE_MONEY = 2;
    public static int EDITTEXTTYPE_NO_SK = 3;
    public static int EDITTEXTTYPE_NOP = 4;

    public int editTextType = 0;
    public boolean constraintMandatory = false;
    public boolean constraintNoZero = false;
    public int constraintMin = -1;
    public int constraintMax = -1;
    public AppEditText confirmSourceField = null;

    public TextInputEditText inpText;

    public AppEditText(Context context, AttributeSet attrs){
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AttrsInput,
                0, 0);
        int inputType = 0;
        int minLines = 0;
        int maxLines = 1;
        String hint = "";
        Drawable rightIcon = null;
        try {
            inputType = a.getInt(R.styleable.AttrsInput_android_inputType, 0);
            minLines = a.getInt(R.styleable.AttrsInput_android_minLines, 0);
            maxLines = a.getInt(R.styleable.AttrsInput_android_maxLines, 1);
            hint = a.getString(R.styleable.AttrsInput_hint);
            rightIcon = a.getDrawable(R.styleable.AttrsInput_right_icon);
        } finally {
            a.recycle();
        }

        if( inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) ){
            setPasswordVisibilityToggleEnabled(true);
        }

        inpText = new TextInputEditText(context, attrs);
        inpText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inpText.setId(View.generateViewId());
        if(inputType != 0) inpText.setInputType(inputType);
        if(minLines != 0) inpText.setMinLines(minLines);
        inpText.setMaxLines(maxLines);
        inpText.setHint(hint);
        inpText.setGravity(Gravity.TOP|Gravity.LEFT);
        inpText.setCompoundDrawablesWithIntrinsicBounds(null, null, rightIcon, null);
        addView(inpText);

//        setErrorTextAppearance(R.style.);
    }

    public void setHint(int i){
        super.setHint(getResources().getString(i));
    }

    public void setText(String text){
        if(text == null) return;
        getEditText().setText(text);
    }

    // ----------------

    public String getValue(boolean nullable){
        String str = getEditText().getText().toString().trim();
        if(str.isEmpty()) return nullable?null:"";
        return str;
    }

    public String getText(){
        String str = getEditText().getText().toString().trim();
        if(str.length() > 0) return str;
        return null;
    }

    public String getTextNotNull(){
        return getEditText().getText().toString().trim();
    }

    public String getOnlyNumberNotNull(){
        return getEditText().getText().toString().replaceAll("\\D","");
    }

    public String getOnlyNumber(){
        String s = getOnlyNumberNotNull();
        if(s.length() > 0) return s;
        return null;
    }

    public String getMoneyNotNull(){
        return getEditText().getText().toString().replaceAll("\\D","");
    }

    public long getMoneyLongNotNull(){
        String s = getMoneyNotNull();
        if(s.length() > 0 ) return Long.parseLong(s);
        return 0;
    }

    public int getIntNotNull(){
        String s = getOnlyNumber();
        if(s != null) return Integer.parseInt(s);
        return 0;
    }

    public void setRightIcon(int icon){
        getEditText().setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0);
    }

    public void setConstraintNumber(){
        getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothint
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if( str.matches("\\d*\\D+\\d*") ){
                    str = str.replaceAll("\\D+","");
                    AppEditText.this.getEditText().setText( str );
                }
            }
        });
    }

    public void setConstraintNoMiddleSpace(){
        getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothint
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if( str.matches("\\S*\\s+\\S*") ){
                    str = str.replaceAll("\\s+","");
                    AppEditText.this.getEditText().setText( str );
                }
            }
        });
    }

    public void setConstraintMoney(AppEditText editTextTerbilang){
        editTextType = EDITTEXTTYPE_MONEY;
        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789.,"));
        getEditText().addTextChangedListener(new TextWatcherMoney(getEditText(), editTextTerbilang));
    }

    public void setConstraintNPWP(){
        editTextType = EDITTEXTTYPE_NPWP;
        constraintMin = constraintMax = 15;
        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789-."));
        getEditText().addTextChangedListener(new TextWatcherNPWP(getEditText()));
    }

    public void setConstraintNoSK(){
        editTextType = EDITTEXTTYPE_NO_SK;
        constraintMin = constraintMax = 15;
        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789/"));
        getEditText().addTextChangedListener(new TextWatcherNoSK(getEditText()));
    }

    public void setConstraintNOP(){
        editTextType = EDITTEXTTYPE_NOP;
        constraintMin = constraintMax = 18;
        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        getEditText().setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        getEditText().addTextChangedListener(new TextWatcherNOP(getEditText()));
    }


}
