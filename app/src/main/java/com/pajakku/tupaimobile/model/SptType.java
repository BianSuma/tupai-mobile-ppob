package com.pajakku.tupaimobile.model;

import com.pajakku.tupaimobile.model.dto.ListModelBase;

import java.io.Serializable;

/**
 * Created by dul on 05/07/19.
 */

public class SptType extends ListModelBase implements Serializable {
    public static final String TYPE_1770 = "1770";
    public static final String TYPE_23_26 = "23_26";
    public static final String TYPE_4_2 = "4_2";

    public String code;
    public String alias;
    public int aliasList;
    public int info;
}
