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
import com.pajakku.tupaimobile.adapter.pager.SSPPagerAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class SSPFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 1;

    private MainActivity mainActivity;

    public ViewPager viewPager;
    private TabLayout tabLayout;
    private View invisView;
    private int toprightMenu = R.menu.fragssp_rightmenu_qhelp;

    public SSPFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ssp, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.fragssp_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fragssp_btn_unfinish)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fragssp_btn_finish)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        invisView = view.findViewById(R.id.fragssp_qhelp_invis);

        AppActionBar appActionBar = view.findViewById(R.id.fragssp_appactionbar);
        appActionBar.setRightMenu(new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return toprightMenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                switch (id){
                    case R.id.fragssp_btnmenu_quickhelp:
                        clickQuickHelp();
                        break;
                    case R.id.fragssp_btnmenu_logout:
                        mainActivity.logoutClearCacheConfirm();
                        break;
                }
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.fragssp_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) toprightMenu = R.menu.fragssp_rightmenu_qhelp;
                else toprightMenu = R.menu.fragssp_rightmenu;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final PagerAdapter adapter = new SSPPagerAdapter(getChildFragmentManager());
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
                    + " must implement MainActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public Fragment getFragment(int fragCode){
        for (Fragment f : getChildFragmentManager().getFragments()) {
            if (f instanceof SSPUnpaidFragment && fragCode == SSPUnpaidFragment.VIEW_PAGER_CODE) {
                return f;
            }
            if (f instanceof SSPDoneFragment && fragCode == SSPDoneFragment.VIEW_PAGER_CODE) {
                return f;
            }
        }
        return null;
    }

    public void showQuickHelp(){
        String sp = AppConstant.SP_QH_SSPLIST;
        boolean b = mainActivity.getSpBool(sp);
        if(b) return;
        clickQuickHelp();
        mainActivity.setSpBool(sp);
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();

        BubbleShowCaseBuilder sc = Utility.createShowCase(getActivity(), getString(R.string.fragssp_qhelptitle_sspcategory), getString(R.string.fragssp_qhelpbody_sspcategory), (View)tabLayout.getParent(), tabLayout);
        if(sc != null) listSc.add(sc);

        sc = Utility.createShowCase(getActivity(), getString(R.string.fragssp_qhelptitle_ssplist), getString(R.string.fragssp_qhelpbody_ssplist), (View)invisView.getParent(), invisView);
        if (sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }
}
