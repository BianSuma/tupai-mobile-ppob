package com.pajakku.tupaimobile.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by dul on 17/12/18.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragList;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> fl) {
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
            case 3:
                return fragList.get(3);
            case 4:
                return fragList.get(4);
            default:
                return null;
        }
//        switch (position) {
//            case HomeFragment.VIEW_PAGER_CODE:
//                return new HomeFragment();
//            case SSPFragment.VIEW_PAGER_CODE:
//                return new SSPFragment();
//            case MainSptFragment.VIEW_PAGER_CODE:
//                return new MainSptFragment();
//            case HelpFragment.VIEW_PAGER_CODE:
//                return new HelpFragment();
//            case ProfileFragment.VIEW_PAGER_CODE:
//                return new ProfileFragment();
//            default:
//                return null;
//        }
    }

    @Override
    public int getCount() {
        return fragList.size();
    }
}
