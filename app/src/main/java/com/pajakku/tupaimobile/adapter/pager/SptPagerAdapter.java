package com.pajakku.tupaimobile.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by dul on 17/12/18.
 */

public class SptPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> listFrag;

    public SptPagerAdapter(FragmentManager fm, List<Fragment> lf) {
        super(fm);
        listFrag = lf;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return listFrag.get(0);
            case 1:
                return listFrag.get(1);
            case 2:
                return listFrag.get(2);
            case 3:
                return listFrag.get(3);
            case 4:
                return listFrag.get(4);
            case 5:
                return listFrag.get(5);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return listFrag.size();
    }
}
