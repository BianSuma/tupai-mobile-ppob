package com.pajakku.tupaimobile.model.dto.ereg;

import com.pajakku.tupaimobile.model.dto.PickedDTO;
import com.pajakku.tupaimobile.model.holder.HolderCommon2Val;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WilayahDTO implements Serializable {
    public String id;
    public String name;
    public String noProp;
    public String namaProp;
    public String noKab;
    public String namaKab;
    public String noKec;
    public String namaKec;
    public String noKel;

    // -----------------

    public String nameNotNull(){
        if(name == null) return "";
        return name;
    }

    public String namaPropNotNull(){
        if(namaProp == null) return "";
        return namaProp;
    }

    public String namaKabNotNull(){
        if(namaKab == null) return "";
        return namaKab;
    }

    public String namaKecNotNull(){
        if(namaKec == null) return "";
        return namaKec;
    }

    // --------------

    public HolderCommon2Val toHolderCommon2Val(){
        HolderCommon2Val d = new HolderCommon2Val();
        d.bold = id + ", "+nameNotNull();
        d.mini = namaKecNotNull() + " " + namaKabNotNull() + " " + namaPropNotNull();
        d.object = this;
        return d;
    }

    public static List<HolderCommon2Val> toHolderCommon2Val(List<WilayahDTO> l){
        List<HolderCommon2Val> ll = new ArrayList<>();
        for(WilayahDTO i : l) ll.add( i.toHolderCommon2Val() );
        return ll;
    }

    public PickedDTO toPickedDTO(){
        PickedDTO d = new PickedDTO();
        d.name = id + ", "+nameNotNull()+ " "+namaKabNotNull();
        d.object = this;
        return d;
    }

    public static List<PickedDTO> toPickedDTO(List<WilayahDTO> l){
        List<PickedDTO> ll = new ArrayList<>();
        for(WilayahDTO i : l) ll.add( i.toPickedDTO() );
        return ll;
    }
}
