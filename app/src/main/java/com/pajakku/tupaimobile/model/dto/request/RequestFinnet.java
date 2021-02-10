package com.pajakku.tupaimobile.model.dto.request;

/**
 * Created by dul on 28/01/19.
 */

public class RequestFinnet {
    public static String REQUESTTYPE_SENDPROBLEM = "01";
    public static String REQUESTTYPE_GETBALANCE = "02";
    public static String REQUESTTYPE_GETTOPUPLINK = "03";
    public static String REQUESTTYPE_EMON_SENDOTP = "04";
    public static String REQUESTTYPE_EMON_REGISTERPHONE = "05";
    public static String REQUESTTYPE_PAYSSP = "06";
    public static String REQUESTTYPE_GETTRANSACTIONLOG = "07";

    public Long dataId;
    public String requestType;
    public String phoneNumber;
    public String pin;
    public String signature;
    public String reqDtime;
    public String custName;
    public String otp;
    public String custStatusCode;
    public String transNumber;
    public String transAmount;
    public String transType;
    public String transDesc;
    public String startDate;
    public String endDate;
    public String tokenID;
    public String appCode;


}
