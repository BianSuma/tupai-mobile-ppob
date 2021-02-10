package com.pajakku.tupaimobile.model.dto.response;

import android.content.Context;

import com.pajakku.tupaimobile.util.Utility;

import java.io.Serializable;

/**
 * Created by dul on 17/07/19.
 */

public class AppStatusData implements Serializable {
    public String backVersion;
    public String buildDate;
    public String clientVersion;

    public boolean isOutdateVersion;
    public String lastClientVersion;
    public String mpnWidgetUser;
    public String mpnWidgetPass;

    public AppStatusDataApi api;

    public AppStatusData(){
        api = new AppStatusDataApi();
    }

    public void setNulled(Context ctx){
        String currVer = Utility.getPInfo(ctx).versionName;

        isOutdateVersion = !clientVersion.equals(currVer);
        lastClientVersion = currVer;
    }

    // --------------

    public String clientVersionNotNull(){
        if(clientVersion == null) return "";
        return clientVersion;
    }
}
