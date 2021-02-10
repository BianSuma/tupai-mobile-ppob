package com.pajakku.tupaimobile.util;

/**
 * Created by dul on 17/12/18.
 */

public final class AppConstant {

    public final static String LOG_TAG = "logtag";
//    public final static String SHARE_PREF_KEY = "com.pajakku.tupaimobile.prefkey";
    public final static String SHARE_PREF_KEY = ".prefkey";
    public final static String PAJAKKU_LINK = "https://pajakku.com";
    public final static String DEEPLINK_SCHEME_SEPARATOR = "://";

    // how long data to kept in local
    public final static long MS_KEEPDATA_HOUR = 3600000L;  // 3600000 = 1 hour
    public final static long MS_KEEPDATA_20HOUR = 72000000L;  // 72000000 = 20 hours
    public final static long MS_KEEPDATA_1YEAR = 31536000000L;  // 31536000000 = 1 year

//    public final static String MERCHANT_ID = "022003";

    // TODO: @prod
    public final static int LIST_SIZE = 10;

    // TODO: @test
//    public final static int LIST_SIZE = 6;

//    public final static String STREAM_HEADER_PAGE = "x-pagination-page";
//    public final static String STREAM_HEADER_COUNT = "x-pagination-count";
//    public final static String STREAM_HEADER_LIMIT = "x-pagination-limit";

//    public final static int DB_VER = 5;
    public final static int DB_VER = 1;
    public final static String DB_NAME = "tupai-mobile";
    public final static String NOTIF_CHANNEL_ID = "MPK_TPIMBL_NOTIFCHANNEL";

    public final static boolean AUTO_RELOGIN = false;

    // notif id
    public final static int NTF_NORMAL = 1;
    
    // harus diakhiri slash '/'
    public final static String URL_PRIVATE = "https://internal.bdg.pajakku.com/";  // dev
    public final static String URL_PRIVATE_PROD = "https://internal.pajakku.com/";

    public final static String URL_SSO_BDG = "https://sso.bdg.pajakku.com/";  // dev
    public final static String URL_SSO_BDG_PROD = "https://sso.pajakku.com/";

    public final static String CLIENT_ID = "pQRfENTgYmbAklqrnDmW0JicgMpcmZJ9sT93OVQs";  // dev
    public final static String CLIENT_ID_PROD = "10VJLySgXj5vDK6y8cEtke5DvGQIoXFBVdXqv3OH";
    public final static String CLIENT_SECRET = "TcyCsoK7IKuDHVqx5iGc0A7dBd0gHygNF1MtyoGD";  // dev
    public final static String CLIENT_SECRET_PROD = "otq7GIBQtmMP9JqPxytbwzKFiNxZ2uIIGz6x6KMP";

    public final static String TUPAIBACKEND_PREFIXURL = "tupai_android/";
    public final static String EBILLING_PREFIXURL = "ebilling_api_eksternal/";
    public final static String EBILLING_INTERNAL_PREFIXURL = "ebilling_api_internal/";

    // @test
//    public final static String TUPAIBACKEND_PREFIXURL = "tupai_test/";
//    public final static String TUPAIBACKEND_PREFIXURL = "tupai_tethering/";
//    public final static String EBILLING_PREFIXURL = "ebilling_api_test/";

    public final static String TOKEN_TYPE = "tokenType";
    public final static String HEADER_AUTHORIZATION = "Authorization";
    public final static String HEADER_HOST = "Host";
    public final static String HEADER_BEARER_SPACE = "Bearer ";
    public final static String LOGIN_REDIRECT_URI = "redirect_uri ";
    public final static String LOGIN_STATE = "state";
    public final static String LOGIN_CODE = "code";
    public final static String LOGIN_PASSWORD = "password";
    public final static String LOGIN_AUTHORIZATION_CODE = "authorization_code";
    public final static String XSUBSCRIPTION_ID = "X-SUBSCRIPTION-ID";
    public final static String XPAJAKKU_CONSUME_ID = "X-Pajakku-ConsumeID";
    public final static String FINNET_OK_CODE = "000";

    public final static String EREG_KODE_NEGARA_INDO = "ID";

    // http request path
    public final static String REQUESTPATH_TRANSNUMBER = "p01";
    public final static String REQUESTPATH_BILLNUMBER = "p02";
    public final static String REQUESTPATH_OTP = "p03";
    public final static String REQUESTPATH_CUSTSTATUSCODE = "p04";

    // error id
    public final static long EI_INVALID_SESSION = 1;
    public final static long EI_BELUM_PUNYA_MOBILE_CASH_ACCOUNT = 2;

    // account user data key
    public final static String AUD_REFRESH_TOKEN = "1";

