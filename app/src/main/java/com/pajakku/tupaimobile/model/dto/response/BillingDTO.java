package com.pajakku.tupaimobile.model.dto.response;

import com.pajakku.tupaimobile.util.DateFunc;

import java.io.Serializable;

/**
 * Created by dul on 25/03/19.
 */

public class BillingDTO implements Serializable {
    public long id;
    public String idBilling;
    public String responseCode;
    public String expiredDate;
    public String status;

    // ------------ NOT NULL

    public String idBillingNotNull(){
        if(idBilling == null) return "";
        return idBilling;
    }

    public String expiredDateNotNull(){
        if(expiredDate == null) return "";
        return expiredDate;
    }

    public String statusNotNull(){
        if(status == null) return "";
        return status;
    }

    // -----------

    // true = expired, false still valid
    public boolean isExpired(){
        long exp = DateFunc.dateToLongNotNull(expiredDate);
        return System.currentTimeMillis() >= exp;
    }
}
