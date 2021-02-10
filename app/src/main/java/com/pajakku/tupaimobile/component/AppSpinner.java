package com.pajakku.tupaimobile.component;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.BaseInputStatus;
import com.pajakku.tupaimobile.model.spinitem.SpinItem;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.List;

public class AppSpinner extends BaseInput {
    public TextInputLayout inpLay;
    public TextInputEditText editText;
//    private Long valLong;
//    private String valStr;
    private SpinItem value;
    private CommonCallback<BaseInput> cbPre;
    private CommonCallback<BaseInput> cb;
    List<SpinItem> items;

    public AppSpinner(Activity ctx, View rv, int id, List<SpinItem> items, CommonCallback<BaseInput> cbPre, CommonCallback<BaseInput> cb){
        super(ctx, rv);
        this.items = items;
        this.cbPre = cbPre;
        this.cb = cb;
        setInpLay(id);
    }

    // idTextInpLay bisa berupa ID dari TextInputLayout / TextInputEditText
    private void setInpLay(int idTextInpLay){
        inpLay = (TextInputLayout)findViewById(idTextInpLay);
        if(inpLay != null) editText = (TextInputEditText) inpLay.getEditText();
        else editText = (TextInputEditText)findViewById(idTextInpLay);

//        editText.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_dropdown,0);
        editText.setMovementMethod(null);
        editText.setKeyListener(null);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( cbPre != null ) cbPre.onSuccess(AppSpinner.this);
                showChoice();
            }
        });
    }

    public void setHint(String h){
        if(h == null) return;
        inpLay.setHint(h);
    }

    // SET VALUE

    public void setValueUnchange(SpinItem si){
        if( si == null) return;
        value = si;
        editText.setText( value.fieldLabel() );
    }

    // GET VALUE

    public SpinItem getValue(){
        return value;
    }

//    public String getValueStr(boolean nullable){
//        if(valStr == null) return (nullable?null:"");
//        return valStr;
//    }

    // --------------

    public void showChoice(){
        PopupMenu popupMenu = new PopupMenu(getBaseContext(), editText);

        int i = 0;
        for(SpinItem si : items) {
            popupMenu.getMenu().add(1, i, i++, si.popupItemLabel());
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setValueUnchange( items.get(item.getItemId()) );
                if(cb != null) cb.onSuccess( AppSpinner.this );
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public BaseInputStatus inputValidate() {
        BaseInputStatus bis = new BaseInputStatus();

        if(constraintMandatory){
            if( value == null){
                bis.valid = false;
                bis.invalidTextRes = R.string.clutility_validator_mandatory;
            }
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
}
