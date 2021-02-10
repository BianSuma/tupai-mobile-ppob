package com.pajakku.tupaimobile.model.actdata;

import com.pajakku.tupaimobile.model.actparam.ActParamSspDetailDone;
import com.pajakku.tupaimobile.model.actparam.ActParamSspDetailUnpaid;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;

import java.io.Serializable;

public class ActDataSspDetailDone implements Serializable {
    public ActParamSspDetailDone ap;
    public ResponseSsp responseSsp;
}
