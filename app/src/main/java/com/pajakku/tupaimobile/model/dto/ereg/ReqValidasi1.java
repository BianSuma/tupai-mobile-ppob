package com.pajakku.tupaimobile.model.dto.ereg;

import com.pajakku.tupaimobile.util.AppConstant;

import java.io.Serializable;

public class ReqValidasi1 implements Serializable {

    public static final int JENISWP_PRIBADI = 1;
    public static final int JENISWP_USAHA = 2;

    public String gelarDepanWp;
    public String gelarBelakangWp;
    public String almKTPNomor;
    public Boolean nikExist;
    public Boolean nikValid;
    public String kategoriWp;
    public String emailWp;
    public String kdNegaraWp;
    public String kdNegaraSuami;
    public Integer jenisWp;
    public String photoKtpFilename;
    public String jenisKelamin;
    public String noIDWp;
    public String tmpLahirWp;
    public String tglLahirWp;
    public String namaWp;
    public String stsNikahWp;
    public String stsPusat;  // PUSAT / CABANG
    public String nomorHPWp;
    public String noKKWp;
    public String almKTPJalan;
    public String almKTPBlok;
    public String almKTPRT;
    public String almKTPRW;
    public String almKTPKdWilayah;

    public ReqDomisili domisili;

//    public WilayahDTO bundleAlmKTPKdWilayah;

    public ReqValidasi1(){
        domisili = new ReqDomisili();
    }

    // ----------- NOT NULL

    public String kdNegaraWpNotNull(){
        if(kdNegaraWp == null) return "";
        return kdNegaraWp;
    }

    // -------------

    public Boolean isWNI(boolean nullable){
        if( kdNegaraWp == null ) return nullable ? null : false;
        return kdNegaraWp.equalsIgnoreCase(AppConstant.EREG_KODE_NEGARA_INDO);
    }
}
