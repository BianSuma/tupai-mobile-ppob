package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.model.GridItem;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.GridItemTypeBundle;
import com.pajakku.tupaimobile.model.dto.TaxtypeAlias;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dul on 18/12/18.
 */

public class GridCollection extends LinearLayout {

    private BaseActivity baseActivity;

    private GridItem currentWpType;
    private GridItem wpTypeAll;
    private GridItem wpTypeUkm;

    private Button btnLoad;

    public AppGridView agvWp;
    private AppGridView agvRecent;
    public AppGridView agvPph;
    public AppGridView agvPpn;
    private AppGridView agvPbb;
    private AppGridView agvBunga;
    private AppGridView agvTaxMisc;
    private AppGridView agvOther;

    private TextView labelCategoryRecent;
    private TextView labelCategoryPph;
    private TextView labelCategoryPpn;
    private TextView labelCategoryPbb;
    private TextView labelCategoryBunga;
    private TextView labelCategoryTaxMisc;

    public View invisView;

    public GridCollection(Context context, AttributeSet attrs){
        super(context, attrs);

        baseActivity = (BaseActivity)context;

        setOrientation(VERTICAL);

        int pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);
        TextView categoryHead;
        LayoutParams layoutParams;
        GridItem gi;
        List<GridItem> items;

        // category tax
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        categoryHead = new TextView(context, attrs);
        categoryHead.setId(View.generateViewId());
        categoryHead.setLayoutParams(layoutParams);
        categoryHead.setText(R.string.fraghome_tax_payment);
        categoryHead.setTypeface(null, Typeface.BOLD);
        categoryHead.setGravity(Gravity.CENTER);
        categoryHead.setTextColor(Color.parseColor("#ffffff"));
        categoryHead.setBackgroundColor(Color.parseColor("#314ea6"));
        categoryHead.setPadding(pad,pad,pad,pad);
        addView(categoryHead);
        categoryHead.setVisibility(GONE);

