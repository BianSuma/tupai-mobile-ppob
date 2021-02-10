package com.pajakku.tupaimobile.model.dto.mpnpajakku;

import com.pajakku.tupaimobile.model.dto.IdOid;

import java.io.Serializable;

public class ReqMpnRefund implements Serializable {
    public IdOid paymentId;
    public String bankName;
    public String bankCode;
    public String accountNumber;
    public String accountHolderName;
    public String amount;
    public String description;

    public ReqMpnRefund(){
        paymentId = new IdOid();
    }
}
