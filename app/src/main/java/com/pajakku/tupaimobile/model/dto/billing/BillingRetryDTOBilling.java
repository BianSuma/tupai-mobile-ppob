package com.pajakku.tupaimobile.model.dto.billing;

import java.io.Serializable;

/**
 * Created by dul on 08/01/19.
 */
public class BillingRetryDTOBilling implements Serializable{
    public Long id;
    public Long sspId;
    public String expiredDate;
    public String idBilling;

    // ----------- NOT NULL

    public String idBillingNotNull(){
        if(idBilling == null) return "";
        return idBilling;
    }
}
