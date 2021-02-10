package com.pajakku.tupaimobile.component;

import android.app.Activity;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.BaseInputStatus;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.ArrayList;
import java.util.List;

public class AppRadio extends BaseInput {
//    private View head;
    private RadioGroup radioGroup;
    private List<AppCompatRadioButton> comps;
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener;

    public AppRadio(Activity ctx, View rv, int id, final CommonCallback<BaseInput> cb){
        super(ctx, rv);
        onCheckedChangeListener  = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                cb.onSuccess(AppRadio.this);
            }
        };
        setRadioGroup(id);

    }

    private void setRadioGroup(int id){
        if(rootView != null) radioGroup = rootView.findViewById(id);
        else radioGroup = ((Activity)getBaseContext()).findViewById(id);
        radioGroup.setOnCheckedChangeListener( onCheckedChangeListener );

        comps = new ArrayList<>();

        int cnt = radioGroup.getChildCount();
        for(int i=0; i<cnt; i++){
            add( (AppCompatRadioButton) radioGroup.getChildAt(i) );
        }
    }

    private void add(AppCompatRadioButton view){
        if(view == null) return;
        comps.add( view );
    }

    // --------------- SET VALUE

    public void setValueUnchange(Boolean b){
        if(b == null) return;
        radioGroup.setOnCheckedChangeListener(null);
        String cmp = b ? getString(R.string.global_yes) : getString(R.string.global_no) ;
        String str;
        for(AppCompatRadioButton i : comps){
            str = (String)i.getTag();
            str = str != null ? str : "";
            if(str.equalsIgnoreCase(cmp)){
                i.setChecked(true);
                break;
            }
        }
        radioGroup.setOnCheckedChangeListener( onCheckedChangeListener );
    }

    public void setValueUnchange(Integer idStr){
        if(idStr == null) return;
        radioGroup.setOnCheckedChangeListener(null);
        String cmp = getString(idStr);
        String str;
        for(AppCompatRadioButton i : comps){
            str = (String)i.getTag();
            str = str != null ? str : "";
            if(str.equalsIgnoreCase(cmp)){
                i.setChecked(true);
                break;
            }
        }
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setValueUnchange(String r){
        if(r == null) return;
        radioGroup.setOnCheckedChangeListener(null);
        String str;
        for(AppCompatRadioButton i : comps){
            str = (String)i.getTag();
            str = str != null ? str : "";
            if(str.equalsIgnoreCase(r)){
                i.setChecked(true);
                break;
            }
        }
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    // --------------- GET VALUE

    public Boolean getValue(int strId, boolean nullable){
        String cmp = getString(strId);
        String str;
        for(AppCompatRadioButton i : comps){
            str = (String)i.getTag();
            str = (str != null) ? str : "";
            if( !str.equalsIgnoreCase(cmp) ) continue;
            return i.isChecked();
        }
        return (nullable?null:false);
    }

    public String getValueStr(boolean nullable){
        for(AppCompatRadioButton i : comps){
            if(i.isChecked()) return (String)i.getTag();
        }
        return (nullable?null:"");
    }

    public String getValueStr(boolean nullable, int hideVal){
        if(!isShow()){
            switch (hideVal){
                case 0: return null;
                case 1: return "";
            }
        }

        return getValueStr(nullable);
    }

    public Boolean getValueBool(boolean nullable){
        for(AppCompatRadioButton i : comps){
            if(i.isChecked()) {
                String str = (String)i.getTag();
                if(str != null){
                    return str.equalsIgnoreCase( getString(R.string.global_yes));
                }
            }
        }
        return (nullable?null:false);
    }

    @Override
    public BaseInputStatus inputValidate() {
        BaseInputStatus bis = new BaseInputStatus();

        if(constraintMandatory){
            if( getValueStr(true) == null ){
                bis.valid = false;
                bis.invalidTextRes = R.string.clutility_validator_mandatory;
            }
        }

        return bis;
    }

    @Override
    protected boolean isShow() {
        return radioGroup.getVisibility() == View.VISIBLE;
    }

    public void setVisible(Boolean b){
        if(b == null) return;
        int vis = b ? View.VISIBLE: View.GONE;
        if(radioGroup != null) radioGroup.setVisibility(vis);
        setVisibleInfoWarn(b);
    }
}
