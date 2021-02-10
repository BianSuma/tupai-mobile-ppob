package com.pajakku.tupaimobile.model.dto.ereg;

import com.pajakku.tupaimobile.model.actdata.ActDataRegistrasiNpwp;

import java.io.Serializable;

public class EregDataByEmail implements Serializable {

        public String id;

        public String lokasiPoto;
        public String lokasiKtp;
        public String status;  // "VALIDASI TAHAP 2 : GAGAL",
        public String message;
        public String user;
        public Integer jenisWp;
        public Boolean agreement;
        public String createdDate;
        public String updatedDate;

        public EregDataByEmailWp wp;
        public EregDataByEmailDomisili domisili;
        public EregDataByEmailKtp ktp;
        public EregDataByEmailKlu klu;
        public EregDataByEmailKlu2 klu2;
//        public EregDataByEmailKlu klu3;
        public EregDataByEmailUsaha usaha;
        public EregDataByEmailSyarat persyaratan;

        public EregDataByEmail(){
                wp = new EregDataByEmailWp();
                domisili = new EregDataByEmailDomisili();
                ktp = new EregDataByEmailKtp();
                klu = new EregDataByEmailKlu();
                klu2 = new EregDataByEmailKlu2();
//                klu3 = new EregDataByEmailKlu();
                usaha = new EregDataByEmailUsaha();
                persyaratan = new EregDataByEmailSyarat();
        }

        public void setNulled(ActDataRegistrasiNpwp actData){
                wp.setNulled(actData);
                usaha.setNulled(actData);
        }

        // ------------ NOT NULL

        public String statusNotNull(){
                if(status == null) return "";
                return status;
        }
}
