package com.pajakku.tupaimobile.model.dto.response;

/**
 * Created by dul on 17/12/18.
 */

public class ResponseAuthorize {
    private String code;
    private String state;

    public ResponseAuthorize() {
    }

    public ResponseAuthorize(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ResponseAuthorize{" +
                "code='" + code + '\'' +
                '}';
    }
}