    // ACT DATA
    public final static String SP_ACTDATA_DEEPLINKHANDLER = "ad1";
    public final static String SP_ACTDATA_SSP_DETAIL_BELUM_BAYAR = "ad2";
    public static final String SP_ACTDATA_MPNPAJAKKU = "ad3";
    public static final String SP_ACTDATA_REFUND = "ad4";
    public static final String SP_ACTDATA_REGISTRASI_NPWP = "ad5";
    public static final String SP_ACTDATA_FILE_BROWSER = "ad6";
    public static final String SP_ACTDATA_KODE_WILAYAH = "ad7";
    public static final String SP_ACTDATA_CAMERA_KIT = "ad8";
    public static final String SP_ACTDATA_KODE_NEGARA = "ad9";
    public static final String SP_ACTDATA_COMMON_WEB = "ad10";
    public static final String SP_ACTDATA_KLU_LIST = "ad11";

    // intent key
    public final static String ITN_TOPUPWEBIVEW_URL = "01";
    public final static String ITN_TOPUPSENDOTP_CUSTSTATUSCODE = "02";
    public final static String ITN_SSPDETAILPAY_SSPUNPAID  = "03";
    public final static String ITN_PAYEPAYBRIWEB_URL  = "04";
    public final static String ITN_XPAYMENT_MODE  = "05";
    public final static String ITN_XPAYMENT_PRODUCT  = "06";
    public final static String ITN_XPAYMENT_PRICE  = "07";
    public final static String ITN_XPAYMENT_PRICEPAY  = "08";
    public final static String ITN_FRAGSSP_BULK  = "09";
    public final static String ITN_FRAGHOMEPAYTAX_TAXTYPE = "10";
    public final static String ITN_FRAGHOMEPAYTAX_WPTYPE  = "11";
    public final static String ITN_SSPDETAILUNPAID_SSPUNPAID = "12";
    public final static String ITN_SSPDETAILDONE_SSPDONE = "13";
    public final static String ITN_XADDEDITWP_ORGANIZATION  = "14";
    public final static String ITN_XADDEDITWP_ACTIONMODE  = "15";
    public final static String ITN_NOTIFSSP_SSPUNPAID = "16";
    public final static String ITN_NOTIFSSP_NOTIFDATE  = "17";
    public final static String ITN_NOTIFX_NOTIFTYPE = "18";
    public final static String ITN_NOTIFSSP_TITLE  = "19";
    public final static String ITN_NOTIFSSP_BODY  = "20";
    public final static String ITN_TAXTYPELISTHOME_TAXTYPE  = "21";
    public final static String ITN_NOTIFPAY_SSPID  = "22";
    public final static String ITN_NOTIFPAY_REFNUM  = "23";
    public final static String ITN_NOTIFPAY_STATUS  = "24";
    public final static String ITN_SPTDETAIL_SPT  = "25";

    // inten filter
    public final static String ITNFILTER_TUPAINOTIF = "if1";
//    public final static String ITNFILTER_NEW_FCMTOKEN = "if2";
//    public final static String ITNFILTER_SUCCESSPAYSSP = "if3";

    public final static String SP_ACTIVITYRESULT = "ACT_RTN";

    // SharedPreferences key
    public final static String SP_USERNAME = "01";
    public final static String SP_SPLASH = "02";
    public final static String SP_SPTDETAIL_SPT = "03";
    public final static String SP_AUTHTOKENEXPIRE = "04";
    public final static String SP_PASSWORD_AES_KEY = "05";
    public final static String SP_AUTH_TOKEN = "05_1";
    public final static String SP_PAYTAX_REQUESTSSP = "06";
    public final static String SP_ACTIVATEMC_MODEINSERTPHONE = "07";
    public final static String SP_DETAILACTIVIY_SSP = "08";
    public final static String SP_DETAILACTIVIY_SSPDONE = "09";
    public final static String SP_FCM_TOKEN = "10";
    public final static String SP_RECENT_TAXTYPE = "11";
    public final static String SP_RECENT_KJS = "12";
    public final static String SP_SPT_SPTDRAFT = "13";

    public final static String SP_BACKMARK_PAYTAXBEGIN = "15";
    public final static String SP_BACKMARK_MAINLOGIN = "16";

    public final static String SP_CACHEKEY_ME = "20";
    public final static String SP_CACHEKEY_MCACTIVATE = "21";
    public final static String SP_CACHEKEY_SSPUNPAID = "22";
    public final static String SP_CACHEKEY_SSPDONE = "23";
    public final static String SP_CACHEKEY_WPLIST = "24";
    public final static String SP_CACHEKEY_STREAMKJS = "25";
    public final static String SP_CACHEKEY_TAXTYPE = "26";
    public final static String SP_CACHEKEY_EMONBALANCE = "27";
    public final static String SP_CACHEKEY_TRANSLOG = "28";
    public final static String SP_CACHEKEY_FIRST = "29";
    public final static String SP_CACHEKEY_REPORTSPT = "30";

