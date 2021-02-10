package com.pajakku.tupaimobile.model.dto.response;

import com.pajakku.tupaimobile.model.Company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 16/01/19.
 */

public class ResponseMe implements Serializable {
    public long id;
    public String username;
    public String name;
    public String email;
    public String mobilePhone;
    public ResponseMeRole role;
    public List<Company> company = new ArrayList<>();

    // -----------------

    public ResponseMe(){
        role = new ResponseMeRole();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
