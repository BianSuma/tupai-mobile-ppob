package com.pajakku.tupaimobile.util;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.pajakku.tupaimobile.R;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;

//import com.jaiselrahman.filepicker.activity.FilePickerActivity;
//import com.jaiselrahman.filepicker.activity.FilePickerActivity;

//import okhttp3.ResponseBody;
//import retrofit2.Response;

/**
 * Created by dul on 31/12/18.
 */

public final class SharePref {

    public static final String SP_CHECK_MIGRASI_YEAR = "sp6";
    private static final String SP_RESPONSE_LOG = "sp11";
    public static final String SP_CURRENT_NIK = "sp12";
    public static final String SP_FCM_UPDATE = "sp13";
    public static final String SP_LAST_LOGIN = "sp14";

    @Deprecated   // segera hapus
    public static final String SPRK_COMMON = "rk00";
    public final static String SPRK_TEST = "rk1";
    public final static String SPRK_GENERATE_MPN_URL = "rk2";
    public final static String SPRK_GET_PAY_BILLING_STATUS = "rk3";
    public final static String SPRK_SINGLE_SSP = "rk4";
    public final static String SPRK_MPN_REFUND = "rk5";
    @Deprecated  // belum terpakai
    public final static String SPRK_LISTNEWEMPLOYEEREQ = "rk6";
    public final static String SPRK_FIRST = "rk7";
    public final static String SPRK_FCM = "rk8";
    public final static String SPRK_REVOKE = "rk9";
    public final static String SPRK_WP_ADD = "rk10";
    public final static String SPRK_WP_DEL = "rk11";
    public final static String SPRK_SSP_DEL = "rk12";
    public final static String SPRK_MPNPAJAKKU_PAYSTATUS = "rk13";
    public final static String SPRK_WP_EDIT = "rk14";
    public static final String SPRK_MPNPAJAKKU_REFUND = "rk15";
    public final static String SPRK_CEK_BILL_GENERATE = "rk16";
    public final static String SPRK_SSP_ADD = "rk17";
    public final static String SPRK_EREG_SUBMITWP = "rk18";
    public final static String SPRK_EREG_VALIDASI_2 = "rk19";
    public final static String SPRK_CHECKNEWCOMPANY = "rk20";  // company ------
    public final static String SPRK_CHECKNEWCOMPANY_ID = "rk20_1";
    public final static String SPRK_CHECKNEWCOMPANY_CODE = "rk20_2";
    public final static String SPRK_CHECKNEWCOMPANY_NAME = "rk20_3";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_ID = "rk20_4";
    public final static String SPRK_CHECKNEWCOMPANY_UMR = "rk20_5";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_ENABLEREFERRAL = "rk20_6";
    public final static String SPRK_CHECKNEWCOMPANY_REFERRALCODE = "rk20_7";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_EXPIRECODE = "rk20_8";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_PAYROLLDATE = "rk20_9";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_TAXTYPE = "rk20_10";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_FROM_COTYPE = "rk20_11";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_TO_COTYPE = "rk20_12";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_FROM_DATE = "rk20_13";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_TO_DATE = "rk20_14";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_SIGNATURENAME = "rk20_15";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_SIGNATURENPWP = "rk20_16";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_SIGNATURECITY = "rk20_17";
    public final static String SPRK_CHECKNEWCOMPANY_SETTING_CUSTOMMAIL = "rk20_18";
    public final static String SPRK_CHECKNEWCOMPANY_NPWP = "rk20_19";
    public final static String SPRK_EREG_VALIDASI_1 = "rk21";
    public final static String SPRK_EREG_KODEWILAYAH = "rk22";
    public final static String SPRK_EREG_UPLOAD_KTP = "rk23";
    public final static String SPRK_EREG_DATA_BY_EMAIL = "rk24";
    public static final String SPRK_EREG_UPDATE_KELENGKAPAN = "rk25";
    public final static String SPRK_EREG_LISTDATA = "rk26";
    public final static String SPRK_EREG_LOG = "rk27";
    public final static String SPRK_LISTTAXOBJECT = "rk28";
    public final static String SPRK_NEWOUTSOURCE = "rk29";
    public final static String SPRK_ONEEMPLOYEE = "rk30";
    public final static String SPRK_EDITNPWP = "rk31";
    public final static String SPRK_LISTMYBOS = "rk32";
    public final static String SPRK_LIST_OUTSOURCEHITUNG = "rk33";
    public final static String SPRK_RESEND_AKTIVATION = "rk34";
    public final static String SPRK_PERIODSUMMARY = "rk35";  // ____ periodsummary
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_ACTIVE = "rk35_1";  // _____ employee
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_NONACTIVE = "rk35_2";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_BPJSKERJA = "rk35_3";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_BPJSSEHAT = "rk35_4";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_TUNJANGAN = "rk35_5";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_POTONGAN = "rk35_6";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_TUNJANGANUPAH = "rk35_7";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_TUNJANGANTHR = "rk35_8";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_BRUTO = "rk35_9";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_PPHUPAH = "rk35_10";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_PPHTHR = "rk35_11";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_TUNJANGANPESANGON = "rk35_12";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_BRUTOPESANGON = "rk35_13";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_PPHPESANGON = "rk35_14";
    public final static String SPRK_PERIODSUMMARY_TEMP_ACTIVE = "rk35_15";  // ______ temp employee
    public final static String SPRK_PERIODSUMMARY_TEMP_NONACTIVE = "rk35_16";
    public final static String SPRK_PERIODSUMMARY_TEMP_BPJSKERJA = "rk35_17";
    public final static String SPRK_PERIODSUMMARY_TEMP_BPJSSEHAT = "rk35_18";
    public final static String SPRK_PERIODSUMMARY_TEMP_TUNJANGAN = "rk35_19";
    public final static String SPRK_PERIODSUMMARY_TEMP_POTONGAN = "rk35_20";
    public final static String SPRK_PERIODSUMMARY_TEMP_TUNJANGANUPAH = "rk35_21";
    public final static String SPRK_PERIODSUMMARY_TEMP_TUNJANGANTHR = "rk35_22";
    public final static String SPRK_PERIODSUMMARY_TEMP_BRUTO = "rk35_21";
    public final static String SPRK_PERIODSUMMARY_TEMP_PPHUPAH = "rk35_22";
    public final static String SPRK_PERIODSUMMARY_TEMP_PPHTHR = "rk35_23";
    public final static String SPRK_PERIODSUMMARY_TEMP_TUNJANGANPESANGON = "rk35_24";
    public final static String SPRK_PERIODSUMMARY_TEMP_BRUTOPESANGON = "rk35_25";
    public final static String SPRK_PERIODSUMMARY_TEMP_PPHPESANGON = "rk35_26";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_ACTIVE = "rk35_27";  // ______ temp tdk bulan employee
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_NONACTIVE = "rk35_28";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_BPJSKERJA = "rk35_29";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_BPJSSEHAT = "rk35_30";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_TUNJANGAN = "rk35_31";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_POTONGAN = "rk35_32";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_TUNJANGANUPAH = "rk35_33";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_TUNJANGANTHR = "rk35_34";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_BRUTO = "rk35_35";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_PPHUPAH = "rk35_36";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_PPHTHR = "rk35_37";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_TUNJANGANPESANGON = "rk35_38";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_BRUTOPESANGON = "rk35_39";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_PPHPESANGON = "rk35_40";
    public final static String SPRK_PERIODSUMMARY_EMPLOYEE_TOTALSALARY = "rk35_41";  // --------- total gaji
    public final static String SPRK_PERIODSUMMARY_TEMP_TOTALSALARY = "rk35_42";
    public final static String SPRK_PERIODSUMMARY_TEMP_TDKBULAN_TOTALSALARY = "rk35_43";  // --------- end period summary
    public final static String SPRK_SENDSALARYBUPOT = "rk36";
    public final static String SPRK_TUNJPOTONGAN = "rk37";
    public final static String SPRK_PHBRUTO = "rk38";
    public final static String SPRK_LISTTOTALSALARY = "rk39";
    public final static String SPRK_EARNINGTYPE = "rk40";
    public final static String SPRK_DEDUCTIONTYPE = "rk41";
    public final static String SPRK_GETONE_BYREFERRALCODE = "rk42";
    public static final String SPRK_GETONE_PROFILENPWP = "rk43";  // profile NPWP {
    public static final String SPRK_GETONE_PROFILENPWP_ID = "rk43_1";
    public static final String SPRK_GETONE_PROFILENPWP_NPWPHEADID = "rk43_2";
    public static final String SPRK_GETONE_PROFILENPWP_NPWP = "rk43_3";
    public static final String SPRK_GETONE_PROFILENPWP_NPWPNAME = "rk43_4";
    public static final String SPRK_GETONE_PROFILENPWP_TELP = "rk43_5";
    public static final String SPRK_GETONE_PROFILENPWP_EMAIL = "rk43_6";
    public static final String SPRK_GETONE_PROFILENPWP_ADDR = "rk43_7";
    public static final String SPRK_GETONE_PROFILENPWP_NPWPOP = "rk43_8";
    public static final String SPRK_GETONE_PROFILENPWP_NIK = "rk43_9";
    public static final String SPRK_GETONE_PROFILENPWP_YEAR = "rk43_10";
    public static final String SPRK_GETONE_PROFILENPWP_URUSEFIN = "rk43_11";
    public static final String SPRK_GETONE_PROFILENPWP_EFIN_NUM = "rk43_12";
    public static final String SPRK_GETONE_PROFILENPWP_EMP = "rk43_13";
    public static final String SPRK_GETONE_PROFILENPWP_BOS = "rk43_14";
    public static final String SPRK_GETONE_PROFILENPWP_FREEMAN = "rk43_15";
    public static final String SPRK_GETONE_PROFILENPWP_PKP = "rk43_16";
    public static final String SPRK_GETONE_PROFILENPWP_PKPDATE = "rk43_17";
    public static final String SPRK_GETONE_PROFILENPWP_NIKAH = "rk43_18";
    public static final String SPRK_GETONE_PROFILENPWP_TANGGUNGAN = "rk43_19";
    public static final String SPRK_GETONE_PROFILENPWP_PISAH_HARTA = "rk43_20";
    public static final String SPRK_GETONE_PROFILENPWP_NPWP_PASANGAN = "rk43_21";
    public static final String SPRK_GETONE_PROFILENPWP_AREA_ID = "rk43_22";
    public static final String SPRK_GETONE_PROFILENPWP_POSTCODE = "rk43_23";
    public static final String SPRK_GETONE_PROFILENPWP_PENGGUNAID = "rk43_24";
    // profile NPWP }
    public final static String SPRK_LISTEMPLOYEESTATUS = "rk44";
    public final static String SPRK_CALCULATEPPH21 = "rk45";
    public final static String SPRK_LIST_HITUNGTHR = "rk46";
    public final static String SPRK_ME = "rk47";
    public final static String SPRK_ME_EMAIL = "rk47_1";
    public final static String SPRK_LIST_BILLID = "rk48";
    public final static String SPRK_DEL_S_GAJI_DEL_BUKYAR = "rk49";
    public final static String SPRK_COUNT_NPWP = "rk50";
    public final static String SPRK_COUNT_NPWP_SIZE = "rk50_1";
    public static final String SPRK_POST_NEW_SOBATPAJAK_USER = "rk51";
    public final static String SPRK_LIST_ACTIVITYLOG = "rk52";
    public final static String SPRK_LIST_SETOR_SPT = "rk53";
    public final static String SPRK_NEW_EMPLOYEE_MANDIRI = "rk54";
    public final static String SPRK_ONE_SALARYPERIOD = "rk55";  // ---- gaji proses {
    public final static String SPRK_ONE_GAJIPROSES_ID = "rk55_1";
    public final static String SPRK_ONE_GAJIPROSES_MASA = "rk55_2";
    public final static String SPRK_ONE_GAJIPROSES_TAHUN = "rk55_3";
    public final static String SPRK_ONE_GAJIPROSES_REVISI = "rk55_4";
    public final static String SPRK_ONE_GAJIPROSES_STATUSHITUNG = "rk55_5";
    public final static String SPRK_ONE_GAJIPROSES_STATUSSPT = "rk55_6";
    public final static String SPRK_ONE_GAJIPROSES_STATUSIDBILL = "rk55_7";
    public final static String SPRK_ONE_GAJIPROSES_STATUSBAYAR = "rk55_8";
    public final static String SPRK_ONE_GAJIPROSES_STATUSLAPOR = "rk55_9";
    public final static String SPRK_ONE_GAJIPROSES_PERIOD_ID = "rk55_10";
    public final static String SPRK_ONE_GAJIPROSES_SPT_ID = "rk55_11";
    public final static String SPRK_ONE_GAJIPROSES_pphTfHutang = "rk55_12";
    public final static String SPRK_ONE_GAJIPROSES_pphFHutang = "rk55_13";
    public final static String SPRK_ONE_GAJIPROSES_pbk = "rk55_14";
    public final static String SPRK_ONE_GAJIPROSES_pphTfPbk = "rk55_15";
    public final static String SPRK_ONE_GAJIPROSES_pphFPbk = "rk55_16";
    public final static String SPRK_ONE_GAJIPROSES_pphTfNtpn = "rk55_17";
    public final static String SPRK_ONE_GAJIPROSES_pphFNtpn = "rk55_18";
    public final static String SPRK_ONE_GAJIPROSES_pphTfSisaNtpn = "rk55_19";
    public final static String SPRK_ONE_GAJIPROSES_pphFSisaNtpn = "rk55_20";
    public final static String SPRK_ONE_GAJIPROSES_pphTfSisaPbk = "rk55_21";
    public final static String SPRK_ONE_GAJIPROSES_pphFSisaPbk = "rk55_22";
    public final static String SPRK_ONE_GAJIPROSES_NPWPPENGGUNAID = "rk55_23";  // ----- gaji proses }
    public final static String SPRK_LIST_INBOX = "rk56";
    public final static String SPRK_ONE_SOBATGAJI_SPT21 = "rk57";
    public final static String SPRK_ONE_SUMMARY_HITUNG = "rk58";
    public final static String SPRK_ONE_SUMMARY_HITUNG_EMPLOYEE_STATUS = "rk58_1";
    public final static String SPRK_ONE_SUMMARY_HITUNG_TEMPORALMONTH_STATUS = "rk58_2";
    public static final String SPRK_ONE_SUMMARY_HITUNG_OUTSOURCE_STATUS = "rk58_3";
    public static final String SPRK_ONE_SUMMARY_HITUNG_OUTSOURCE_KTT_TB_COUNT = "rk58_4";
    public static final String SPRK_LIST_SUMMARY_HITUNG = "rk59";
    public static final String SPRK_ONE_OUTSOURCEHITUNG = "rk60";
    public static final String SPRK_ONE_DAFTAR_BIAYA = "rk61";
    public static final String SPRK_POST_BPE = "rk62";
    public static final String SPRK_UPD_SETTING = "rk63";
    public static final String SPRK_POST_CALCULATE_OUTSOURCEHITUNG = "rk67";
    public static final String SPRK_LIST_BPJS = "rk68";
    public static final String SPRK_UPD_BPJS = "rk69";
    public static final String SPRK_UPD_SOBATSETOR_BUKTIBAYAR = "rk70";
    public static final String SPRK_LIST_SOBATSETOR_PERSEPSI = "rk71";
    public static final String SPRK_LIST_SOBATSETOR_TAXTYPE = "rk72";
    public static final String SPRK_LIST_SOBATSETOR_KJS_FAV = "rk73";
    public static final String SPRK_LIST_SOBATSETOR_KJS = "rk74";
    public static final String SPRK_LIST_SOBATSETOR_ID_BILL = "rk75";
    public static final String SPRK_ONE_SOBATSETOR_INQUIRYNPWP = "rk76";
    public static final String SPRK_GET_INBOX_UNREAD_COUNT = "rk77";
    public static final String SPRK_POST_SEND_IDBILL_TO_EMAIL = "rk78";
    public static final String SPRK_ONE_BUKYAR = "rk79";
    public static final String SPRK_UPD_DAFTARBIAYA = "rk80";
    public static final String SPRK_CHECK_ADD_NPWP = "rk81";
    public static final String SPRK_SOBATGAJI_IDBILL_NEW = "rk82";
    public static final String SPRK_LIST_SOBATFAKTUR_SPT1111 = "rk83";
    public static final String SPRK_LIST_SOBATFAKTUR_FAKTURLISTITEM = "rk84";
    public static final String SPRK_LIST_SOBATFAKTUR_LAWANTRAX = "rk85";
    public static final String SPRK_LIST_SOBATFAKTUR_LAMP_A1 = "rk86";
    public static final String SPRK_LIST_SOBATFAKTUR_LAMP_A2 = "rk87";
    public static final String SPRK_LIST_S_USAHA_ENOFA = "rk88";
    public static final String SPRK_LIST_SOBATFAKTUR_BRGJASA = "rk89";
    public static final String SPRK_LIST_SOBATFAKTUR_SUPP = "rk90";
    public static final String SPRK_LIST_SOBATDAFTAR_NPWPREGISTERLOG = "rk91";
    public static final String SPRK_UPD_IAM_PROFILE = "rk92";
    public static final String SPRK_LIST_SOBATDAFTAR_EFINREGISTERLOG = "rk93";
    public static final String SPRK_DEL_PENGGAJIAN = "rk94";
    public static final String SPRK_NEW_SOBATDAFTAR_NPWPREGISTER = "rk95";
    public static final String SPRK_UPD_SOBATGAJI_IDBILLDONE = "rk96";
    public static final String SPRK_CEK_EMP_NEW = "rk97";
    public static final String SPRK_NEW_SOBATDAFTAR_EFINREGISTER = "rk98";
    public static final String SPRK_LIST_SOBATPPN_DETAILTRAX_BRG = "rk99";
    public static final String SPRK_POST_SOBATPPN_SAVEFAKTURSUPPLIER = "rk100";
    public static final String SPRK_POST_MIGRASI = "rk101";
    public static final String SPRK_UPD_FCM = "rk102";
    public static final String SPRK_GAJI_FINALISASI = "rk103";
    public static final String SPRK_GET_SOBATGAJI_SLIPGAJI = "rk104";
    public static final String SPRK_UPD_SOBATPAJAK_MSG_READ = "rk105";
    public static final String SPRK_UPD_SOBATGAJI_UPDATE_SPT = "rk106";
    public static final String SPRK_UPD_SOBATGAJI_EMP_ACTIVATION = "rk107";
    public static final String SPRK_SEND_SOBATGAJI_SPT_TO_EMAIL = "rk108";
    public static final String SPRK_POST_SOBATGAJI_GET_BUPOT = "rk109";
    public static final String SPRK_SOBATLAPOR_REQ_TOKEN = "rk110";
    public static final String SPRK_S_PAJAK_USERCONSOLE_PROFILE = "rk111";
    public static final String SPRK_S_PAJAK_EXCHANGE_TOKEN = "rk112";
    public static final String SPRK_LISL_S_PPN_TERUTANG_IDBILL = "rk113";
    public static final String SPRK_LISL_S_PPN_TERUTANG_BUKYAR = "rk114";
    public static final String SPRK_DEL_S_PPN_TERUTANG_BUKYAR = "rk115";
    public static final String SPRK_DEL_S_PPN_IDBILL_NEW = "rk116";
    public static final String SPRK_POST_S_USAHA_LAWANTRAX_NEW = "rk117";
    public static final String SPRK_DEL_S_USAHA_LAWANTRAX_DEL = "rk118";
    public static final String SPRK_LIST_S_PPN_FAKTURMASUK_NEW_ITEM = "rk119";
    public static final String SPRK_POST_S_USAHA_ENOFA_NEW = "rk120";
    public static final String SPRK_DEL_S_USAHA_ENOFA_DEL = "rk121";
    public static final String SPRK_PUT_S_USAHA_ENOFA_EDIT = "rk122";
    public static final String SPRK_GET_S_PPN_SPT1111_HISTORY = "rk123";
    public static final String SPRK_DEL_S_PPN_SPT1111_DEL = "rk124";
    public static final String SPRK_LIST_S_USAHA_KASBANK = "rk125";
    public static final String SPRK_POST_S_USAHA_NEW_TRANSFER_BANK = "rk126";
    public static final String SPRK_LIST_S_USAHA_PEMBELIAN = "rk127";
    public static final String SPRK_LIST_S_USAHA_FAKTUR_BELINEWPPN = "rk128";
    public static final String SPRK_LIST_S_USAHA_BIAYA = "rk129";
    public static final String SPRK_LIST_S_USAHA_BELINEWNONPPN_LUNAS = "rk130";
    public static final String SPRK_LIST_S_USAHA_BELINEWNONPPN_UANGMUKA = "rk131";
    public static final String SPRK_LIST_S_USAHA_ASET = "rk132";
    public static final String SPRK_LIST_S_USAHA_ASETJUAL = "rk133";
    public static final String SPRK_LIST_S_USAHA_ASETSUSUT = "rk134";
    public static final String SPRK_POST_S_USAHA_SAVEASETJUAL = "rk135";
    public static final String SPRK_POST_S_USAHA_EDITASETJUAL = "rk136";
    public static final String SPRK_LIST_S_USAHA_SUSUTDETAILPERIOD = "rk137";
    public static final String SPRK_LIST_S_USAHA_JUALASETDETAILPRODUK = "rk138";
    public static final String SPRK_LIST_S_USAHA_TAXSETORSENDIRI = "rk139";
    public static final String SPRK_LIST_S_USAHA_JUALASETDETAIL_BEBAN = "rk140";
    public static final String SPRK_LIST_S_USAHA_CATEGORY = "rk141";
    public static final String SPRK_LIST_S_USAHA_UNIT = "rk142";
    public static final String SPRK_POST_S_USAHA_CATEGORY_NEW = "rk143";
    public static final String SPRK_PUT_S_USAHA_CATEGORY_EDIT = "rk144";
    public static final String SPRK_POST_S_USAHA_UNIT_NEW = "rk145";
    public static final String SPRK_PUT_S_USAHA_UNIT_EDIT = "rk146";
    public static final String SPRK_GET_S_USAHA_ALL_PRODUKBRG = "rk147";
    public static final String SPRK_GET_S_USAHA_ALL_PRODUKJASA = "rk148";
    public static final String SPRK_GET_S_USAHA_ALL_PERSEDIAAN = "rk149";
    public static final String SPRK_GET_S_USAHA_ALL_REPORT_NERACA = "rk150";
    public static final String SPRK_GET_S_USAHA_ALL_REPORT_NERACA_DETAIL = "rk151";
    public static final String SPRK_GET_S_USAHA_ALL_REPORT_NERACA_DETAIL_TRAX = "rk152";
    public static final String SPRK_GET_S_USAHA_ALL_REPORT_NERACA_DETAIL_TRAX_DETAIL = "rk153";
    public static final String SPRK_GET_S_USAHA_ALL_DAFTAR_AKUN_COA = "rk154";
    public static final String SPRK_GET_SOB_DAFTAR_ALL_NPWP_PROF = "rk155";
    public static final String SPRK_GET_SOB_PAJAK_IMG_AVATAR = "rk156";
    public static final String SPRK_GET_SOB_UPAH_SEND_REPORTBANK_TO_EMAIL = "rk157";
    public static final String SPRK_GET_SOB_UPAH_SEND_REPORTSALARY_TO_EMAIL = "rk158";
    public static final String SPRK_GET_SOB_UPAH_SEND_REPORTEMP_TO_EMAIL = "rk159";
    public static final String SPRK_POST_SOB_DAFTAR_SAVE_VALIDASI_1_KTP_KK = "rk160";
    public static final String SPRK_POST_SOB_DAFTAR_SAVE_VALIDASI_2_BIOMETRIK = "rk161";
    public static final String SPRK_GET_SOB_DAFTAR_LIST_KODE_WILAYAH = "rk162";
    public static final String SPRK_GET_SOB_DAFTAR_ONE_SIGNATURE = "rk163";
    public static final String SPRK_PUT_SOB_PJK_EDIT_NPWP_UTAMA = "rk164";
    public static final String SPRK_GET_LIST_TAX_TYPE = "rk165";
    public static final String SPRK_GET_LIST_KJS = "rk166";
    public static final String SPRK_GET_S_DAFTAR_LIST_DATA = "rk167";
    public static final String SPRK_GET_S_DAFTAR_ONE_BY_EMAIL = "rk168";

