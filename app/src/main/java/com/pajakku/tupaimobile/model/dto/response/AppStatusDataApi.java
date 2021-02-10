package com.pajakku.tupaimobile.model.dto.response;

import java.io.Serializable;

/**
 * Created by dul on 17/07/19.
 */

public class AppStatusDataApi implements Serializable {
    public String mpnPajakkuWidget;
    public String mpnPajakkuClientId;
    public String mpnPajakkuHost;
    public String mpnPajakkuBase;

    public String eregHost;

    // ------------ NOT NULL

    public String mpnPajakkuWidgetNotNull(){
        if(mpnPajakkuWidget== null) return "";
        return mpnPajakkuWidget;
    }

    public String mpnPajakkuClientIdNotNull(){
        if(mpnPajakkuClientId == null) return "";
        return mpnPajakkuClientId;
    }

    public String mpnPajakkuHostNotNull(){
        if(mpnPajakkuHost == null) return "";
        return mpnPajakkuHost;
    }

    public String mpnPajakkuBaseNotNull(){
        if(mpnPajakkuBase == null) return "";
        return mpnPajakkuBase;
    }
}
