package com.pajakku.tupaimobile.model.actdata;

import com.pajakku.tupaimobile.model.actparam.ActParamMpnPajakku;
import com.pajakku.tupaimobile.model.actparam.ActParamRefundForm;
import com.pajakku.tupaimobile.model.dto.mpnpajakku.ReqRefund;

import java.io.Serializable;

public class ActDataRefundForm implements Serializable {
    public ActParamRefundForm ap;
    public ReqRefund saving;

    public ActDataRefundForm(){
        saving = new ReqRefund();
    }
}
