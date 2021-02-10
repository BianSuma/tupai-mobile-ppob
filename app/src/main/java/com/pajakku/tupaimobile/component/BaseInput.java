package com.pajakku.tupaimobile.component;

import android.app.Activity;
import android.content.ContextWrapper;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.BaseInputStatus;


public abstract class BaseInput extends ContextWrapper {

    protected View rootView;
    protected View lblHead;
    protected View lblInfo;
    protected AppCompatTextView lblWarn;

    public boolean constraintMandatory;

    public BaseInput(Activity ctx, View rv){
        super(ctx);
        rootView = rv;
    }

    protected View findViewById(int id){
        if(rootView != null) return rootView.findViewById(id);
        else return ((Activity)getBaseContext()).findViewById(id);
    }

    public void setHeadView(int id){
        if(rootView != null) lblHead = rootView.findViewById(id);
        else lblHead = ((Activity)getBaseContext()).findViewById(id);
    }

//    public void setInfo(View v, int id){
//        lblInfo = v.findViewById(id);
//    }
//
//    public void setInfo(Activity act, int id){
//        lblInfo = act.findViewById(id);
//    }

    public void setInfo(int id){
        if(rootView != null) lblInfo = rootView.findViewById(id);
        else lblInfo = ((Activity)getBaseContext()).findViewById(id);
    }

//    public void setWarn(View v, int id){
//        lblWarn = v.findViewById(id);
//    }
//
//    public void setWarn(Activity act, int id){
//        lblWarn = act.findViewById(id);
//    }

    public void setWarn(int id){
        if(rootView != null) lblWarn = rootView.findViewById(id);
        else lblWarn = ((Activity)getBaseContext()).findViewById(id);
    }

    public boolean checkInputWarn(){
        if(!isShow()){
            if(lblWarn != null) lblWarn.setVisibility(View.GONE);
            return true;
        }

        BaseInputStatus bis = inputValidate();
        if(lblWarn == null) return bis.valid;

        if( bis.valid ){
            lblWarn.setVisibility(View.GONE);
        }else{
            if(bis.invalidDescription != null){
                lblWarn.setText(bis.invalidDescription);
            }else {
                switch (bis.invalidTextRes) {
                    case 0:
                        lblWarn.setText("Data masukan keliru");
                        break;
                case R.string.clutility_validator_minimum:
//                case R.string.clutility_validator_maximum:
//                case R.string.clutility_validator_nik:
                    lblWarn.setText( getString(bis.invalidTextRes, bis.dataNum) );
                    break;
                    default:
                        lblWarn.setText(bis.invalidTextRes);
                        break;
                }
            }
            lblWarn.setVisibility(View.VISIBLE);

        }

        return bis.valid;
    }

    protected void setVisibleInfoWarn(Boolean b){
        if(b == null) return;
        int vis = (b? View.VISIBLE: View.GONE);
        if(lblHead != null) lblHead.setVisibility( vis );
        if(lblInfo != null) lblInfo.setVisibility( vis );
//        if(lblWarn != null) lblWarn.setVisibility( vis );
    }

    protected abstract BaseInputStatus inputValidate();
    protected abstract boolean isShow();

}
