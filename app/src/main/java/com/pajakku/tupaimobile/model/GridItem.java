package com.pajakku.tupaimobile.model;

import com.pajakku.tupaimobile.model.dto.GridItemTypeBundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 18/12/18.
 */

public class GridItem {
    public int icon;
    public String name;
    private boolean selected;
    public int type;
    public List<GridItem> itemList;
    public Taxtype taxtype;

    public GridItem(int icon, String name, int type) {
        this.icon = icon;
        this.name = name;
        this.selected = false;
        this.type = type;
        itemList = new ArrayList<>();
    }

    public GridItem(int icon, String name, boolean selected) {
        this.icon = icon;
        this.name = name;
        this.selected = selected;
        this.type = TYPE_WP;
        itemList = new ArrayList<>();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<GridItem> getItemList() {
        return itemList;
    }

    public void addItem(GridItem item) {
        this.itemList.add(item);
    }

    public void clearItem(){
        itemList.clear();
    }

    public static final int TYPE_PPH = 1;
    public static final int TYPE_PPN = 2;
    public static final int TYPE_PBB = 3;
    public static final int TYPE_BUNGA = 4;
    public static final int TYPE_TAXMISC = 5;

    public static final int TYPE_WP = 8;
    public static final int TYPE_OTHER = 9;

    public static GridItemTypeBundle filterByType(List<GridItem> list){
        GridItemTypeBundle ret = new GridItemTypeBundle();
        for(GridItem gi : list){
            switch(gi.type){
                case TYPE_PPH: ret.addPph(gi);
                    break;
                case TYPE_PPN: ret.addPpn(gi);
                    break;
                case TYPE_PBB: ret.addPbb(gi);
                    break;
                case TYPE_BUNGA: ret.addBunga(gi);
                    break;
                default:
                    ret.addTaxmisc(gi);
            }
        }
        return ret;
    }
}
