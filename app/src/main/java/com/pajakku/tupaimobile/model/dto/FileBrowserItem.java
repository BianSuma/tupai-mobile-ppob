package com.pajakku.tupaimobile.model.dto;

import com.pajakku.tupaimobile.model.holder.HolderCommon1Val;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileBrowserItem implements Serializable {
    public String name;
    public String path;
    public boolean isDir;

    // ------------ CONVERT

    public HolderCommon1Val toHolderCommon1Val(){
        HolderCommon1Val d = new HolderCommon1Val();
        d.object = this;
        d.str = (isDir?"* ":"   ") + name;
        return d;
    }

    public static List<HolderCommon1Val> toHolderCommon1Val(List<FileBrowserItem> l){
        List<HolderCommon1Val> ll = new ArrayList<>();
        for(FileBrowserItem i : l) ll.add(i.toHolderCommon1Val());
        return ll;
    }
}
