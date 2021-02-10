package com.pajakku.tupaimobile.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pajakku.tupaimobile.fragment.ProfileHomeFragment;
import com.pajakku.tupaimobile.fragment.ProfileWPFragment;

/**
 * Created by dul on 17/12/18.
 */

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {
//    private int mNumOfTabs;
//    private ViewPager viewPager;

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
//        this.mNumOfTabs = NumOfTabs;
//        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ProfileHomeFragment.VIEW_PAGER_CODE:
                return new ProfileHomeFragment();
            case ProfileWPFragment.VIEW_PAGER_CODE:
                return new ProfileWPFragment();
//            case ProfileDisclaimerFragment.VIEW_PAGER_CODE:
//                return new ProfileDisclaimerFragment();
//            case ProfilePolicyFragment.VIEW_PAGER_CODE:
//                return new ProfilePolicyFragment();
//            case ProfileSKFragment.VIEW_PAGER_CODE:
//                return new ProfileSKFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
//        return ProfileSKFragment.VIEW_PAGER_CODE + 1;
        return ProfileWPFragment.VIEW_PAGER_CODE + 1;
    }
}
