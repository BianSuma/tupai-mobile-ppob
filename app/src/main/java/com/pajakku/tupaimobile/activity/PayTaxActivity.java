package com.pajakku.tupaimobile.activity;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.pager.PayPPhPagerAdapter;
import com.pajakku.tupaimobile.component.FilterView;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.fragment.PayTax0KJSFragment;
import com.pajakku.tupaimobile.fragment.PayTax2PeriodFragment;
import com.pajakku.tupaimobile.fragment.PayTax3SetorFragment;
import com.pajakku.tupaimobile.fragment.PayTax4SkNopFragment;
import com.pajakku.tupaimobile.fragment.PayTax5SummaryFragment;
import com.pajakku.tupaimobile.fragment.PayTax1WPFragment;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.model.dto.TaxtypeAlias;
import com.pajakku.tupaimobile.model.dto.WrappedSsp;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;

public class PayTaxActivity extends RepositoryActivity {

    private final int GOTO_FRAGMENT_SETOR = 1;
    private final int GOTO_FRAGMENT_SUMMARY = 2;

    public WrappedSsp wrappedSsp;
    public NonSwipeableViewPager pager;
    private FilterView filterView;
    private int prevFragmentIdx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tax);

        pager = findViewById(R.id.paytax_pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                int gotoFragment = 0;
//                if(position == PayTax4SkNopFragment.VIEW_PAGER_CODE){
//                    if(prevFragmentIdx == PayTax5SummaryFragment.VIEW_PAGER_CODE){
//                        gotoFragment = GOTO_FRAGMENT_SETOR;
//                    }
//                    if(prevFragmentIdx == PayTax3SetorFragment.VIEW_PAGER_CODE){
//                        gotoFragment = GOTO_FRAGMENT_SUMMARY;
//                    }
//                }
//                prevFragmentIdx = position;
//
//                PayTax4SkNopFragment frag = (PayTax4SkNopFragment)getFragment(PayTax4SkNopFragment.VIEW_PAGER_CODE);
//
//                if( frag != null && !frag.isInputShow() ) {
//                    switch (gotoFragment) {
//                        case GOTO_FRAGMENT_SETOR:
//                            pager.setCurrentItem(PayTax3SetorFragment.VIEW_PAGER_CODE);
//                            break;
//                        case GOTO_FRAGMENT_SUMMARY:
//                            frag.clickNext();
//                            break;
//                    }
//                }

                switch (position){
                    case PayTax1WPFragment.VIEW_PAGER_CODE:
                        PayTax1WPFragment frag1 = (PayTax1WPFragment) getFragment(position);
                        if(frag1 != null) frag1.showQuickHelp();
                        break;
                    case PayTax3SetorFragment.VIEW_PAGER_CODE:
                        PayTax3SetorFragment frag3 = (PayTax3SetorFragment)getFragment(position);
                        if(frag3 != null) frag3.showQuickHelp();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        PagerAdapter adapter = new PayPPhPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);


        if(savedInstanceState == null) {
            Intent itn = getIntent();
            int wpType = itn.getIntExtra(AppConstant.ITN_FRAGHOMEPAYTAX_WPTYPE, TaxtypeAlias.WPTYPE_ALL);
            Taxtype taxtype = (Taxtype) itn.getSerializableExtra(AppConstant.ITN_FRAGHOMEPAYTAX_TAXTYPE);

            wrappedSsp = new WrappedSsp();
            wrappedSsp.taxType = taxtype;
            wrappedSsp.wpType = wpType;
            wrappedSsp.isSave = false;
        }else{
            wrappedSsp = (WrappedSsp) savedInstanceState.getSerializable(AppConstant.SP_PAYTAX_REQUESTSSP);
        }

        filterView = findViewById(R.id.paytax_filterview);

    }


    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(AppConstant.SP_PAYTAX_REQUESTSSP, wrappedSsp);
    }

    @Override
    public void onBackPressed() {
        Kjs kjs = wrappedSsp.taxSlipType;
        switch ( pager.getCurrentItem() ){
            case PayTax1WPFragment.VIEW_PAGER_CODE:
                if(filterView.getVisibility() == View.VISIBLE){
                    filterView.hide();
                }else {
                    pager.setCurrentItem(PayTax0KJSFragment.VIEW_PAGER_CODE);
                }
                break;
            case PayTax2PeriodFragment.VIEW_PAGER_CODE:
                pager.setCurrentItem(PayTax1WPFragment.VIEW_PAGER_CODE);
                break;
            case PayTax3SetorFragment.VIEW_PAGER_CODE:
                pager.setCurrentItem(PayTax2PeriodFragment.VIEW_PAGER_CODE);
                break;
            case PayTax4SkNopFragment.VIEW_PAGER_CODE:
                pager.setCurrentItem(PayTax3SetorFragment.VIEW_PAGER_CODE);
                break;
            case PayTax5SummaryFragment.VIEW_PAGER_CODE:
                if(kjs.noSkActive || kjs.nopActive || kjs.subjekPajakActive) {
                    pager.setCurrentItem(PayTax4SkNopFragment.VIEW_PAGER_CODE);
                }else{
                    pager.setCurrentItem(PayTax3SetorFragment.VIEW_PAGER_CODE);
                }
                break;
            default:  // at KJS fragment
                super.onBackPressed();
        }
    }


    public void invokeFragmentInit(int id){
        for ( Fragment f : getSupportFragmentManager().getFragments() ){
            if(id == PayTax0KJSFragment.VIEW_PAGER_CODE && f instanceof PayTax0KJSFragment){
                ((PayTax0KJSFragment)f).init();
            }
            if(id == PayTax1WPFragment.VIEW_PAGER_CODE && f instanceof PayTax1WPFragment){
                ((PayTax1WPFragment)f).init();
            }
            if(id == PayTax2PeriodFragment.VIEW_PAGER_CODE && f instanceof PayTax2PeriodFragment){
                ((PayTax2PeriodFragment)f).init();
            }
            if(id == PayTax3SetorFragment.VIEW_PAGER_CODE && f instanceof PayTax3SetorFragment){
                ((PayTax3SetorFragment)f).init();
            }
            if(id == PayTax4SkNopFragment.VIEW_PAGER_CODE && f instanceof PayTax4SkNopFragment){
                ((PayTax4SkNopFragment)f).init();
            }
            if(id == PayTax5SummaryFragment.VIEW_PAGER_CODE && f instanceof PayTax5SummaryFragment){
                ((PayTax5SummaryFragment)f).init();
            }
        }
    }

    public Fragment getFragment(int id){
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f instanceof PayTax0KJSFragment) {
                if( PayTax0KJSFragment.VIEW_PAGER_CODE == id ) return f;
            }else if ( f instanceof PayTax1WPFragment) {
                if( PayTax1WPFragment.VIEW_PAGER_CODE == id ) return f;
            }else if ( f instanceof PayTax3SetorFragment) {
                if( PayTax3SetorFragment.VIEW_PAGER_CODE == id ) return f;
            }else if ( f instanceof PayTax4SkNopFragment) {
                if( PayTax4SkNopFragment.VIEW_PAGER_CODE == id ) return f;
            }
        }
        return null;
    }

    public void resetFilterViewparam(int type){
        switch (type) {
            case FilterView.LISTTYPE_SSPDONE:
                filterView.paramFindSspDone = new FilterParam();
                break;
            case FilterView.LISTTYPE_WP:
                filterView.paramFindWp = new FilterParam();
                break;
            default:
                filterView.paramFindSspUnpaid = new FilterParam();
                break;
        }
    }

    public FilterParam getFilterViewParam(int listType){
        switch (listType){
            case FilterView.LISTTYPE_SSPDONE: return filterView.paramFindSspDone;
            case FilterView.LISTTYPE_WP: return filterView.paramFindWp;
        }
        return filterView.paramFindSspUnpaid;
    }

    public void setFilterViewCallback(int listType, CommonCallback<FilterParam> callback){
        filterView.setCallback(listType, callback);
        filterView.show();
        switch (listType){
            case FilterView.LISTTYPE_WP:
                filterView.setModeFindWp();
                break;
            default:
                filterView.setModeFind();
                break;
        }
    }

    public void setFilterViewSort(int listType, CommonCallback<FilterParam> callback){
        filterView.setCallbackSort(listType, callback);
        filterView.show();
        switch (listType){
            case FilterView.LISTTYPE_WP:
                filterView.setModeSortWp();
                break;
            default:
                filterView.setModeSort();
                break;
        }
    }
}
