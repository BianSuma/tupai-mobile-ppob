package com.pajakku.tupaimobile.model.dto;

import androidx.room.Ignore;

import com.pajakku.tupaimobile.model.Kjs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 24/01/19.
 */

public class ModelMultiSelect implements Serializable {
    @Ignore
    public boolean isCheck = false;

    public static List<Kjs> toKjs(List<ModelMultiSelect> l){
        List<Kjs> ll = new ArrayList<>();
        for(ModelMultiSelect d : l) ll.add((Kjs)d);
        return ll;
    }
}
