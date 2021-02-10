package com.pajakku.tupaimobile.model.dto.ereg;

import java.io.Serializable;

public class EregDataByEmailKlu implements Serializable {

        public String id;
        public String klu1JnsPegawai;
        public String klu1KdKlu;
        public String klu1Uraian;

        // -------- NOT NULL

        public String klu1JnsPegawaiNotNull(){
                if(klu1JnsPegawai == null) return "";
                return klu1JnsPegawai;
        }

        public String klu1KdKluNotNull(){
                if(klu1KdKlu == null) return "";
                return klu1KdKlu;
        }
}
