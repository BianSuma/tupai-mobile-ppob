package com.pajakku.tupaimobile.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;

import java.io.Serializable;

/**
 * Created by dul on 28/12/18.
 */

@Entity(tableName = Wajibpajak.TABLE_NAME)
public class Wajibpajak extends ModelMultiSelect {
    public static final String TABLE_NAME = "wajibpajak";

    public static final String COLUMN_NAME = "name";

    @PrimaryKey
    public long id;
    @ColumnInfo(name = COLUMN_NAME)
    public String name;
    @ColumnInfo(name="npwp")
    public String npwp;
    @ColumnInfo(name="address")
    public String address;
    @ColumnInfo(name="city")
    public String city;
    @ColumnInfo(name="postal_code")
    public String postalCode;
    @ColumnInfo(name="phone")
    public String phone;
    @ColumnInfo(name="email")
    public String email;
    @ColumnInfo(name="pic_name")
    public String picName;
    @ColumnInfo(name="pic_email")
    public String picEmail;
    @ColumnInfo(name="pic_position")
    public String picPosition;
    @ColumnInfo(name="pic_phone")
    public String picPhone;


    public Wajibpajak() {
    }

    @Override
    @Ignore
    public String toString() {
        return "Wajibpajak{" +
                "name='" + name + '\'' +
                ", npwp='" + npwp + '\'' +
                ", address='" + address + '\'' +
//                ", icon='" + icon + '\'' +
                '}';
    }
}
