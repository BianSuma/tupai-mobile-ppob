package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.adapter.pager.ProfilePagerAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

//    public static final int VIEW_PAGER_CODE = 4;  // TODO: @warn show SPT
    public static final int VIEW_PAGER_CODE = 3;  // TODO: @warn hide SPT

    private ViewPager viewPager;
    private int toprightMenu = R.menu.fragprof_profile;
    private MainActivity mainActivity;

    private View invisView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        invisView = view.findViewById(R.id.fragprof_qhelp_invis);

        AppActionBar appActionBar = view.findViewById(R.id.fragprof_appactionbar);
        appActionBar.setRightMenu(new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return toprightMenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                switch(id){
                    case R.id.fragprof_btnmenu_quickhelp:
                        clickQuickHelp();
                        break;
                    case R.id.fragprof_toprightbtn_about:
                        Utility.showAbout(mainActivity);
                        break;
                    case R.id.fragprof_btnmenu_logout:
                        mainActivity.logoutClearCacheConfirm();
                        break;
                }

            }
        });

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.fragprof_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fragprof_label_tabidentity)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fragprof_label_wplist)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) view.findViewById(R.id.fragprof_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d(AppConstant.LOG_TAG, "---- on page scrolled");
            }
            @Override
            public void onPageSelected(int position) {
//                Log.d(AppConstant.LOG_TAG, "---- on page selected "+position);
                if( position == 1 ) toprightMenu = R.menu.fragprof_profile_quickhelp;
                else toprightMenu = R.menu.fragprof_profile;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d(AppConstant.LOG_TAG, "---- state change");
            }
        });
        final PagerAdapter adapter = new ProfilePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                invokeFragmentInit(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                invokeFragmentInit(tab.getPosition());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public void onBackPressed(){
        switch (viewPager.getCurrentItem()){
            case ProfileWPFragment.VIEW_PAGER_CODE:
                viewPager.setCurrentItem(ProfileHomeFragment.VIEW_PAGER_CODE);
                break;
        }
    }

    public int getCurrentFragmentIdx(){
        return viewPager.getCurrentItem();
    }

    public Fragment getFragment(int fragCode){
        for (Fragment f : getChildFragmentManager().getFragments()) {
            if (f instanceof ProfileHomeFragment && fragCode == ProfileHomeFragment.VIEW_PAGER_CODE) {
                return f;
            }else if (f instanceof ProfileWPFragment && fragCode == ProfileWPFragment.VIEW_PAGER_CODE) {
                return f;
            }
        }
        return null;
    }

    private void clickQuickHelp(){

        Fragment frag = getFragment(ProfileWPFragment.VIEW_PAGER_CODE);

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        if( frag != null ) {
            ProfileWPFragment fragWp = (ProfileWPFragment) frag;
            sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxwp_qhelptitle_add), getString(R.string.paytaxwp_qhelpbody_add), (View) fragWp.appCircleButtonAdd.getParent(), fragWp.appCircleButtonAdd);
            if (sc != null) {
                listSc.add(sc);
            }
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxwp_qhelptitle_wplist), getString(R.string.paytaxwp_qhelpbody_wplist), (View)invisView.getParent(), invisView);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }
}
