package com.pajakku.tupaimobile.model.dto.response;

import java.io.Serializable;

public class PayInfo implements Serializable {
    public String partnerCode;
    public String branchCode;
    public String traxId;
    public String orderNo;
    public String mataUang;
    public Long paymentCreatedAt;
    public Long paymentUpdatedAt;
    public Long updatedAt;

    // --------- NOT NULL

    public String branchCodeNotNull(){
        if(branchCode == null) return "";
        return branchCode;
    }

    public String mataUangNotNull(){
        if(mataUang == null) return "";
        return mataUang;
    }
}