    private static SharedPreferences sharePref;

    public static SharedPreferences getInstance(Context ctx){
        if(sharePref == null) sharePref = ctx.getSharedPreferences(ctx.getString(R.string.global_package_name)+AppConstant.SHARE_PREF_KEY, Context.MODE_PRIVATE);
        return sharePref;
    }

    public static void boolSet(Context ctx, String key, boolean b){
        SharedPreferences.Editor editor = getInstance(ctx).edit();
        editor.putBoolean(key, b);
        editor.commit();
    }
    public static boolean boolGet(Context ctx, String key){
        return getInstance(ctx).getBoolean(key, false);
    }

    public static void intSet(Context ctx, String key, int d){
        SharedPreferences.Editor editor = getInstance(ctx).edit();
        editor.putInt(key, d);
        editor.commit();
    }
    public static int intGet(Context ctx, String key){
        return getInstance(ctx).getInt(key, 0);
    }

    public static void setLong(Context ctx, String key, long d){
        SharedPreferences.Editor editor = getInstance(ctx).edit();
        editor.putLong(key, d);
        editor.commit();
    }
    public static long getLong(Context ctx, String key){
        return getInstance(ctx).getLong(key, 0);
    }

    public static void setStr(Context ctx, String key, String data){
        SharedPreferences.Editor editor = getInstance(ctx).edit();
        editor.putString(key, data);
        editor.commit();
    }
    public static String getStr(Context ctx, String key){
        return getInstance(ctx).getString(key, "");
    }

