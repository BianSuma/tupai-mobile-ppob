package com.pajakku.tupaimobile.model.dto;

/**
 * Created by dul on 17/12/18.
 */

public class LoginDTO {
    private String client_id;
    private String redirect_uri;
    private String state;
    private String response_type;
    private String username;
    private String password;
    private String grant_type;

    public LoginDTO(String username, String password) {
        client_id = "FdUwvV72EH8sXwevDP5ftSTTF6DottzCBg1hQafZ";
        redirect_uri = "redirect_uri";
        state = "state";
        response_type = "code";
        this.username = username;
        this.password = password;
        grant_type = "password";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
