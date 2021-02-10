package com.pajakku.tupaimobile.model.dto.response;

/**
 * Created by dul on 12/07/19.
 */

public class TransLog {

    public static final String STATUS_LUNAS = "LUNAS";
    public static final String STATUS_RESPONSE_FAIL = "RESPONSE_FAIL";

    public long id;
    public String refId;
    public long userId;
    public long sspId;
    public String mcashProvider;
    public String status;
    public String createdAt;
    public String updatedAt;
}
