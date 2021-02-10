package com.pajakku.tupaimobile.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 05/07/19.
 */

public class BrutoTax extends ListModelBase {
    public List<BrutoTaxItem> l;  // item list

    public BrutoTax(){
        l = new ArrayList<>();
    }
}
