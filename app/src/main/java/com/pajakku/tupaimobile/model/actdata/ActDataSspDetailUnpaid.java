package com.pajakku.tupaimobile.model.actdata;

import com.pajakku.tupaimobile.model.actparam.ActParamMpnPajakku;
import com.pajakku.tupaimobile.model.actparam.ActParamSspDetailUnpaid;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;

import java.io.Serializable;

public class ActDataSspDetailUnpaid implements Serializable {
    public ActParamSspDetailUnpaid ap;
    public ResponseSsp responseSsp;
}