    public static void del(Context ctx, String key){
        SharedPreferences.Editor editor = getInstance(ctx).edit();
        editor.remove(key);
        editor.commit();
    }

    // ------- SPECIFIC

//    public static String pwdGet(Activity ctx){
//        Account account = HttpReq.getAccount(ctx);
//        String key = SharePref.getStr(ctx, AppConstant.SP_PASSWORD_AES_KEY);
//        String password = HttpReq.getInstanceAM(ctx).getPassword(account);
//        if(password == null) return "";
//        try {
//            return AESCrypt.decrypt(key, password);
//        }catch (GeneralSecurityException e){
//            return password;
//        }
//    }

//    public static void yearSet(Context ctx, int y){
//        SharedPreferences.Editor editor = getInstance(ctx).edit();
//        editor.putInt(AppConstant.SP_YEAR, y);
//        editor.commit();
//    }
//    public static int yearGet(Context ctx){
//        return getInstance(ctx).getInt(AppConstant.SP_YEAR, DateFunc.currentYear());
//    }

    public static void nikSet(Context ctx, String nik){
        SharedPreferences.Editor editor = getInstance(ctx).edit();
        editor.putString(SharePref.SP_CURRENT_NIK, nik);
        editor.commit();
    }
    public static String nikGet(Context ctx){
        return getInstance(ctx).getString(SharePref.SP_CURRENT_NIK, "");
    }

