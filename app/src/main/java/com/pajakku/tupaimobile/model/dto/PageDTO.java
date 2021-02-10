package com.pajakku.tupaimobile.model.dto;

import com.tech.freak.wizardpager.model.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageDTO <T> implements Serializable {
    public Long page;
    public Integer size;
    public Integer sort;  // -1
    public String sortBy;  // "id"
    public Long total;
    public List<T> data;

    public PageDTO(){
        data = new ArrayList<>();
    }
}
