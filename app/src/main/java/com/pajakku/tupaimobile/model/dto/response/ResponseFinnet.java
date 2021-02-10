package com.pajakku.tupaimobile.model.dto.response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dul on 28/01/19.
 */

public class ResponseFinnet {

    public static final String STATUSCODE_EMON_OK = "000";
    public static final String STATUSCODE_EMON_201 = "201";
    public static final String STATUSCODE_TUPAI_SEND_OTP = "T01";  // tanda telah dikirim OTP via SMS
    public static final String STATUSCODE_TUPAI_EMON_REGISTERED = "T02";  // mobile cash akun terbuat, tdk perlu verifikasi OTP

    public static final String CUSTSTATUSCODE_BELUM_REGISTER = "001";
    public static final String CUSTSTATUSCODE_BELUM_PAIRING = "002";
    public static final String CUSTSTATUSCODE_SUDAH_PAIRINGREGISTER = "003";

    public String statusCode;
    public String statusDesc;
    public String custStatusCode;
    public String custStatusDesc;
    public String tokenID;
    public String tokenExpiry;
    public double processingTime;
    public String custName;
    public String custBalance;
    public String appCode;
    public String detailHist;
    public String widgetURL;

    public long fetchExpiry(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = df.parse(tokenExpiry);
            return d.getTime();
        }catch (Exception e){
            return 0;
        }
    }

    public boolean isActivate(){
        if(custStatusCode == null) return false;
        if(custStatusCode.equals(CUSTSTATUSCODE_SUDAH_PAIRINGREGISTER)) return true;
        return false;
    }


}
