package com.pajakku.tupaimobile.model.dto.mpnpajakku;

import com.pajakku.tupaimobile.model.dto.DolarDate;
import com.pajakku.tupaimobile.model.dto.IdOid;

import java.io.Serializable;

public class RespMpnBillingStatus implements Serializable {
    public IdOid _id;
    public String transactionId;
    public String paymentCode;  // ID Billing
    public String currency;
    public String partnerCode;
    public String branchCode;
    public String amount;
    public String status;
    public String orderNo;
    public String ntb;  // nullable
    public String ntpn; // nullable
    public DolarDate createdAt;
    public DolarDate updatedAt;
    public RespMpnBillingStatusResponse response;

    public RespMpnBillingStatus(){
        response = new RespMpnBillingStatusResponse();
    }
}
