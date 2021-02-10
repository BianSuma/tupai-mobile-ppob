package com.pajakku.tupaimobile.util;

/**
 * Created by dul on 31/12/18.
 */

public final class AppConf {
    public static final int URLTYPE_PROD = 1;
    public static final int URLTYPE_DEV = 2;
    public static final int URLTYPE_DUMMY = 3;

    public static final String APP_BUILD_DATE = "2020 1221 2024";

    public static int URL_TYPE = 2;  // 1 prod, 2 dev, 3 dummy
    public static boolean NO_DEV_MARK = false;
    public static boolean VALIDATE = true;
    public static boolean NO_LONG_CLICK = false;

    public static String clientId(){
        return URL_TYPE == URLTYPE_PROD ? AppConstant.CLIENT_ID_PROD : AppConstant.CLIENT_ID ;
    }

    public static String clientSecret(){
        return URL_TYPE == URLTYPE_PROD ? AppConstant.CLIENT_SECRET_PROD : AppConstant.CLIENT_SECRET ;
    }

    public static String urlSso(){
        return URL_TYPE == URLTYPE_PROD ? AppConstant.URL_SSO_BDG_PROD : AppConstant.URL_SSO_BDG ;
    }

    public static String urlPrivate(){
        return URL_TYPE == URLTYPE_PROD ? AppConstant.URL_PRIVATE_PROD : AppConstant.URL_PRIVATE ;
    }

    public static String urltypeStr(){
//        return URL_PROD ? "PROD" : "DEV";
        switch (URL_TYPE){
            case URLTYPE_PROD: return "PROD";
            case URLTYPE_DEV: return "DEV";
            case URLTYPE_DUMMY: return "DUMMY";
        }
        return "unknown "+URL_TYPE;
    }

    private AppConf(){}

}
