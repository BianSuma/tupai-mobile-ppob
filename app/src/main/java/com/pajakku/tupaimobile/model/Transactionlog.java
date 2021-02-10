package com.pajakku.tupaimobile.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.dao.TaxtypeDao;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

import java.io.Serializable;

/**
 * Created by dul on 25/01/19.
 */

@Entity(tableName = Transactionlog.TABLE_NAME)
public class Transactionlog extends ModelMultiSelect implements Serializable {
    public static final String TABLE_NAME = "transactionlog";

    public static final String COLUMN_UPDATEDAT = "updated_at";

    public static final int TRANSTYPE_PAYSSP = 1;

    public static final int TRANSSTATUS_START = 1;
    public static final int TRANSSTATUS_SUCCESS_EMON_TRANSACTION = 2;
    public static final int TRANSSTATUS_SUCCESS_FRAME_PAYMENT = 3;
    public static final int TRANSSTATUS_FAIL_FRAMEPAYMENT = 4;  // success reversal
    public static final int TRANSSTATUS_FAIL_FRAMEPAYMENT_BEFOREREVERSAL = 5;  // fail frame payment, belum reversal
    public static final int TRANSSTATUS_FAIL_FIRSTREVERSAL = 6;
    public static final int TRANSSTATUS_FAIL_SECONDREVERSAL = 7;

    @PrimaryKey
    public long id;
    @ColumnInfo(name="trans_type")
    public int transType;
    @ColumnInfo(name="trans_status")
    public int transStatus;
    @ColumnInfo(name="amount")
    public String amount;
    @ColumnInfo(name="bill_number")
    public String billNumber;
    @ColumnInfo(name="request_trans_number")
    public String requestTransNumber;
    @ColumnInfo(name="created_at")
    public String createdAt;
    @ColumnInfo(name=COLUMN_UPDATEDAT)
    public String updatedAt;


    public Transactionlog(){}

    @Ignore
    public int fetchStatus(){
        switch (transStatus){
            case Transactionlog.TRANSSTATUS_SUCCESS_FRAME_PAYMENT:
                return R.string.translog_label_statussuccess;
            case Transactionlog.TRANSSTATUS_FAIL_FRAMEPAYMENT:
                return R.string.translog_label_statusfail;
        }
        return R.string.translog_label_statusproceed;
    }

    @Ignore
    public int fetchLabelTransType(){
        switch (transType){
            case Transactionlog.TRANSTYPE_PAYSSP:
                return R.string.translog_label_transtypepayssp;
        }
        return R.string.translog_label_transtypeunknown;
    }
}