        // button load tax code
        int padBtnLoad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.button_height));
        layoutParams.topMargin = padBtnLoad;
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        btnLoad = new Button(context, attrs);
        btnLoad.setLayoutParams( layoutParams );
        btnLoad.setText(R.string.comp_gridcollection_btn_reloadtaxtype);
        btnLoad.setBackgroundResource(R.drawable.bg_btn_common);
        btnLoad.setPadding(padBtnLoad, 0, padBtnLoad, 0);
        addView(btnLoad);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, pad);

        int padGridContent = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        agvWp = new AppGridView(context, attrs);
        agvWp.setNumColumns(2);
        agvWp.setId(View.generateViewId());
        agvWp.setLayoutParams(layoutParams);
        agvWp.setBackgroundColor(Color.parseColor("#f2f2f2"));
        agvWp.setVisibility( GONE );
        agvWp.setPadding(padGridContent, padGridContent, padGridContent, padGridContent);
        addView(agvWp);

        items = new ArrayList<>();
        currentWpType = wpTypeUkm = new GridItem(R.drawable.ic_tie_ukm, getResources().getString(R.string.fraghome_griditem_ukm), true);
        items.add(wpTypeUkm);
        wpTypeAll = new GridItem(R.drawable.ic_alltax, getResources().getString(R.string.fraghome_griditem_all), false);
        items.add(wpTypeAll);
        agvWp.setListener(new AppGridView.AppListener() {

            @Override
            public void onClickItem(GridItem gi) {
                clickWpType(gi);
            }
        });
        agvWp.addAll(items);

        // grid recent
        labelCategoryRecent = createLabelCategory(context, attrs, R.string.fraghome_recent_tax);

        agvRecent = new AppGridView(context, attrs);
        agvRecent.setId(View.generateViewId());
        agvRecent.setLayoutParams(layoutParams);
        addView(agvRecent);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        invisView = new View(context, attrs);
        invisView.setLayoutParams(lp);
        invisView.setBackgroundResource(android.R.color.transparent);
        addView(invisView);

        // grid pph
        labelCategoryPph = createLabelCategory(context, attrs, R.string.fraghome_pay_pph);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pad, pad, pad, pad);

        agvPph = new AppGridView(context, attrs);
        agvPph.setId(View.generateViewId());
        agvPph.setLayoutParams(layoutParams);
        addView(agvPph);

        // grid ppn
        labelCategoryPpn = createLabelCategory(context, attrs, R.string.fraghome_label_gridcategoryppn);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pad, pad, pad, pad);

        agvPpn = new AppGridView(context, attrs);
        agvPpn.setId(View.generateViewId());
        agvPpn.setLayoutParams(layoutParams);
        addView(agvPpn);

        // grid pbb
        labelCategoryPbb = createLabelCategory(context, attrs, R.string.fraghome_label_gridcategorypbb);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pad, pad, pad, pad);

        agvPbb = new AppGridView(context, attrs);
        agvPbb.setId(View.generateViewId());
        agvPbb.setLayoutParams(layoutParams);
        addView(agvPbb);

        // grid bunga penagihan
        labelCategoryBunga = createLabelCategory(context, attrs, R.string.fraghome_label_gridcategorybunga);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pad, pad, pad, pad);

        agvBunga = new AppGridView(context, attrs);
        agvBunga.setId(View.generateViewId());
        agvBunga.setLayoutParams(layoutParams);
        addView(agvBunga);

        // grid tax misc
        labelCategoryTaxMisc = createLabelCategory(context, attrs, R.string.fraghome_label_gridcategorytaxmisc);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pad, pad, pad, pad);

        agvTaxMisc = new AppGridView(context, attrs);
        agvTaxMisc.setId(View.generateViewId());
        agvTaxMisc.setLayoutParams(layoutParams);
        addView(agvTaxMisc);

        // grid other
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        categoryHead = new TextView(context, attrs);
        categoryHead.setId(View.generateViewId());
        categoryHead.setLayoutParams(layoutParams);
        categoryHead.setText(R.string.fraghome_label_gridcategoryother);
        categoryHead.setTypeface(null, Typeface.BOLD);
        categoryHead.setGravity(Gravity.CENTER);
        categoryHead.setTextColor(Color.parseColor("#ffffff"));
        categoryHead.setBackgroundColor(Color.parseColor("#314ea6"));
        categoryHead.setPadding(pad,pad,pad,pad);
        addView(categoryHead);
        categoryHead.setVisibility(GONE);  // TODO: fix

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pad, pad, pad, pad);

        agvOther = new AppGridView(context, attrs);
        agvOther.setId(View.generateViewId());
        agvOther.setLayoutParams(layoutParams);
        addView(agvOther);
        agvOther.setVisibility(GONE);  // TODO: fix

        items = new ArrayList<>();
        gi = new GridItem(R.drawable.fraghome_griditem_pulse, getResources().getString(R.string.fraghome_griditem_pulse), GridItem.TYPE_OTHER);
        items.add(gi);
        gi = new GridItem(R.drawable.fraghome_griditem_pascabayar, getResources().getString(R.string.fraghome_griditem_pascabayar), GridItem.TYPE_OTHER);
        items.add(gi);
        gi = new GridItem(R.drawable.fraghome_griditem_tokenpln, getResources().getString(R.string.fraghome_griditem_tokenpln), GridItem.TYPE_OTHER);
        items.add(gi);
        gi = new GridItem(R.drawable.fraghome_griditem_tagihanpln, getResources().getString(R.string.fraghome_griditem_tagihanpln), GridItem.TYPE_OTHER);
        items.add(gi);
        gi = new GridItem(R.drawable.fraghome_griditem_bpjs, getResources().getString(R.string.fraghome_griditem_bpjs), GridItem.TYPE_OTHER);
        items.add(gi);
        gi = new GridItem(R.drawable.fraghome_griditem_indihome, getResources().getString(R.string.fraghome_griditem_indihome), GridItem.TYPE_OTHER);
        items.add(gi);

        agvOther.addAll(items);
    }

    public void init(OnClickListener btnLoadClickListener, AppGridView.AppListener appListener){

        btnLoad.setOnClickListener(btnLoadClickListener);

        agvRecent.setListener(appListener);
        agvPph.setListener(appListener);
        agvPpn.setListener(appListener);
        agvPbb.setListener(appListener);
        agvBunga.setListener(appListener);

        agvTaxMisc.setListener(appListener);

        agvOther.setListener(appListener);
    }

    private TextView createLabelCategory(Context context, AttributeSet attrs, int str){
        int pad = getResources().getDimensionPixelSize(R.dimen.holo_shadow_pad);

        LayoutParams relativeParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParam.setMargins(pad, 0, pad, 0);

        pad = getResources().getDimensionPixelSize(R.dimen.layout_content_pad);

        TextView labelCategory = new TextView(context, attrs);
        labelCategory.setId(View.generateViewId());
        labelCategory.setLayoutParams(relativeParam);
        labelCategory.setText(str);
        labelCategory.setGravity(Gravity.CENTER);
        labelCategory.setTextColor(Color.parseColor("#aaaaaa"));
        labelCategory.setBackgroundResource(R.drawable.bg_labelcategory_tax);
        labelCategory.setPadding(pad,pad,pad,pad);
        labelCategory.setVisibility( GONE );
        addView(labelCategory);

        return labelCategory;
    }


    public void setTaxType(List<Taxtype> taxtypes){
//        gridWpIconHighlight(wpTypeUkm);
        if( taxtypes.size() > 0 ) {
            btnLoad.setVisibility( GONE );
            agvWp.setVisibility( VISIBLE );
        }else {
            btnLoad.setVisibility( VISIBLE );
            agvWp.setVisibility( GONE );
        }

        wpTypeAll.clearItem();
        wpTypeUkm.clearItem();

        List<GridItem> itemsPph = new ArrayList<>();
        List<GridItem> itemsPpn = new ArrayList<>();
        List<GridItem> itemsPbb = new ArrayList<>();
        List<GridItem> itemsBunga = new ArrayList<>();
        List<GridItem> itemsTaxMisc = new ArrayList<>();

        GridItem gi;
        for(Taxtype tt : taxtypes){
            gi = new GridItem(tt.fetchIcon(), tt.fetchAlias(getResources()), tt.fetchCategory());
            gi.taxtype = tt;
            switch ( tt.fetchCategory() ){
                case GridItem.TYPE_PPH: itemsPph.add(gi);
                    break;
                case GridItem.TYPE_PPN: itemsPpn.add(gi);
                    break;
                case GridItem.TYPE_PBB: itemsPbb.add(gi);
                    break;
                case GridItem.TYPE_BUNGA: itemsBunga.add(gi);
                    break;
                default:
                    itemsTaxMisc.add(gi);
            }

            for(TaxtypeAlias alias : Taxtype.taxtypeAliases){
                if(!alias.code.equals(tt.code)) continue;
                if(alias.wpType == null) continue;
                for(int wp : alias.wpType){
                    if(wp == TaxtypeAlias.WPTYPE_UKM) wpTypeUkm.addItem(gi);
                }
            }
            wpTypeAll.addItem(gi);
        }

        agvPph.addAll(itemsPph);
        agvPpn.addAll(itemsPpn);
        agvPbb.addAll(itemsPbb);
        agvBunga.addAll(itemsBunga);

        agvTaxMisc.addAll(itemsTaxMisc);

        clickWpType(wpTypeUkm);

//        gridWpIconHighlight(wpTypeUkm);
//
//        GridItemTypeBundle typeBundle = new GridItemTypeBundle();
//        typeBundle.pph = itemsPph;
//        typeBundle.ppn = itemsPpn;
//        typeBundle.pbb = itemsPbb;
//        typeBundle.bunga = itemsBunga;
//        typeBundle.taxmisc = itemsTaxMisc;
//        labelCategoryShow( typeBundle );
    }

    private void clickWpType(GridItem giWp){

        gridWpIconHighlight(giWp);

        GridItemTypeBundle typeBundle = GridItem.filterByType(giWp.getItemList());
        agvPph.addAll(typeBundle.pph);
        agvPpn.addAll(typeBundle.ppn);
        agvPbb.addAll(typeBundle.pbb);
        agvBunga.addAll(typeBundle.bunga);
        agvTaxMisc.addAll(typeBundle.taxmisc);

        labelCategoryShow(giWp.itemList, typeBundle);
    }

    private void gridWpIconHighlight(GridItem gi){

        currentWpType.setSelected(false);
        currentWpType = gi;
        currentWpType.setSelected(true);
        agvWp.adapter.notifyDataSetChanged();
    }

    private void labelCategoryShow(List<GridItem> listItem, GridItemTypeBundle typeBundle){

        Set<String> setTaxtype = baseActivity.getSpStringSet(AppConstant.SP_RECENT_TAXTYPE);
        List<GridItem> listRecent = new ArrayList<>();
        for(String c : setTaxtype){
            String[] arr = c.split("_");
            String code = arr[0];
            for(GridItem gi : listItem){
                if(code.equals(gi.taxtype.code)){
                    listRecent.add(gi);
                    break;
                }
            }
        }
        agvRecent.addAll(listRecent);
        if(listRecent.isEmpty()) labelCategoryRecent.setVisibility(GONE);
        else labelCategoryRecent.setVisibility(VISIBLE);

//        getRecentList(new CommonCallback<List<GridItem>>() {
//            @Override
//            public void onSuccess(List<GridItem> data) {
//                agvRecent.addAll(data);
//
//                if(data.isEmpty()) labelCategoryRecent.setVisibility(GONE);
//                else labelCategoryRecent.setVisibility(VISIBLE);
//            }
//        });

        if( typeBundle.pph.isEmpty() ) labelCategoryPph.setVisibility(View.GONE);
        else labelCategoryPph.setVisibility(View.VISIBLE);

        if( typeBundle.ppn.isEmpty() ) labelCategoryPpn.setVisibility(View.GONE);
        else labelCategoryPpn.setVisibility(View.VISIBLE);

        if( typeBundle.pbb.isEmpty() ) labelCategoryPbb.setVisibility(View.GONE);
        else labelCategoryPbb.setVisibility(View.VISIBLE);

        if( typeBundle.bunga.isEmpty() ) labelCategoryBunga.setVisibility(View.GONE);
        else labelCategoryBunga.setVisibility(View.VISIBLE);

        if( typeBundle.taxmisc.isEmpty() ) labelCategoryTaxMisc.setVisibility(View.GONE);
        else labelCategoryTaxMisc.setVisibility(View.VISIBLE);
    }

    private void getRecentList(final CommonCallback<List<GridItem>> callback){

        baseActivity.getDataCacheList(AppConstant.SP_CACHEKEY_TAXTYPE, new CommonCallback<List<Taxtype>>() {
            @Override
            public void onSuccess(List<Taxtype> data) {
                Set<String> setTaxtype = baseActivity.getSpStringSet(AppConstant.SP_RECENT_TAXTYPE);
                List<GridItem> list = new ArrayList<>();
                GridItem gi;
                for(String c : setTaxtype){
                    String[] arr = c.split("_");
                    String code = arr[0];
                    for(Taxtype aTT : data){
                        if(code.equals(aTT.code)){
                            gi = new GridItem(aTT.fetchIcon(), aTT.fetchAlias(getResources()), aTT.fetchCategory());
                            gi.taxtype = aTT;
                            list.add(gi);
                            break;
                        }
                    }
                }
                callback.onSuccess(list);
            }
        });

    }

    public void addRecent(Taxtype tt){
        Set<String> recent = baseActivity.getSpStringSet(AppConstant.SP_RECENT_TAXTYPE);

        for(String s : recent){
            if( s.contains(tt.code+"_") ) return;  // avoid duplicate
        }

        if(recent.size() >= 3) {
            String old = null;
            long oldMs = 0;
            for(String s : recent){
                String[] arr = s.split("_");
                long ms = Long.parseLong(arr[1]);
                if(old == null){
                    old = s;
                    oldMs = ms;
                }else if(ms < oldMs){
                    old = s;
                    oldMs = ms;
                }
            }
            recent.remove(old);
        }
        recent.add(tt.code + "_" + System.currentTimeMillis());
        baseActivity.setSpStringSet(AppConstant.SP_RECENT_TAXTYPE, recent);
    }

    public int getWpType(){
        if(currentWpType == wpTypeUkm) return TaxtypeAlias.WPTYPE_UKM;
        return TaxtypeAlias.WPTYPE_ALL;
    }

}
