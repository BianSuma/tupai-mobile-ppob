package com.pajakku.tupaimobile.model;

import com.pajakku.tupaimobile.model.dto.Subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 12/02/19.
 */

public class Company implements Serializable {
    public long id;
    public String name;
    public String address;
    public String city;
    public String phone;
    public String email;
    public String npwp;
    public String postalCode;
    public String fax;
    public String website;
    public String industry;
    public List<Subscription> subscriptions = new ArrayList<>();

    public Company(){}
}
