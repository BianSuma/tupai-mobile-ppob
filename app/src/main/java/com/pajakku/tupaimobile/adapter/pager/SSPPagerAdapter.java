package com.pajakku.tupaimobile.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pajakku.tupaimobile.fragment.SSPDoneFragment;
import com.pajakku.tupaimobile.fragment.SSPUnpaidFragment;

/**
 * Created by dul on 17/12/18.
 */

public class SSPPagerAdapter extends FragmentStatePagerAdapter {

    public SSPPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case SSPUnpaidFragment.VIEW_PAGER_CODE:
                return new SSPUnpaidFragment();
            case SSPDoneFragment.VIEW_PAGER_CODE:
                return new SSPDoneFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return SSPDoneFragment.VIEW_PAGER_CODE + 1;
    }
}
