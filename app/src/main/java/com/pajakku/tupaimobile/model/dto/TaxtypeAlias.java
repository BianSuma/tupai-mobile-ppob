package com.pajakku.tupaimobile.model.dto;

/**
 * Created by dul on 01/02/19.
 */

public class TaxtypeAlias {
    public static final int WPTYPE_ALL = 0;
    public static final int WPTYPE_UKM = 1;

    public String code;
    public int name;
    public int[] wpType;
    public int category;
    public int icon;

    public TaxtypeAlias(String code, int name, int []wpType, int category, int icon) {
        this.code = code;
        this.name = name;
        this.wpType = wpType;
        this.category = category;
        this.icon = icon;
    }
}
