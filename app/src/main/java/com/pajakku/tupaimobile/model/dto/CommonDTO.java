package com.pajakku.tupaimobile.model.dto;

import java.io.Serializable;

public class CommonDTO implements Serializable {

    public static final int CODE_TEST = 1;  // nilainya harus sama dg server

    // admin code

    // public code
    public static final int CODE_GENERATE_MPN_URL = 501;  // nilainya harus sama dg server
    // {\"username\":\"QsMDEbiAmtOfKbGPxoLlEngGMTYvvbwnWcboKyCf\",\"accessKey\":\"https://mpn.bdg.pajakku.com/widget/pay?access_key=ZmjbLYdhTNglJotIfLrapHBBikErhvBijRLqiRzi\",\"expiredDateTime\":\"2020-08-05T18:53:45.103\"}
    // getAccessKey()+"&widgetClientId="+clientIdMpn+"&billingCode="+kodeBilling
    public static final int CODE_STATUS_BILLING = 502;
    public static final int CODE_MPN_REFUND = 503;
    public static final int CODE_LIST_KJP = 504;
    public static final int CODE_LIST_KJS = 505;
    public static final int CODE_FIRST = 506;  // pertama kali request api tupai, setelah login sukses
    public static final int CODE_WP_ADD = 507;
    public static final int CODE_WP_LIST = 508;
    public static final int CODE_WP_DEL = 509;
    public static final int CODE_WP_ONE = 510;
    public static final int CODE_WP_EDIT = 511;
    public static final int CODE_SSP_LIST = 512;
    public static final int CODE_FCM = 513;
    public static final int CODE_SSP_ONE = 514;
    public static final int CODE_SSP_DEL = 515;
    public static final int CODE_CEK_BILL_GENERATE = 516;
    public static final int CODE_SSP_ADD = 517;
    public static final int CODE_EREG_SUBMITWP = 518;
    public static final int CODE_EREG_VALIDASI_1 = 519;
    public static final int CODE_EREG_KODE_WILAYAH = 520;
    public static final int CODE_EREG_DATA_BY_EMAIL = 521;
    public static final int CODE_EREG_UPDATE_KELENGKAPAN = 522;
    public static final int CODE_EREG_LIST_DATA = 523;
    public static final int CODE_EREG_LOG = 524;

    public static final int CODE_EREG_VALIDASI_2 = 1000;
    public static final int CODE_EREG_UPLOAD_KTP = 1001;

    public Integer code;
    public String json;
}
