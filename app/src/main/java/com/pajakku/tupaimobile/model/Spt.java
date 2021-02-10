package com.pajakku.tupaimobile.model;

import androidx.room.Ignore;
import android.content.Context;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

/**
 * Created by dul on 28/12/18.
 */

//@Entity
public class Spt extends ModelMultiSelect {
    public static final String TABLE_NAME = "spt";

//    public static final String COLUMN_CODE = "code";
//    public static final String COLUMN_NAME = "name";

    public static final String STATUS_DRAFT = "Draft";
    public static final String STATUS_SUBMIT = "Sudah Submit";
    public static final String STATUS_DONE = "Selesai";

//    @PrimaryKey
    public long id;
//    @ColumnInfo( name = COLUMN_CODE )
    public String sptTypeCode;
    public String npwp;
    public String wpName;
//    public String taxInfo;
    public long amount;
    public int year;
    public int pembetulan;
    public String ntte;
    public String status;
    public String workType;
    public String klu;
    public String pasutriStatus;
    public String pasutriNpwp;

    public Spt() {
    }

    @Ignore
    public int fetchStatusIcon(){
        if( status.equals(STATUS_DRAFT) ) return R.drawable.spt_status_draft;
        if( status.equals(STATUS_SUBMIT) ) return R.drawable.spt_status_submit;
        if( status.equals(STATUS_DONE) ) return R.drawable.spt_status_done;
        return 0;
    }

    @Ignore
    public int fetchStatusIconWhite(){
        if( status.equals(STATUS_DRAFT) ) return R.drawable.spt_statuswhite_draft;
        if( status.equals(STATUS_SUBMIT) ) return R.drawable.spt_statuswhite_submit;
        if( status.equals(STATUS_DONE) ) return R.drawable.spt_statuswhite_done;
        return 0;
    }

    @Ignore
    public int fetchStatusColor(){
        switch (fetchStatusIcon()){
            case R.drawable.spt_status_draft: return R.color.sptstatus_draft;
            case R.drawable.spt_status_submit: return R.color.sptstatus_submit;
            case R.drawable.spt_status_done: return R.color.sptstatus_done;
        }
        return 0;
    }

    @Ignore
    public String fetchStatusDesc(Context ctx){
        switch (fetchStatusIcon()){
            case R.drawable.spt_status_draft: return ctx.getString(R.string.modellabel_sptdraft);
            case R.drawable.spt_status_submit: return ctx.getString(R.string.modellabel_sptsubmit);
            case R.drawable.spt_status_done: return ctx.getString(R.string.modellabel_sptdone);
        }
        return status;
    }

    @Ignore
    public String fetchPembetulan(){
        if( pembetulan > 0 ) return ("Pembetulan ke-" + pembetulan);
        return "Normal";
    }

    @Override
    @Ignore
    public String toString() {
        return "Spt{" +
                "id=" + id +
                ", npwp='" + npwp + '\'' +
//                ", taxInfo='" + taxInfo + '\'' +
                '}';
    }
}
