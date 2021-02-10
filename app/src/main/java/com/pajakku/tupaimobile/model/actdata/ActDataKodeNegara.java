package com.pajakku.tupaimobile.model.actdata;


import java.io.Serializable;

public class ActDataKodeNegara implements Serializable {
    public String findKey;

    public ActDataKodeNegara(){

    }

    // ------------ NOT NULL

    public String findKeyNotNull(){
        if(findKey == null) return "";
        return findKey;
    }
}
