package com.pajakku.tupaimobile.model.dto;

import android.content.Context;

import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.request.RequestSsp;
import com.pajakku.tupaimobile.util.Utility;

import java.io.Serializable;

/**
 * Created by dul on 25/01/19.
 */

public class WrappedSsp implements Serializable{
    // for data
    public Long id;
    public Taxtype taxType;
    public Kjs taxSlipType;
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
    public boolean generateIdBilling;
    public long wpId;

//    public String taxTypeCode;
//    public String taxSlipTypeCode;
//    month1: String,
//    month2: String,
//    code: String,
//    npwp: String,
//    name: String,
//    address: String,
//    city: String,
//    nop: Option[String] = None,
//    year: String,
//    noSk: Option[String] = None,
//    amount: Long,
//    npwpPenyetor: Option[String] = None,
//    resultId: Option[Long] = None

    // for category
    public int wpType;
    // for flag save
    public boolean isSave;

    public String fetchTaxPeriod(Context context){
        if(month1 == null || month2 == null) return "";
        String m = context.getString(Utility.toMonthShort(month1));
        if(!month1.equals(month2)){
            m = m + " - " + context.getString(Utility.toMonthShort(month2));
        }
        return m + " " + year;
    }
    public Taxtype fetchDefaultTaxType(){
        if(taxType != null) return taxType;
        Taxtype tt = new Taxtype();
        tt.code = "";
        tt.name = "";
        return tt;
    }
    public Kjs fetchDefaultTaxSlipType(){
        if(taxSlipType != null) return taxSlipType;
        Kjs kjs = new Kjs();
        kjs.code = "";
        kjs.name = "";
        return kjs;
    }
    public String fetchMonth1NotNull(){
        if(month1 != null) return month1;
        return "";
    }
    public String fetchMonth2NotNull(){
        if(month2 != null) return month2;
        return "";
    }
    public String fetchDefaultName(){
        if(name != null) return name;
        return "";
    }
    public String npwpNotNull(){
        if(npwp != null) return npwp;
        return "";
    }
    public String fetchDefaultAddress(){
        if(address != null) return address;
        return "";
    }
    public String yearNotNull(){
        if(year != null) return year;
        return "";
    }
    public String noSkNotNull(){
        if(noSk != null) return noSk;
        return "";
    }
    public String nopNotNull(){
        if(nop != null) return nop;
        return "";
    }
    public String npwpPenyetorNotNull(){
        if(npwpPenyetor != null) return npwpPenyetor;
        return "";
    }

    public RequestSsp toRequestSsp(){
        RequestSsp requestSsp = new RequestSsp();
        requestSsp.wpId = wpId;
        requestSsp.taxTypeCode = fetchDefaultTaxType().code;
        requestSsp.kjpName = fetchDefaultTaxType().name;
        requestSsp.taxSlipTypeCode = fetchDefaultTaxSlipType().code;
        requestSsp.kjsName = fetchDefaultTaxSlipType().name;
        requestSsp.month1 = month1;
        requestSsp.month2 = month2;
        requestSsp.year = year;
        requestSsp.npwp = npwp;
        requestSsp.npwpPenyetor = npwpPenyetor;
        requestSsp.name = name;
        requestSsp.address = address;
        requestSsp.city = city;
        requestSsp.nop = nop;
        requestSsp.noSk = noSk;
        requestSsp.amount = amount;

        return requestSsp;
    }
}
