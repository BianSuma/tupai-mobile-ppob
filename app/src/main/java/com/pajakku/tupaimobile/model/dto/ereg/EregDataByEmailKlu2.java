package com.pajakku.tupaimobile.model.dto.ereg;

import java.io.Serializable;

public class EregDataByEmailKlu2 implements Serializable {
        public String id;
        public String klu2KdKlu;
        public String klu2Merk;
        public String klu2Pembukuan;
        public String klu2HavePegawai;
        public String klu2ThBukuAkhir;
        public String klu2ThBukuAwal;
        public String klu2Uraian;

        // ------------------ NOT NULL

        public String klu2KdKluNotNull(){
                if(klu2KdKlu == null) return "";
                return klu2KdKlu;
        }

        public String klu2ThBukuAwalNotNull(){
                if(klu2ThBukuAwal== null) return "";
                return klu2ThBukuAwal;
        }

        public String klu2ThBukuAkhirNotNull(){
                if(klu2ThBukuAkhir == null) return "";
                return klu2ThBukuAkhir;
        }
}
