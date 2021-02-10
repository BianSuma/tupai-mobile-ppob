package com.pajakku.tupaimobile.model.dto.ereg;

import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;

import java.io.Serializable;

public class EregDataByEmailWp implements Serializable {
    public String id;
    public String kategoriWp;
    public String npwpPusat;
    public String namaWp;
    public String nomorTelpWp;
    public String nomorHPWp;
    public String emailWp;
    public String kdNegaraWp;
    public String noIDWp;
    public String noKKWp;
    public String noKitasWp;
    public String tmpLahirWp;
    public String tglLahirWp;
    public String stsNikahWp;
    public String jmlTanggunganWp;  // bisa string kosong ""
    public String penghasilanWp;  // bisa string kosong ""
    public String jenisKelamin;    // "L"

    public void setNulled(ActDataRegistrasiNpwp actData){
        namaWp = actData.savingValidasi1.namaWp;
        emailWp = actData.savingValidasi1.emailWp;
        nomorTelpWp = actData.savingValidasi1.nomorHPWp;
        nomorHPWp = actData.savingValidasi1.nomorHPWp;
        kdNegaraWp = actData.savingValidasi1.kdNegaraWp;
        jmlTanggunganWp = actData.savingTanggungan;
        penghasilanWp = actData.savingJmlPenghasilan;

        if(actData.savingValidasi1.isWNI(false)){
            noKKWp = actData.savingValidasi1.noKKWp;
            noKitasWp = "";
        }else{
            noKKWp = "";
            noKitasWp = actData.savingValidasi1.noKKWp;
        }

        jenisKelamin = actData.savingValidasi1.jenisKelamin;

//        if(emailWpNotNull().isEmpty()) emailWp = actData.savingValidasi1.emailWp;
//        if(nomorTelpWpNotNull().isEmpty()) nomorTelpWp = actData.savingValidasi1.nomorHPWp;
//        if(nomorHPWpNotNull().isEmpty()) nomorHPWp = actData.savingValidasi1.nomorHPWp;
//        if(kdNegaraWpNotNull().isEmpty()) kdNegaraWp = actData.savingValidasi1.kdNegaraWp;
//        if(jmlTanggunganWpNotNull().isEmpty()) jmlTanggunganWp = actData.savingTanggungan;
//        if(penghasilanWpNotNull().isEmpty()) penghasilanWp = actData.savingJmlPenghasilan;
//        if(noKitasWpNotNull().isEmpty()) noKitasWp = actData.savingValidasi1.noKKWp;
//        if(jenisKelaminNotNull().isEmpty()) jenisKelamin = actData.savingValidasi1.jenisKelamin;
    }

    // ------------------- NOT NULL

    public String namaWpNotNull(){
        if(namaWp == null) return "";
        return namaWp;
    }

    public String emailWpNotNull(){
        if(emailWp==null) return "";
        return emailWp;
    }

    public String kdNegaraWpNotNull(){
        if(kdNegaraWp == null) return "";
        return kdNegaraWp;
    }

    public String noIDWpNotNull(){
        if(noIDWp == null) return "";
        return noIDWp;
    }

    public String nomorTelpWpNotNull(){
        if(nomorTelpWp == null) return "";
        return nomorTelpWp;
    }

    public String nomorHPWpNotNull(){
        if(nomorHPWp == null) return "";
        return nomorHPWp;
    }

    public String jmlTanggunganWpNotNull(){
        if(jmlTanggunganWp == null) return "";
        return jmlTanggunganWp;
    }

    public String penghasilanWpNotNull(){
        if(penghasilanWp == null) return "";
        return penghasilanWp;
    }

    public String noKitasWpNotNull(){
        if(noKitasWp == null) return "";
        return noKitasWp;
    }

    public String jenisKelaminNotNull(){
        if(jenisKelamin == null) return "";
        return jenisKelamin;
    }
}
