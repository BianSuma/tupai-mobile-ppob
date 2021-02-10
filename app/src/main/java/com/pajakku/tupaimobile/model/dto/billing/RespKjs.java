package com.pajakku.tupaimobile.model.dto.billing;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 28/12/18.
 */


public class RespKjs implements Serializable {  // tax slip code

    public String id;
    public String code;
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

    public RespKjs() {
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

    // -------------- CONVERT

    public Kjs toKjs(){
        Kjs d = new Kjs();
        d.code = code;
        d.name = name;
        d.nopActive = nopActive;
        d.noSkActive = noSkActive;
        d.subjekPajakActive = subjekPajakActive;
        d.month1Id = month1Id;
        d.month1Active = month1Active;
        d.month2Id = month2Id;
        d.month2Active = month2Active;
        return d;
    }

    public static List<Kjs> toKjs(List<RespKjs> l){
        List<Kjs> ll = new ArrayList<>();
        for(RespKjs d : l) ll.add(d.toKjs());
        return ll;
    }
}
