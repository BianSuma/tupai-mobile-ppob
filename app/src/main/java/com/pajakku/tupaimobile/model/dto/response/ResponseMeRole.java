package com.pajakku.tupaimobile.model.dto.response;

import com.pajakku.tupaimobile.model.Company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 16/01/19.
 */

public class ResponseMeRole implements Serializable {
        public String name;
        public Boolean isAdmin;

        // ---------- NOT NULL

        public boolean isAdminNotNull(){
            if(isAdmin == null) return false;
            return isAdmin;
        }
}
