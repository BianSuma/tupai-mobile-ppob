package com.pajakku.tupaimobile.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pajakku.tupaimobile.dao.TaxtypeDao;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

import java.util.List;

/**
 * Created by dul on 28/12/18.
 */

@Entity(tableName = Kjs.TABLE_NAME)
public class Kjs extends ModelMultiSelect {  // tax slip code
    public static final String TABLE_NAME = "kjs";

    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_NAME = "name";

    @PrimaryKey
    public long id;
    @ColumnInfo( name = COLUMN_CODE )
    public String code;
    @ColumnInfo( name = COLUMN_NAME )
    public String name;
    @ColumnInfo(name="nop_active")
    public boolean nopActive;
    @ColumnInfo(name="nosk_active")
    public boolean noSkActive;
    @ColumnInfo(name="subjek_pajak_active")
    public boolean subjekPajakActive;

    @ColumnInfo(name="month1id")
    public Integer month1Id;
    @ColumnInfo(name="month1active")
    public Boolean month1Active;
    @ColumnInfo(name="month2id")
    public Integer month2Id;
    @ColumnInfo(name="month2active")
    public Boolean month2Active;

    public Kjs() {
    }

    @Override
    @Ignore
    public String toString() {
        return code + " - " + name;
    }

    @Ignore
    public boolean fetchMonth1Active(){
        if(month1Active == null) return true;
        return month1Active;
    }

    @Ignore
    public boolean fetchMonth2Active(){
        if(month2Active == null) return true;
        return month2Active;
    }

    public String fullName(){
        return code + " - " + name;
    }

    // ------------------ NOT NULL

    @Ignore
    public String codeNotNull(){
        if(code == null) return "";
        return code;
    }

    @Ignore
    public String nameNotNull(){
        if(name == null) return "";
        return name;
    }

    // ---------

    @Ignore
    public static Kjs findOneByCode(List<Kjs> l, String code){
        if(code == null) return null;
        for(Kjs d : l){
            if( d.codeNotNull().equalsIgnoreCase(code) ) return d;
        }
        return null;
    }
}
