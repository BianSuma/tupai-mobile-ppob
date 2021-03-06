package com.pajakku.tupaimobile.component;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.ifc.CommonCallback2;
import com.pajakku.tupaimobile.model.dto.BaseInputStatus;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

public class InpPicker extends BaseInput {
    public int actRes;
    private TextInputLayout inpLay;
    public TextInputEditText editText;  // public utk set long click
    private PickedDTO value;
//    private AppCompatImageView imgFoto;
    public CommonCallback<PickedDTO> onPick;

    public InpPicker(Activity ctx, View rv, int id, int actRes, CommonCallback<BaseInput> cbPre, CommonCallback<PickedDTO> onPick){
        super(ctx, rv);
        this.actRes = actRes;
        this.onPick = onPick;

        setInpLay(id, cbPre);
    }

    // idTextInpLay bisa berupa ID dari TextInputLayout / TextInputEditText
    private void setInpLay(int idTextInpLay, final CommonCallback<BaseInput> cbPre){
        final InpPicker iam = this;
        inpLay = (TextInputLayout)findViewById(idTextInpLay);
        if(inpLay != null) editText = (TextInputEditText) inpLay.getEditText();
        else editText = (TextInputEditText)findViewById(idTextInpLay);

        editText.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_dropdown,0);
        editText.setMovementMethod(null);
        editText.setKeyListener(null);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbPre != null) cbPre.onSuccess(iam);
            }
        });
    }

//    public void setFotoView(int id){
//        imgFoto = (AppCompatImageView) findViewById(id);
//    }

    // SET VALUE

//    public void setValueUnchange(String path){
//        if(path == null) return;
//        FileBrowserItem d = new FileBrowserItem();
//        d.path = path;
//        d.name = Utility.getPathFileName(path);
//        setValueUnchange(d);
//    }

    public void setValueUnchange(PickedDTO v){
        if(v == null) return;
        value = v;
        editText.setText( value.name );

//        if(imgFoto != null){
//            Bitmap bm = BitmapFactory.decodeFile(value.path);
//            if(bm != null) {
//                imgFoto.setImageBitmap(bm);
//            }else{   // bm bisa null krn gagal parse data
//                Utility.log("2020 0904 1833 bm null");
//                Utility.toast(getBaseContext(), "2020 0904 1833 bm null");
//            }
//        }
    }

    // GET VALUE

    public PickedDTO getValue(){
        return value;
    }

//    public String getValuePath(boolean nullable){
//        if(value != null){
//            if( value.path != null ) return value.path;
//        }
//        return (nullable?null:"");
//    }

//    public String getValueShow(boolean nullable){
//        if(value.isEmpty() ) return (nullable?null:"");
//        return editText.getText().toString();
//    }

    @Override
    public BaseInputStatus inputValidate() {
        BaseInputStatus bis = new BaseInputStatus();

        if(constraintMandatory){
            if( value == null ){
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
