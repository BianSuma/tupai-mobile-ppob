package com.pajakku.tupaimobile.model;

/**
 * Created by dul on 18/12/18.
 */

public class WpNtpn {
    private String kap;
    private String kjs;
    private String monthYear;
    private String setor;
    private String ntpn;

    public WpNtpn(String kap, String kjs, String monthYear, String setor, String ntpn) {
        this.kap = kap;
        this.kjs = kjs;
        this.monthYear = monthYear;
        this.setor = setor;
        this.ntpn = ntpn;
    }

    public String getKap() {
        return kap;
    }

    public void setKap(String kap) {
        this.kap = kap;
    }

    public String getKjs() {
        return kjs;
    }

    public void setKjs(String kjs) {
        this.kjs = kjs;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getNtpn() {
        return ntpn;
    }

    public void setNtpn(String ntpn) {
        this.ntpn = ntpn;
    }
}
