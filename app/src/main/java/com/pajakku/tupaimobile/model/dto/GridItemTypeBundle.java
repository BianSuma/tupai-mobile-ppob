package com.pajakku.tupaimobile.model.dto;

import com.pajakku.tupaimobile.model.GridItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 03/01/19.
 */

public class GridItemTypeBundle {
    public List<GridItem> pph;
    public List<GridItem> ppn;
    public List<GridItem> pbb;
    public List<GridItem> bunga;
    public List<GridItem> taxmisc;

    public GridItemTypeBundle() {
        pph = new ArrayList<>();
        ppn = new ArrayList<>();
        pbb = new ArrayList<>();
        bunga = new ArrayList<>();
        taxmisc = new ArrayList<>();
    }

    public void addPph(GridItem e){
        pph.add(e);
    }

    public void addPpn(GridItem e){
        ppn.add(e);
    }

    public void addPbb(GridItem e){
        pbb.add(e);
    }
    public void addBunga(GridItem e){
        bunga.add(e);
    }

    public void addTaxmisc(GridItem e){
        taxmisc.add(e);
    }

}
