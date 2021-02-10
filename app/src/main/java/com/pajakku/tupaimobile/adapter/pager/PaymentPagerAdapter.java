package com.pajakku.tupaimobile.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pajakku.tupaimobile.fragment.PaymentCCFragment;
import com.pajakku.tupaimobile.fragment.PaymentGuideFragment;
import com.pajakku.tupaimobile.fragment.PaymentMethodFragment;
import com.pajakku.tupaimobile.fragment.PaymentResultFragment;

/**
 * Created by dul on 17/12/18.
 */

public class PaymentPagerAdapter extends FragmentStatePagerAdapter {
//    private int mNumOfTabs;
//    private ViewPager viewPager;

    public PaymentPagerAdapter(FragmentManager fm) {
        super(fm);
//        this.mNumOfTabs = NumOfTabs;
//        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PaymentMethodFragment.VIEW_PAGER_CODE:
                return new PaymentMethodFragment();
            case PaymentGuideFragment.VIEW_PAGER_CODE:
                return new PaymentGuideFragment();
            case PaymentCCFragment.VIEW_PAGER_CODE:
                return new PaymentCCFragment();
            case PaymentResultFragment.VIEW_PAGER_CODE:
                return new PaymentResultFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PaymentResultFragment.VIEW_PAGER_CODE + 1;
    }
}
