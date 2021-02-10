package com.pajakku.tupaimobile.model.dto.billing;

import java.io.Serializable;

/**
 * Created by dul on 08/01/19.
 */
public class BillingRetryDTO implements Serializable{

    public Long id;
    public Long userId;
    public Long amount;
    public BillingRetryDTOBilling billing;

    public BillingRetryDTO(){
        billing = new BillingRetryDTOBilling();
    }
}
