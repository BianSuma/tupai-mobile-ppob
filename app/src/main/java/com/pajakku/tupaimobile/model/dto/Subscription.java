package com.pajakku.tupaimobile.model.dto;

import com.pajakku.tupaimobile.model.Wajibpajak;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dul on 20/02/19.
 */

public class Subscription implements Serializable {
    public long id;
    public List<Wajibpajak> wajibpajaks;
}
