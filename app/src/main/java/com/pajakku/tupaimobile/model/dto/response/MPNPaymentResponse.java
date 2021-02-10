package com.pajakku.tupaimobile.model.dto.response;

import java.io.Serializable;

/**
 * Created by dul on 25/03/19.
 */

public class MPNPaymentResponse implements Serializable {

    public static final String BIL_INQUIRED = "Inquired";
    public static final String   BIL_WAITING_PAYMENT = "Waiting Payment";
    public static final String    BIL_WAITING_REFUND = "Waiting Refund";
    public static final String BIL_REFUNDED = "Refunded";
        public static final String BIL_PAID = "Paid";
    public static final String        BIL_EXPIRED = "Expired";
    public static final String          BIL_CANCEL = "Cancel";

    public long id;
    public String transRefId;
    public String ntpn;
    public String ntb;
    public String transactionDateTime;
    public String mcashProvider;
    public String amount;
    public String status;

    // ------------- NOT NULL

    public String ntpnNotNull(){
        if(ntpn == null) return "";
        return ntpn;
    }

    public String ntbNotNull(){
        if(ntb == null) return "";
        return ntb;
    }

    public String amountNotNull(){
        if(amount == null) return "";
        return amount;
    }

    public String statusNotNull(){
        if(status == null) return "";
        return status;
    }

    public String mcashProviderNotNull(){
        if(mcashProvider == null) return "";
        return mcashProvider;
    }
}
