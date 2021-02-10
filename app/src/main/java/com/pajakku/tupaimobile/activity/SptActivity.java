package com.pajakku.tupaimobile.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.pager.SptPagerAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.fragment.Spt1IntroFragment;
import com.pajakku.tupaimobile.fragment.Spt0SptTypeFragment;
import com.pajakku.tupaimobile.fragment.Spt2WpFragment;
import com.pajakku.tupaimobile.fragment.Spt3YearFragment;
import com.pajakku.tupaimobile.fragment.Spt4WpExtraFragment;
import com.pajakku.tupaimobile.fragment.Spt5PeredaranBrutoFragment;
import com.pajakku.tupaimobile.model.Spt;
import com.pajakku.tupaimobile.model.SptDraft;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.Utility;
import com.tech.freak.wizardpager.ui.StepPagerStrip;

import java.util.ArrayList;
import java.util.List;

public class SptActivity extends RepositoryActivity {

    public ViewPager viewPager;
    public SptDraft sptDraft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spt);

        if(savedInstanceState == null) {
//            SptType st = new SptType();
//            st.info = 0;
//
//            Wajibpajak wp = new Wajibpajak();
//            wp.npwp = "";
//            wp.name = "";

            sptDraft = new SptDraft();
            sptDraft.status = Spt.STATUS_DRAFT;
            sptDraft.sptTypeCode = "";
            sptDraft.npwp = "";
            sptDraft.wpName = "";
            sptDraft.year = Utility.currentYear() - 1;
            sptDraft.pembetulan = 0;
            sptDraft.workType = getSpString(AppConstant.SP_AVAL_WORKTYPE);
            sptDraft.klu = getSpString(AppConstant.SP_AVAL_KLU);
            sptDraft.pasutriStatus = getSpString(AppConstant.SP_AVAL_PASUTRISTATUS);
            sptDraft.pasutriNpwp = getSpString(AppConstant.SP_AVAL_PASUTRINPWP);
        }else{
            sptDraft = (SptDraft) savedInstanceState.getSerializable(AppConstant.SP_SPT_SPTDRAFT);
        }

        AppActionBar appActionBar = findViewById(R.id.activityspt_appactionbar);
        appActionBar.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTopLeft();
            }
        });

        List<Fragment> listFrag = new ArrayList<>();
        listFrag.add( new Spt0SptTypeFragment() );
        listFrag.add( new Spt1IntroFragment() );
        listFrag.add( new Spt2WpFragment() );
        listFrag.add( new Spt3YearFragment() );
        listFrag.add( new Spt4WpExtraFragment() );
        listFrag.add( new Spt5PeredaranBrutoFragment() );

        final StepPagerStrip strip = findViewById(R.id.activityspt_steppagerstrip);
        strip.setPageCount(listFrag.size());

        viewPager = (ViewPager) findViewById(R.id.activityspt_pager);
        PagerAdapter adapter = new SptPagerAdapter(getSupportFragmentManager(), listFrag);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                strip.setCurrentPage(position);
                invokeSetViewData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(AppConstant.SP_SPT_SPTDRAFT, sptDraft);
    }

    @Override
    public void onBackPressed() {
        clickTopLeft();
    }

    public void invokeSetViewData(int id){
        Fragment f = getFragment(id);
        if(f == null) return;
        switch (id){
            case Spt0SptTypeFragment.VIEW_PAGER_CODE:
                ((Spt0SptTypeFragment)f).setViewData();
                break;
            case Spt1IntroFragment.VIEW_PAGER_CODE:
                ((Spt1IntroFragment)f).setViewData();
                break;
//            case Spt2WpFragment.VIEW_PAGER_CODE:
//                ((Spt2WpFragment)f).setViewData();
//                break;
            case Spt3YearFragment.VIEW_PAGER_CODE:
                ((Spt3YearFragment)f).setViewData();
                break;
            case Spt4WpExtraFragment.VIEW_PAGER_CODE:
                ((Spt4WpExtraFragment)f).setViewData();
                break;
        }
    }

    public Fragment getFragment(int id){
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f instanceof Spt0SptTypeFragment) {
                if( Spt0SptTypeFragment.VIEW_PAGER_CODE == id ) return f;
            }else if ( f instanceof Spt1IntroFragment) {
                if( Spt1IntroFragment.VIEW_PAGER_CODE == id ) return f;
            }else if ( f instanceof Spt2WpFragment) {
                if( Spt2WpFragment.VIEW_PAGER_CODE == id ) return f;
            }else if ( f instanceof Spt3YearFragment) {
                if( Spt3YearFragment.VIEW_PAGER_CODE == id ) return f;
            }else if ( f instanceof Spt4WpExtraFragment) {
                if( Spt4WpExtraFragment.VIEW_PAGER_CODE == id ) return f;
            }
        }
        return null;
    }

    private void clickTopLeft(){
        switch (viewPager.getCurrentItem()){
            case 1:
                viewPager.setCurrentItem(0);
                break;
            case 2:
                viewPager.setCurrentItem(1);
                break;
            case 3:
                viewPager.setCurrentItem(2);
                break;
            case 4:
                viewPager.setCurrentItem(3);
                break;
            case 5:
                viewPager.setCurrentItem(4);
                break;
            default:
                finish();
        }

    }
}
