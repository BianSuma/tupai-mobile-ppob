package com.pajakku.tupaimobile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.component.AppRadio;
import com.pajakku.tupaimobile.component.AppSpinner;
import com.pajakku.tupaimobile.component.BaseInput;
import com.pajakku.tupaimobile.component.InpDate;
import com.pajakku.tupaimobile.component.InpFile;
import com.pajakku.tupaimobile.component.InpPicker;
import com.pajakku.tupaimobile.component.InpTextX;
import com.pajakku.tupaimobile.ifc.CommonCallback2;
import com.pajakku.tupaimobile.model.dto.FileBrowserItem;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.spinitem.SpinItem;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {
    protected Activity activity;
    private View rootView;
    private List<BaseInput> inputs;
    private AppCompatButton btnBot;

    public BaseFragment(){
        inputs = new ArrayList<>();
    }

    protected View onCreateView(int viewId, @NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return rootView = inflater.inflate(viewId, container, false);
    }

    @Override
    public void onAttach(@NonNull Context ctx) {
        super.onAttach(ctx);

        activity = (Activity) ctx;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        inputs.clear();
    }

    public void onActResultFrag(Activity act, int reqCode, Serializable seri){

        Utility.proceedActivityResult(inputs, reqCode, seri, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                checkInput(data);
            }
        });
        onActResultFragChild(act, reqCode, seri );
    }

    protected void onActResultFragChild(Activity act, int reqCode, Serializable data){
        Utility.logWarn("2020 0905 1934 boleh di-override");
    }

    // ----------


    protected InpTextX setCompInpTextX(int id){
        InpTextX comp = new InpTextX(activity, rootView, id, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                checkInput(data);
            }
        });
        inputs.add(comp);
        return comp;
    }

    protected InpFile setCompInpFile(int id, int actRes, CommonCallback<BaseInput> cbPre, CommonCallback<FileBrowserItem> onPick){
        InpFile comp = new InpFile(activity, rootView, id, actRes, cbPre, onPick);
        inputs.add(comp);
        return comp;
    }

    protected AppRadio setCompAppRadio(int id){
        AppRadio rd = new AppRadio(activity, rootView, id, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                checkInput(data);
            }
        });
        inputs.add(rd);
        return rd;
    }

    protected InpDate setCompInpDate(int id, CommonCallback<BaseInput> cbPre){
        InpDate comp = new InpDate(activity, rootView, id, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                checkInput(data);
            }
        }, cbPre);
        inputs.add(comp);
        return comp;
    }

    protected InpPicker setCompInpPicker(int id, int actRes, CommonCallback<BaseInput> cbPre, CommonCallback<PickedDTO> onPick){
        InpPicker comp = new InpPicker(activity, rootView, id, actRes, cbPre, onPick);
        inputs.add(comp);
        return comp;
    }

    protected AppSpinner setCompAppSpinner(int id, List<SpinItem> items, CommonCallback<BaseInput> cbPre){
        AppSpinner bi = new AppSpinner(activity, rootView, id, items, cbPre, new CommonCallback<BaseInput>() {
            @Override
            public void onSuccess(BaseInput data) {
                checkInput(data);
            }
        });
        inputs.add(bi);
        return bi;
    }

    protected void setBotBtn(int id){
        btnBot = rootView.findViewById(id);
        btnBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    // -----------------

    public boolean checkInput(BaseInput baseInput){

        return Utility.checkInput((BaseActivity) activity, this, baseInput, inputs, btnBot);

    }

    public void validateSave(){
        if( ! checkInput(null) ) {
            Utility.toast(activity, "Data masukan tidak lengkap");
            return;
        }
        save();
    }

    public void save(){
        Utility.logErr("2020 0903 1347 must override");
    }

    public boolean onSubmitValidate(BaseActivity act, BaseInput bi){
        Utility.logWarn("2020 0905 2130 onSubmitValidate boleh di-override");
        return true;
    }

    public void onChangeInputComponent(BaseActivity act, BaseInput bi, boolean isValid){
        Utility.logWarn("2020 0905 2121 onChangeInputComponen boleh di-override");
    }
}
