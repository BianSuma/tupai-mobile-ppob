package com.pajakku.tupaimobile.model.dto;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dul on 02/01/19.
 */

public class PajakkuClaim {
    public boolean isValid = true;
    public String iss = "iss";
    public String sub = "sub";
    public List<String> aud = Arrays.asList("aud");
    public long exp = System.currentTimeMillis() + 3600000;
    public long iat = System.currentTimeMillis();
    public String jti = "jti";
    public Map<String,String> metadata;

    public  PajakkuClaim() {
        this.metadata = new HashMap<>();
        metadata.put("npwp","123456789011111");
        metadata.put("email","user@site.com");
        metadata.put("username","user");
        metadata.put("mobilePhone","123456789012");
        metadata.put("role","user");
        metadata.put("isAdmin","true");
    }
}