    // companyId
    public static String compIdGet(Context ctx){
        return getInstance(ctx).getString(SharePref.SPRK_CHECKNEWCOMPANY_ID, "");
    }

//    public static void npwpIdSet(Context ctx, long id){
//        SharedPreferences.Editor editor = getInstance(ctx).edit();
//        editor.putLong(AppConstant.SP_CURRENT_PROFILENPWP, id);
//        editor.commit();
//    }
//    public static long npwpIdGet(Context ctx){
//        return getInstance(ctx).getLong(AppConstant.SP_CURRENT_PROFILENPWP, 0);
//    }

    public static void trackLog(Context ctx, int type, String str){
        if(!trackLogStatus()) return;

        Set<String> set = getInstance(ctx).getStringSet(SP_RESPONSE_LOG, new HashSet<String>());

        if( set.size() >= 15 ){
            set.remove(set.iterator().next());
        }

        set.add( (type+" "+str+"\n") );

        SharedPreferences.Editor editor = getInstance(ctx).edit();
        editor.putStringSet(SP_RESPONSE_LOG, set);
        editor.commit();
    }

//    public static String trackLogGet(Context ctx){
//        Set<String> set = getInstance(ctx).getStringSet(SP_RESPONSE_LOG, new HashSet<String>());
//        if(set.size() <= 0){
//            return "Empty log";
//        }
//
//        DisplayMetrics dm = Utility.getScreenWH(ctx);
//        BaseActivity ba = (BaseActivity)ctx;
//        float density  = ba.getResources().getDisplayMetrics().density;
//        float dpWidth  = dm.widthPixels / density;
//        float dpHeight = dm.heightPixels / density;
//
//        String str = Utility.getDeviceName()+" "+ dpWidth+"x"+dpHeight+ " v"+Utility.getVersionName(ctx)+"\n";
//        str += set.toString();
//
//        return str;
//    }
//
//    public static void trackLogCopy(Context ctx){
//        Utility.copy(ctx, trackLogGet(ctx));
//        Utility.toast(ctx, "Log Error disalin");
//    }

    // ----------------

    public static boolean trackLogStatus(){
        return true;
    }

    private SharePref(){}

}
