package com.pajakku.tupaimobile.model;

//import com.orm.SugarRecord;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.dao.TaxtypeDao;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 17/01/19.
 */

@Entity(tableName = Sspdone.TABLE_NAME)
public class Sspdone extends Sspunpaid {
    public static final String TABLE_NAME = "sspdone";

    public Sspdone(){}

    @Ignore
    public static List<Sspdone> getInstanceListSspdone(List<ResponseSsp> sspdtos){
//        Sspdone ssp;
        List<Sspdone> list = new ArrayList<>();
        for(ResponseSsp dto : sspdtos){
            list.add( dto.toSspdone() );
        }
        return list;
    }

    // TODO: fix
    @Ignore
    public int fetchStatus(){
        return R.string.sspstatus_done;
    }
}
