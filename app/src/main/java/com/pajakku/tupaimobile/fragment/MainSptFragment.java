package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.activity.SptActivity;
import com.pajakku.tupaimobile.activity.SptDetailActivity;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.model.Spt;
import com.pajakku.tupaimobile.model.SptType;
import com.pajakku.tupaimobile.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

//import com.tech.freak.wizardpager.ui.StepPagerStrip;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MainSptFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 2;

    public ViewPager viewPager;
    private MainActivity mainActivity;
    public AppCircleButton fabAdd;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppRecyclerView appRecyclerView;

    private View invisView;

    public MainSptFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mainspt, container, false);

//        invisView = view.findViewById(R.id.mainspt_qhelp_invis);
//
//        AppActionBar appActionBar = view.findViewById(R.id.mainspt_appactionbar);
//        appActionBar.setRightMenu(new AppActionBar.AppListener() {
//            @Override
//            public int menuResource() {
//                return R.menu.mainspt_toprightmenu;
//            }
//
//            @Override
//            public void onClickRightMenu(int id) {
//                clickRightMenu(id);
//            }
//        });

        swipeRefreshLayout = v.findViewById(R.id.mainspt_swipyrefreshlayout);

        appRecyclerView = v.findViewById(R.id.mainspt_list_spt);
        appRecyclerView.init(true, swipeRefreshLayout, new AppRecyclerView.AppCallback<Spt>() {
            @Override
            public void onClick(Spt d) {
                clickItem(d);
            }

            @Override
            public void onRefresh() {
//                mainActivity.resetFilterViewparam(FilterView.LISTTYPE_WP);
//                RequestParamConfig r = new RequestParamConfig();
//                fetchData(1, new FilterParam(), r);
            }

            @Override
            public void onLoadMore(long nextPage) {
//                FilterParam fdp = mainActivity.getFilterViewParam(FilterView.LISTTYPE_WP);
//                RequestParamConfig rpc = new RequestParamConfig();
//                rpc.isCache = false;
//                mainActivity.requestWajibpajak(nextPage, fdp, true, rpc, new SuccessFailCallback<List<Wajibpajak>, ResponseError>() {
//                    @Override
//                    public void onSuccess(List<Wajibpajak> data) {
//                        appRecyclerView.loadMoreAddItem(data);
//                    }
//
//                    @Override
//                    public boolean onFail(ResponseError data) {
//                        appRecyclerView.stopLoadMore();
//                        return true;
//                    }
//                });
                appRecyclerView.stopLoadMore();
            }
        });

        fabAdd =  v.findViewById(R.id.mainspt_fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickShowSptActivity();
            }
        });

        return v;
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

    @Override
    public void onResume() {
        super.onResume();

        // TODO: @test
        List<Spt> items = new ArrayList<>();
        Spt spt;
        for(int i=1; i<6;i++){
            spt = new Spt();
            switch (i%3){
//                case 1:
//                    spt.sptTypeCode = SptType.TYPE_23_26;
//                    break;
//                case 2:
//                    spt.sptTypeCode = SptType.TYPE_4_2;
//                    break;
                default:
                    spt.sptTypeCode = SptType.TYPE_1770;
            }
            spt.wpName = "Andi Sugandi";
            spt.npwp = ("01345678901111"+i);
//            switch (i){
//                case 1:
//                    spt.taxInfo = "Jan";
//                    break;
//                case 2:
//                    spt.taxInfo = "Feb";
//                    break;
//                case 3:
//                    spt.taxInfo = "Mar";
//                    break;
//                case 4:
//                    spt.taxInfo = "Apr";
//                    break;
//                default:
//                    spt.taxInfo = "Mei";
//                    break;
//            }
//            spt.taxInfo = ("PPh OP "+spt.taxInfo+" 2018");
            spt.npwp = "12345678905555"+i;
            spt.amount = 2000;
            spt.pembetulan = (i % 2);
            spt.year = 2004+i;
            spt.ntte = null;
            switch ( i % 3 ){
                case 1:
                    spt.status = Spt.STATUS_SUBMIT;
                    break;
                case 2:
                    spt.status = Spt.STATUS_DONE;
                    break;
                default:
                    spt.status = Spt.STATUS_DRAFT;
                    break;
            }
            items.add(spt);
        }
        appRecyclerView.updateListView( items );

    }

//    public Fragment getFragment(int fragCode){
//        for (Fragment f : getChildFragmentManager().getFragments()) {
//            if (f instanceof SSPUnpaidFragment && fragCode == SSPUnpaidFragment.VIEW_PAGER_CODE) {
//                return f;
//            }
//            if (f instanceof SSPDoneFragment && fragCode == SSPDoneFragment.VIEW_PAGER_CODE) {
//                return f;
//            }
//        }
//        return null;
//    }

//    private void clickRightMenu(int id){
//        switch (id){
//            case R.id.mainspt_btnmenu_quickhelp:
//                clickQuickHelp();
//                break;
//            case R.id.mainspt_btnmenu_logout:
//                mainActivity.logoutClearCacheConfirm();
//                break;
//        }
//    }

    private void clickShowSptActivity(){
        Intent itn = new Intent(getContext(), SptActivity.class);
        getActivity().startActivity(itn);
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

//        sc = Utility.createShowCase(getActivity(), getString(R.string.mainspt_qhelptitle_sspcategory), getString(R.string.mainspt_qhelpbody_sspcategory), (View)tabLayout.getParent(), tabLayout);
//        if(sc != null) listSc.add(sc);

//        sc = Utility.createShowCase(getActivity(), getString(R.string.mainspt_qhelptitle_ssplist), getString(R.string.mainspt_qhelpbody_ssplist), (View)invisView.getParent(), invisView);
//        if (sc != null){
//            listSc.add(sc);
//        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    private void clickItem(Spt spt){
        Intent i = new Intent(mainActivity, SptDetailActivity.class);
        i.putExtra(AppConstant.ITN_SPTDETAIL_SPT, spt);
        mainActivity.startActivity(i);
    }
}
