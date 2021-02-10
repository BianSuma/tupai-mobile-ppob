package com.pajakku.tupaimobile.model.spinitem;

public class SpinItem2Str implements SpinItem {
    public String str0;
    public String str1;

    @Override
    public String popupItemLabel() {
        return str1+", "+str0;
    }

    @Override
    public String fieldLabel() {
        return popupItemLabel();
    }
}
