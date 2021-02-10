package com.pajakku.tupaimobile.model.dto;

import java.io.Serializable;

/**
 * Created by dul on 01/02/19.
 */

public class TaxReceipt implements Serializable {
    public long id;
    public String ntb;
    public String ntpn;
    public String stan;
    public String paymentDateTime;
    public String bookDate;
    public String providerBranchCode;
}
