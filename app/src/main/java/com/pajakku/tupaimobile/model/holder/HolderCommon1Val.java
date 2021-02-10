package com.pajakku.tupaimobile.model.holder;

import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

public class HolderCommon1Val extends ModelMultiSelect {
    public String str;
    public Object object;

    // ------------- NOT NULL

    public String strNotNull(){
        if(str == null) return "";
        return str;
    }
}
