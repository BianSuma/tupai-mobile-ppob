package com.pajakku.tupaimobile.model.holder;

import com.google.android.material.transition.Hold;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.dto.ereg.WilayahDTO;

import java.util.ArrayList;
import java.util.List;

public class HolderCommon2Val extends ModelMultiSelect {
    public String bold;
    public String mini;
    public Object object;

    // ---------------

    public HolderCommon2Val(){}

    public HolderCommon2Val(String mini, String bold){
        this.mini = mini;
        this.bold = bold;
    }

    public static HolderCommon2Val getInstance(String b, String m){
        return new HolderCommon2Val(m, b);
    }

    // ------------- NOT NULL

    public String boldNotNull(){
        if(bold == null) return "";
        return bold;
    }

    public String miniNotNull(){
        if(mini == null) return "";
        return mini;
    }

    // --------------- CONVERT

    public PickedDTO toPickedDTO(int style){
        PickedDTO d = new PickedDTO();
        d.name = boldNotNull() + " " + miniNotNull();
        d.object = this;
        return d;
    }

    public static List<PickedDTO> toPickedDTO(List<HolderCommon2Val> l, int style){
        List<PickedDTO> ll = new ArrayList<>();
        for(HolderCommon2Val i : l) ll.add( i.toPickedDTO(style) );
        return ll;
    }
}
