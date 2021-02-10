package com.pajakku.tupaimobile.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by dul on 17/12/18.
 */

public class HelpPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragList;

    public HelpPagerAdapter(FragmentManager fm, List<Fragment> fl) {
        super(fm);
        fragList = fl;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragList.get(0);
            case 1:
                return fragList.get(1);
            case 2:
                return fragList.get(2);
//            case HelpActivateMCashFragment.VIEW_PAGER_CODE:
//                return new HelpActivateMCashFragment();

//            case HelpCallUsFragment.VIEW_PAGER_CODE:
//                return new HelpCallUsFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragList.size();
    }
}
