package com.pajakku.tupaimobile.model.dto.response;

import com.pajakku.tupaimobile.model.dto.request.FrameMPNCustomerData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dul on 28/01/19.
 */

public class ResponseFinnetFrame {

    public static final String RESULTCODE_SUCCESS = "00";

    public String resultCode;
    public String resultDesc;
    public String amount;
    public String bit61;
    public String traxId;

    public FrameMPNCustomerData customerData;

}
