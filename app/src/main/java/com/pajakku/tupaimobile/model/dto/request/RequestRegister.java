package com.pajakku.tupaimobile.model.dto.request;

/**
 * Created by dul on 17/12/18.
 */

public class RequestRegister {
    private String username;
    public String name;
    public String email;
    private String password;
    private String verifyPassword;
    public String mobilePhone;

    public RequestRegister(){}

    @Deprecated
    public RequestRegister(String username, String name, String email, String password, String verifyPassword, String mobilePhone) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.verifyPassword = verifyPassword;
        this.mobilePhone = mobilePhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
