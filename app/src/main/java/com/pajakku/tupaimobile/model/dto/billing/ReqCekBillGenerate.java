package com.pajakku.tupaimobile.model.dto.billing;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.pajakku.tupaimobile.model.Kjs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 28/12/18.
 */


public class ReqCekBillGenerate implements Serializable {
    public Long sspId;
    public String refCode;
}
