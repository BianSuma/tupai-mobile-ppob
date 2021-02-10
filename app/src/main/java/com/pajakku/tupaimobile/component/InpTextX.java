package com.pajakku.tupaimobile.component;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.BaseInputStatus;
import com.pajakku.tupaimobile.util.CommonCallback;


public class InpTextX extends BaseInput implements TextWatcher{
    public static final int TYPE_KITAS = 1;
    public static final int TYPE_EMAIL = 2;

    private int type;
    private TextInputLayout inpLay;
    public TextInputEditText editText;
//    private TextWatcher textWatcher;
    private CommonCallback<BaseInput> cb;
    public int constraintMin;

    private boolean constraintNoZero;

    public InpTextX(Activity ctx, View rv, int id, final CommonCallback<BaseInput> cb){
        super(ctx, rv);
        this.cb = cb;

//        textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String text = s.toString().trim();
//                switch (type){
//                    case TYPE_KITAS:
//                        if(text.startsWith("0")) setValueUnchange(text.substring(1));
//                        break;
//                }
//                if(cb != null) cb.onSuccess(InpTextX.this);
//            }
//        };

        setInpLay(id);
    }

    // idTextInpLay bisa berupa ID dari TextInputLayout / TextInputEditText
    private void setInpLay(int idTextInpLay){
        inpLay = (TextInputLayout)findViewById(idTextInpLay);
        if(inpLay != null) editText = (TextInputEditText) inpLay.getEditText();
        else editText = (TextInputEditText)findViewById(idTextInpLay);

        editText.addTextChangedListener(this);
    }

    public void setHint(String s){
        inpLay.setHint(s);
    }

    public void setPasswordToggle(){
//        setRightIcon(R.drawable.ic_detail, new CommonCallback<Void>() {
//            @Override
//            public void onSuccess(Void data) {
//                isPasswordShow = !isPasswordShow;
//                if(isPasswordShow) {
//                    inpText.setInputType(InputType.TYPE_CLASS_TEXT);
//                }else {
//                    inpText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//            }
//        });
//        inpText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu_lock_grey,0,R.drawable.ic_detail,0);
        editText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye,0);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextInputEditText et = (TextInputEditText)v;
                if(event.getAction() == MotionEvent.ACTION_DOWN && event.getRawX() >= (v.getRight() - et.getCompoundDrawables()[2].getBounds().width())) {  // 2 = DRAWABLE_RIGHT
//                    if(callback != null) callback.onSuccess(null);
                    if(et.getInputType() == InputType.TYPE_CLASS_TEXT){
                        et.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }else{
                        et.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                    return true;
                }
                v.performClick();
                return false;
            }
        });
    }

    // ------------ TYPE

    public void setTypeKitas(){
        type = TYPE_KITAS;
    }

    public void setTypeEmail(){
        type = TYPE_EMAIL;
    }

    // SET VALUE

    public void setValueUnchange(String v){
        if(v == null) return;
//        value = v;
        editText.removeTextChangedListener(this);
        editText.setText( v );
        editText.addTextChangedListener(this);
    }

    // GET VALUE

    public String getValue(boolean nullable){
        Editable ed = editText.getText();
        if(ed != null){
            String str = ed.toString().trim();
            if( !str.isEmpty() ){
                return str;
            }
        }
        return nullable?null:"";
    }

    @Override
    public BaseInputStatus inputValidate() {
        BaseInputStatus bis = new BaseInputStatus();

        String str = getValue(false);

        if(constraintMandatory){
            if( str.isEmpty() ){
                bis.valid = false;
                bis.invalidTextRes = R.string.clutility_validator_mandatory;
                return bis;
            }
        }
        if(constraintMin != 0 && !str.isEmpty()){
            if( str.length() < constraintMin){
                bis.valid = false;
                bis.invalidTextRes = R.string.clutility_validator_minimum;
                bis.dataNum = constraintMin;
            }
        }

        if( type == TYPE_EMAIL && ! str.matches("\\S+@\\S+") ){
            bis.valid = false;
            bis.invalidDescription = "Format email salah";
        }

        return bis;
    }

    @Override
    protected boolean isShow() {
        if(inpLay != null) return inpLay.getVisibility() == View.VISIBLE;
        else return editText.getVisibility() == View.VISIBLE;
    }

    public void setVisible(Boolean b){
        if(b == null) return;
        int vis = b ? View.VISIBLE: View.GONE;
        if(inpLay != null) inpLay.setVisibility(vis);
        if(editText != null) editText.setVisibility(vis);
        setVisibleInfoWarn(b);
    }

    // ---------- impl

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString().trim();
        switch (type){
            case TYPE_KITAS:
                if(text.startsWith("0")) setValueUnchange(text.substring(1));
                break;
        }
        if(cb != null) cb.onSuccess(InpTextX.this);
    }
}