    public final static String SP_CACHEME_ID = "40";
    public final static String SP_CACHEME_FULLNAME = "41";
    public final static String SP_CACHEME_PHONE = "42";
    public final static String SP_CACHEME_EMAIL = "43";
    public final static String SP_CACHEME_COMPANY_ID = "44";
    public final static String SP_CACHEME_COMPANY_NAME = "45";
    public final static String SP_CACHEME_SUBSCRIPTION_ID = "46";

    public final static String SP_CACHEMCTOKEN_TOKEN = "50";
    public final static String SP_CACHEMCTOKEN_EXPIRY = "51";
    public final static String SP_CACHEMCTOKEN_CUSTSTATUSCODE = "52";

    public final static String SP_CACHEEMONBALANCE_CUSTBALANCE = "60";
    public final static String SP_CACHEEMONBALANCE_IS_MC_ACTIVE = "61";

    public final static String SP_CACHESTATUSDATA_CLIENTVERSION = "291";
    public final static String SP_CACHESTATUSDATA_ISOUTDATEVERSION = "292";
    public final static String SP_CACHESTATUSDATA_LASTCLIENTVERSION = "293";
    public final static String SP_CACHESTATUSDATA_API_MPNPAJAKKUWIDGET = "sp291_1";
    public final static String SP_CACHESTATUSDATA_API_MPNPAJAKKUCLIENTID = "sp291_2";
    public final static String SP_CACHESTATUSDATA_API_MPNPAJAKKUHOST = "sp291_3";
    public final static String SP_CACHESTATUSDATA_API_MPNPAJAKKUBASE = "sp291_4";
    public final static String SP_CACHESTATUSDATA_MPNWIDGET_USER = "sp291_5";
    public final static String SP_CACHESTATUSDATA_MPNWIDGET_PASS = "sp291_6";
    public final static String SP_CACHESTATUSDATA_API_EREGHOST = "sp291_7";

    public final static String SP_ONCE_DATENOTIF = "70";

    public final static String SP_AVAL_WORKTYPE = "v1";
    public final static String SP_AVAL_KLU = "v2";
    public final static String SP_AVAL_PASUTRISTATUS = "v3";
    public final static String SP_AVAL_PASUTRINPWP = "v4";

    // quick help
    public final static String SP_QH_MAINHOME = "qh1";
    public final static String SP_QH_KJS = "qh2";
    public final static String SP_QH_PAYSETOR = "qh3";
    public final static String SP_QH_SSPLIST = "qh4";
    public final static String SP_QH_WP = "qh5";


    // activity result request code
    public final static int ACTRES_COMMON = 1;
    public final static int ACTRES_TAXTYPE_AS_LIST = 2;
    public final static int ACTRES_TOP_UP = 3;
    public final static int ACTRES_EPAYBRI_PAY = 4;
    public final static int ACTRES_SSPUNPAID_DETAIL_PAY = 5;
    public final static int ACTRES_SSPUNPAID_DETAIL_TO_LIST = 6;
    public final static int ACTRES_MPN_PAJAKKU = 7;
    public final static int ACTRES_CREATE_SSP = 8;
    public final static int ACTRES_FILE_BROWSER = 9;
    public final static int ACTRES_IGNORE = 10;  // utk diset yg balikannya diabaikan seperti beberapa permission WRITE_EXTERNAL_STORAGE
    public final static int ACTRES_KLU = 11;

    // request permission code
    public final static int RP_GET_ACCOUNT = 41;
    public final static int RP_WRITE_EXTERNAL_STORAGE = 42;

//    // TODO: @test
//    @Deprecated public final static String X_PAJAKKU_PROFILE = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc1ZhbGlkIjp0cnVlLCJpc3MiOiJpc3MiLCJzdWIiOiJzdWIiLCJhdWQiOlsiYXVkIl0sImV4cCI6MTU0Njk0NDQzMTQ3MSwiaWF0IjoxNTQ2OTQwODMxNDcxLCJqdGkiOiJqdGkiLCJtZXRhZGF0YSI6eyJpc0FkbWluIjoidHJ1ZSIsImVtYWlsIjoidXNlckBzaXRlLmNvbSIsInJvbGUiOiJ1c2VyIiwidXNlcm5hbWUiOiJ1c2VyIiwibW9iaWxlUGhvbmUiOiIxMjM0NTY3ODkwMTIiLCJpZCI6IjEiLCJucHdwIjoiMTIzNDU2Nzg5MDExMTExIn19.TaCVALiw8-iH9c_rrpQW-AwiTbx6HlCnbvQZKI5CCZg";

    private AppConstant(){}
}
