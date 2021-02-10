package com.pajakku.tupaimobile.model.spinitem;

public class SpinItem2StrCustom implements SpinItem {
    public static final int TYPE_FIRSTCODE = 1;

    public String idStr;
    public String str0;
    public int type;

    @Override
    public String popupItemLabel() {
        switch (type){
            case TYPE_FIRSTCODE: return idStr + " " + str0;
        }
        return str0;
    }

    @Override
    public String fieldLabel() {
        return popupItemLabel();
    }
}
