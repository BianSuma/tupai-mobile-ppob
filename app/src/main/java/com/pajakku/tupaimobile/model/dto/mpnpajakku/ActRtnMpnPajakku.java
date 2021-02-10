package com.pajakku.tupaimobile.model.dto.mpnpajakku;

import java.io.Serializable;

public class ActRtnMpnPajakku implements Serializable {
    public Long sspId;
    public String institutionCode;
    public String vaNumber;

    // ----------

    public void sspIdSet(String str){
        if(str == null) return;
        sspId = Long.parseLong(str);
    }
}
