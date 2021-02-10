package com.pajakku.tupaimobile.model.dto.ereg;

import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;

import java.io.Serializable;

public class EregDataByEmailUsaha implements Serializable {

        public String id;
        public String almUsahaJalan;
        public String almUsahaNomor;
        public String almUsahaRT;
        public String almUsahaRW;
        public String almUsahaKdWilayah;
        public String almUsahaFaksimili;
        public String almUsahaHP;
        public String almUsahaNoTelp;
        public String almUsahaKota;
        public String almUsahaProvinsi;

        // --------------

        public void setNulled(ActDataRegistrasiNpwp actData){
                if(almUsahaJalanNotNull().isEmpty()) almUsahaJalan = actData.savingValidasi1.almKTPJalan;
                if(almUsahaRTNotNull().isEmpty()) almUsahaRT = actData.savingValidasi1.almKTPRT;
                if(almUsahaRWNotNull().isEmpty()) almUsahaRW = actData.savingValidasi1.almKTPRW;
                if(almUsahaKdWilayahNotNull().isEmpty()) almUsahaKdWilayah = actData.savingValidasi1.almKTPKdWilayah;
                if(almUsahaHPNotNull().isEmpty()) almUsahaHP = actData.savingValidasi1.nomorHPWp;
        }

        // ---------------- NOT NULL

        public String almUsahaJalanNotNull(){
                if(almUsahaJalan == null) return "";
                return almUsahaJalan;
        }

        public String almUsahaRTNotNull(){
                if(almUsahaRT==null) return "";
                return almUsahaRT;
        }

        public String almUsahaRWNotNull(){
                if(almUsahaRW==null) return "";
                return almUsahaRW;
        }

        public String almUsahaKdWilayahNotNull(){
                if(almUsahaKdWilayah == null) return "";
                return almUsahaKdWilayah;
        }

        public String almUsahaHPNotNull(){
                if(almUsahaHP==null) return "";
                return almUsahaHP;
        }

        // ----------------
}
