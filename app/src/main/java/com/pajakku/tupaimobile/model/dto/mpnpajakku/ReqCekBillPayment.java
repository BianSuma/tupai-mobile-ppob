package com.pajakku.tupaimobile.model.dto.mpnpajakku;

import java.io.Serializable;

public class ReqCekBillPayment implements Serializable {
    public Long sspId;
    public String institutionCode;
    public String vaNumber;
    public RespMpnBillingStatus payStatus;
}
