package com.pajakku.tupaimobile.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.dao.TaxtypeDao;
import com.pajakku.tupaimobile.model.dto.ModelMultiSelect;
import com.pajakku.tupaimobile.model.dto.TaxtypeAlias;
import com.pajakku.tupaimobile.model.response.RespListTaxType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dul on 25/01/19.
 */
@Entity(tableName = TaxtypeDao.TABLE_NAME)
public class Taxtype extends ModelMultiSelect {  // KAP

    public static final String COLUMN_ID = "id";

    public static final TaxtypeAlias[] taxtypeAliases = {
             new TaxtypeAlias("411111", R.string.fraghome_griditem_minyakbumi, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411112", R.string.fraghome_griditem_gasalam, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411119", R.string.fraghome_griditem_migas, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411121", R.string.fraghome_griditem_pph21, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411122", R.string.fraghome_griditem_pph22, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411123", R.string.fraghome_griditem_pph22impor, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411124", R.string.fraghome_griditem_pph23, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411125", R.string.fraghome_griditem_pph2539op, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411126", R.string.fraghome_griditem_pph2539badan, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411127", R.string.fraghome_griditem_pph26, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411128", R.string.fraghome_griditem_pphfinal, new int[]{TaxtypeAlias.WPTYPE_UKM}, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411129", R.string.fraghome_griditem_pphnonmigas, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411131", R.string.fraghome_griditem_pphfiskal, null, GridItem.TYPE_PPH, R.drawable.ic_pph)
        ,    new TaxtypeAlias("411211", R.string.fraghome_griditem_ppndlmnegeri, null, GridItem.TYPE_PPN, R.drawable.ic_ppn)
        ,    new TaxtypeAlias("411212", R.string.fraghome_griditem_ppnimport, null, GridItem.TYPE_PPN, R.drawable.ic_ppn)
        ,    new TaxtypeAlias("411219", R.string.fraghome_griditem_ppnlainnya, null, GridItem.TYPE_PPN, R.drawable.ic_ppn)
        ,    new TaxtypeAlias("411221", R.string.fraghome_griditem_ppnbmdlmnegeri, null, GridItem.TYPE_PPN, R.drawable.ic_ppn)
        ,    new TaxtypeAlias("411222", R.string.fraghome_griditem_ppnbmimport, null, GridItem.TYPE_PPN, R.drawable.ic_ppn)
        ,    new TaxtypeAlias("411229", R.string.fraghome_griditem_ppnbmlainnya, null, GridItem.TYPE_PPN, R.drawable.ic_ppn)
        ,    new TaxtypeAlias("411313", R.string.fraghome_griditem_pbbkebun, null, GridItem.TYPE_PBB, R.drawable.ic_pbb)
        ,    new TaxtypeAlias("411314", R.string.fraghome_griditem_pbbhutan, null, GridItem.TYPE_PBB, R.drawable.ic_pbb)
        ,    new TaxtypeAlias("411315", R.string.fraghome_griditem_pbbminerba, null, GridItem.TYPE_PBB, R.drawable.ic_pbb)
        ,    new TaxtypeAlias("411316", R.string.fraghome_griditem_pbbmigas, null, GridItem.TYPE_PBB, R.drawable.ic_pbb)
        ,    new TaxtypeAlias("411317", R.string.fraghome_griditem_pbbpanasbumi, null, GridItem.TYPE_PBB, R.drawable.ic_pbb)
        ,    new TaxtypeAlias("411319", R.string.fraghome_griditem_pbbp3sektor, null, GridItem.TYPE_PBB, R.drawable.ic_pbb)
        ,    new TaxtypeAlias("411611", R.string.fraghome_griditem_beamaterai, null, GridItem.TYPE_TAXMISC, R.drawable.ic_taxmisc)
        ,    new TaxtypeAlias("411612", R.string.fraghome_griditem_jualmaterai, null, GridItem.TYPE_TAXMISC, R.drawable.ic_taxmisc)
        ,    new TaxtypeAlias("411613", R.string.fraghome_griditem_pdptnpajaklain, null, GridItem.TYPE_TAXMISC, R.drawable.ic_taxmisc)
        ,    new TaxtypeAlias("411619", R.string.fraghome_griditem_tdklangsunglain, null, GridItem.TYPE_TAXMISC, R.drawable.ic_taxmisc)
        ,    new TaxtypeAlias("411621", R.string.fraghome_griditem_bungapph, null, GridItem.TYPE_BUNGA, R.drawable.ic_taxbunga)
        ,    new TaxtypeAlias("411622", R.string.fraghome_griditem_bungappn, null, GridItem.TYPE_BUNGA, R.drawable.ic_taxbunga)
        ,    new TaxtypeAlias("411623", R.string.fraghome_griditem_bungappnbm, null, GridItem.TYPE_BUNGA, R.drawable.ic_taxbunga)
        ,    new TaxtypeAlias("411624", R.string.fraghome_griditem_bungaptll, null, GridItem.TYPE_BUNGA, R.drawable.ic_taxbunga)
    };

