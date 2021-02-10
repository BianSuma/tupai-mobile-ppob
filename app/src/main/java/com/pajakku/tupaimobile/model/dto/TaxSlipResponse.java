package com.pajakku.tupaimobile.model.dto;

import java.io.Serializable;

/**
 * Created by dul on 08/01/19.
 */

@Deprecated  // mungkin dihapus, diganti dg BillingDTO
public class TaxSlipResponse implements Serializable{
    public static String RESPONSECODE_OK = "00";

    public long id;
    public String idBilling;
    public String expiredDate;
    public String idbillingPajakku;
    public String responseCode;
    public String responseDescription;

    // ------------- NOT NULL

    public String idBillingNotNull(){
        if(idBilling == null) return "";
        return idBilling;
    }

}
