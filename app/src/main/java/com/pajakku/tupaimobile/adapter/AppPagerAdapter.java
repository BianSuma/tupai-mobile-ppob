package com.pajakku.tupaimobile.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class AppPagerAdapter extends FragmentStatePagerAdapter {
    public List<Fragment> fragList;
    private FragmentManager fm;

    public AppPagerAdapter(FragmentManager fm, List<Fragment> fl) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fm = fm;
        fragList = fl;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

}
