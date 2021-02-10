package com.pajakku.tupaimobile.model.dto;

import java.io.Serializable;

import okhttp3.ResponseBody;

public class ResponseDTO implements Serializable {
    public Integer code;
    public String message;

    public Integer kode;
    public String error_description;
    public FieldErrorMessage errorMessage;

    public ResponseDTO(){
        errorMessage = new FieldErrorMessage();
    }

    public void setNulled(){
        if(code == null){
            if(kode != null) code = kode;
        }
        if(message == null) {
            if (error_description != null) message = error_description;
            if (errorMessage.id != null) message = errorMessage.id;
        }
    }

    // -------------- NOT NULL

    public String messageNotNull(){
        if(message == null) return "";
        return message;
    }
}
