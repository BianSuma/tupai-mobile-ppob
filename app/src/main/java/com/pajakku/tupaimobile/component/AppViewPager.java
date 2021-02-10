package com.pajakku.tupaimobile.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.pajakku.tupaimobile.adapter.AppPagerAdapter;

import java.util.List;

public class AppViewPager extends ViewPager {
    public final static int TYPE_SWIPEABLE = 1;
    public final static int TYPE_NONSWIPEABLE = 2;
    private AppPagerAdapter adapter;
    private int type;
    public AppViewPager(Context ctx, AttributeSet attrs){
        super(ctx, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (type){
            case TYPE_NONSWIPEABLE:
                return false;
        }
//        return false;
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(type == TYPE_NONSWIPEABLE) return false;

        performClick();
//        return true;
        return super.onTouchEvent(event);
    }
//
    @Override
    public boolean performClick() {
        return super.performClick();
//        return true;
    }

    public void setSwipeable(){
        type = TYPE_SWIPEABLE;
    }
    public void setNonswipeable(){
        type = TYPE_NONSWIPEABLE;
    }

    public void init(FragmentManager fm, List<Fragment> fl){
        adapter = new AppPagerAdapter(fm, fl);
        setAdapter(adapter);

        setNonswipeable();
    }

    public int getCount(){
        return adapter.getCount();
    }

    public Fragment getItem(int pos){
        if( adapter.getCount() <= pos ) return null;
        return adapter.getItem(pos);
    }

//    public void add(Fragment f){
//        adapter.add(f);
//    }
//
//    public void popFrag(){
//        adapter.popFrag();
//    }
}
