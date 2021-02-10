package com.pajakku.tupaimobile.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pajakku.tupaimobile.fragment.PayTax0KJSFragment;
import com.pajakku.tupaimobile.fragment.PayPPhMethodFragment;
import com.pajakku.tupaimobile.fragment.PayTax3SetorFragment;
import com.pajakku.tupaimobile.fragment.PayTax4SkNopFragment;
import com.pajakku.tupaimobile.fragment.PayPPhResultFragment;
import com.pajakku.tupaimobile.fragment.PayTax5SummaryFragment;
import com.pajakku.tupaimobile.fragment.PayTax2PeriodFragment;
import com.pajakku.tupaimobile.fragment.PayTax1WPFragment;

/**
 * Created by dul on 17/12/18.
 */

public class PayPPhPagerAdapter extends FragmentStatePagerAdapter {
//    private int mNumOfTabs;
//    private ViewPager viewPager;

    public PayPPhPagerAdapter(FragmentManager fm) {
        super(fm);
//        this.mNumOfTabs = NumOfTabs;
//        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PayTax0KJSFragment.VIEW_PAGER_CODE:
                return new PayTax0KJSFragment();
            case PayTax1WPFragment.VIEW_PAGER_CODE:
                return new PayTax1WPFragment();
            case PayTax2PeriodFragment.VIEW_PAGER_CODE:
                return new PayTax2PeriodFragment();
            case PayTax3SetorFragment.VIEW_PAGER_CODE:
                return new PayTax3SetorFragment();
            case PayTax4SkNopFragment.VIEW_PAGER_CODE:
                return new PayTax4SkNopFragment();
            case PayTax5SummaryFragment.VIEW_PAGER_CODE:
                return new PayTax5SummaryFragment();
            case PayPPhMethodFragment.VIEW_PAGER_CODE:
                return new PayPPhMethodFragment();
            case PayPPhResultFragment.VIEW_PAGER_CODE:
                return new PayPPhResultFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PayPPhResultFragment.VIEW_PAGER_CODE + 1;
    }
}