    @PrimaryKey
    public long id;
    @ColumnInfo(name="id_str")
    public String idStr;
    @ColumnInfo(name="code")
    public String code;
    @ColumnInfo(name="name")
    public String name;

    public Taxtype(){}

    // ------------- NOT NULL

    @Ignore
    public String codeNotNull(){
        if(code == null) return "";
        return code;
    }

    @Ignore
    public String nameNotNull(){
        if(name == null) return "";
        return name;
    }

    // ------------- NOT NULL

    @Ignore
    public String fullName(){
        return code +" - " + name;
    }

    @Ignore
    public int fetchIcon(){
        for(TaxtypeAlias tta : taxtypeAliases){
            if(tta.code.equals(code)){
                if(tta.icon != 0) return tta.icon;
            }
        }
        return R.drawable.ic_pph;
    }

    @Ignore
    public String fetchAlias(Resources res){
        for(TaxtypeAlias tta : taxtypeAliases){
            if(tta.code.equals(code)){
                if(tta.name != 0) return res.getString(tta.name);
            }
        }

        return code;
    }

    @Ignore
    public int fetchCategory(){
        return fetchCategoryStatic(code);
    }

    @Ignore
    public static int fetchCategoryStatic(String code){
        for(TaxtypeAlias tta : taxtypeAliases){
            if(tta.code.equals(code)){
                if(tta.category != 0) return tta.category;
            }
        }
        return GridItem.TYPE_TAXMISC;
    }

    @Ignore
    public static Drawable fetchColoredItem(Context ctx, int icon, int gridCategory){
        Drawable d = ContextCompat.getDrawable(ctx, icon);

        switch(gridCategory) {
            case GridItem.TYPE_PPH:
                d.setColorFilter(Color.parseColor("#00a6bf"), PorterDuff.Mode.SRC_IN);
                break;
            case GridItem.TYPE_PPN:
                d.setColorFilter(Color.parseColor("#8444b5"), PorterDuff.Mode.SRC_IN);
                break;
            case GridItem.TYPE_PBB:
                d.setColorFilter(Color.parseColor("#28b833"), PorterDuff.Mode.SRC_IN);
                break;
            case GridItem.TYPE_BUNGA:
                d.setColorFilter(Color.parseColor("#9ddb19"), PorterDuff.Mode.SRC_IN);
                break;
            case GridItem.TYPE_TAXMISC:
                d.setColorFilter(Color.parseColor("#ed8f2a"), PorterDuff.Mode.SRC_IN);
                break;
        }

        return d;
    }

    @Ignore
    public static int fetchBackCircle(int gridCategory){
        switch(gridCategory) {
            case GridItem.TYPE_PPH:
                return R.drawable.bg_gridcircle_pph;
            case GridItem.TYPE_PPN:
                return R.drawable.bg_gridcircle_ppn;
            case GridItem.TYPE_PBB:
                return R.drawable.bg_gridcircle_pbb;
            case GridItem.TYPE_BUNGA:
                return R.drawable.bg_gridcircle_bunga;
            case GridItem.TYPE_TAXMISC:
                return R.drawable.bg_gridcircle_misctax;
        }
        return 0;
    }

    // ------------------ CONVERT

    @Ignore
    public RespListTaxType toRespListTaxType(){
        RespListTaxType d = new RespListTaxType();
        d.id = idStr;
        d.code = code;
        d.name = name;
        return d;
    }

    @Ignore
    public static List<RespListTaxType> toRespListTaxType(List<Taxtype> l){
        List<RespListTaxType> ll = new ArrayList<>();
        for(Taxtype d : l) ll.add( d.toRespListTaxType() );
        return ll;
    }

    @Override
    @Ignore
    public String toString(){
        return code + " - " +name;
    }

}
