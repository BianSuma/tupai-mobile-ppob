package com.pajakku.tupaimobile.model.dto.request;

import android.content.Context;

import com.pajakku.tupaimobile.util.Utility;

import java.io.Serializable;

/**
 * Created by dul on 25/01/19.
 */

public class RequestSsp implements Serializable{

    public Long id;
    public String taxTypeCode;
    public String kjpName;
    public String taxSlipTypeCode;
    public String kjsName;
    public String month1;
    public String month2;
    public String code;
    public String npwp;
    public String name;
    public String address;
    public String city;
    public String year;
    public String noSk;
    public String nop;
    public long amount;
    public String npwpPenyetor;
    public String description;
    public long wpId;
    public long resultId;

    public String fetchTaxPeriod(Context context){
        if(month1 == null || month2 == null) return "";
        String m = context.getString(Utility.toMonthShort(month1));
        if(!month1.equals(month2)){
            m = m + " - " + context.getString(Utility.toMonthShort(month2));
        }
        return m + " " + year;
    }


}
