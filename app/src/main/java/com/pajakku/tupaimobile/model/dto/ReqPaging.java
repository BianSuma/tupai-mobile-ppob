package com.pajakku.tupaimobile.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 05/07/19.
 */

public class ReqPaging implements Serializable {
    public Long page;
    public Integer size;
    public String order;
    public String column;
    public String field;
    public String query;
    public Boolean isPaid;
}
