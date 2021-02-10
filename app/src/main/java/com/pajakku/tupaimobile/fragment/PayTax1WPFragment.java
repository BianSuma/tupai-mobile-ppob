package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.AddEditWpActivity;
import com.pajakku.tupaimobile.activity.PayTaxActivity;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.component.FilterView;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.WrappedSsp;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SuccessFailCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayTax1WPFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private PayTaxActivity payTaxActivity;
    private TaxWPHeader taxWPHeader;
    private AppRecyclerView appRecyclerView;

    private AppCircleButton appCircleBtnAdd;
    private View invisView;

    public PayTax1WPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paytax1_wp, container, false);

        invisView = view.findViewById(R.id.paytaxwp_qhelp_invis);

        final NonSwipeableViewPager pager = getActivity().findViewById(R.id.paytax_pager);

        AppActionBar actionBar = view.findViewById(R.id.paytaxwp_appactionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PayTax0KJSFragment.VIEW_PAGER_CODE);
            }
        }, new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.paytaxwp_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                clickRightMenu(id);
            }
        });

        if((taxWPHeader = view.findViewById(R.id.paytaxwp_taxwpheader)) == null) throw new RuntimeException();

        swipeRefreshLayout = view.findViewById(R.id.paytaxwp_swipyrefreshlayout);

        appRecyclerView = view.findViewById(R.id.paypphwp_listview);
        appRecyclerView.init(true, swipeRefreshLayout, new AppRecyclerView.AppCallback<Wajibpajak>() {
            @Override
            public void onClick(Wajibpajak d) {
                clickWPItem(d);
            }

            @Override
            public void onRefresh() {
                payTaxActivity.resetFilterViewparam(FilterView.LISTTYPE_WP);
                RequestParamConfig rpc = new RequestParamConfig();
                rpc.isRelogin = true;
                fetchData(1, new FilterParam(), rpc);
            }

            @Override
            public void onLoadMore(long nextPage) {
                FilterParam fdp = payTaxActivity.getFilterViewParam(FilterView.LISTTYPE_WP);
                RequestParamConfig rpc = new RequestParamConfig();
                rpc.isRelogin = false;
                rpc.isCache = false;

                rpc.reqPaging.page = nextPage;
                rpc.reqPaging.field = fdp.field;
                rpc.reqPaging.query = fdp.query;
                rpc.reqPaging.order = fdp.column;
                ApiMain.requestWPList(payTaxActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Wajibpajak>, ResponseDTO>() {
                    @Override
                    public void onSuccess(List<Wajibpajak> data) {
                        appRecyclerView.loadMoreAddItem(data);
                    }

                    @Override
                    public void onFail(ResponseDTO data) {
                        appRecyclerView.stopLoadMore();
                    }
                });
//                payTaxActivity.requestWPList(nextPage, fdp, rpc, new SuccessFailCallback<List<Wajibpajak>, ResponseError>() {
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
            }
        });

        appCircleBtnAdd =  view.findViewById(R.id.paytaxwp_circlebtn_add);
        appCircleBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFabNewWp();
            }
        });

        AppCircleButton appCircleButton = view.findViewById(R.id.paytaxwp_circlebtn_find);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payTaxActivity.setFilterViewCallback(FilterView.LISTTYPE_WP, new CommonCallback<FilterParam>() {
                    @Override
                    public void onSuccess(FilterParam data) {
                        RequestParamConfig rpc = new RequestParamConfig();
                        rpc.isRelogin =  true;
                        fetchData(1, data, rpc);
                    }
                });
            }
        });

        appCircleButton = view.findViewById(R.id.paytaxwp_circlebtn_sort);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payTaxActivity.setFilterViewSort(FilterView.LISTTYPE_WP, new CommonCallback<FilterParam>() {
                    @Override
                    public void onSuccess(FilterParam data) {
                        RequestParamConfig rpc = new RequestParamConfig();
                        rpc.isRelogin = true;
                        fetchData(1, data, rpc);
                    }
                });
            }
        });

        init();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PayTaxActivity) {
            payTaxActivity = (PayTaxActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PayTaxActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        payTaxActivity = null;
    }

    @Override
    public void onResume(){
        super.onResume();

        // TODO: @prod
        RequestParamConfig reqParam = new RequestParamConfig();
        reqParam.forceRequest = false;
        fetchData(1, new FilterParam(), reqParam);

        // TODO: @test
//            List<Wajibpajak> wps = new ArrayList<>();
//            Wajibpajak org = new Wajibpajak();
//            org.name = "PT Pajakku";
//            org.npwp = "123456789055555";
//            org.address = "Jln. Cigadung Bandung";
//            org.city = "Bandung";
//            wps.add(org);
//            payTaxActivity.setCache(AppConstant.SP_CACHEKEY_WPLIST, wps);
//            appRecyclerView.updateListView(wps);

    }

    public void init(){

        WrappedSsp wrappedSsp = payTaxActivity.wrappedSsp;

        taxWPHeader.setIcon(wrappedSsp.fetchDefaultTaxType().fetchIcon(), wrappedSsp.fetchDefaultTaxType().code);
        taxWPHeader.setHeadName(wrappedSsp.fetchDefaultTaxType().name);
        taxWPHeader.setKapKjs(wrappedSsp.fetchDefaultTaxType(), wrappedSsp.fetchDefaultTaxSlipType());

    }

    public void showProgressCircle(){
        swipeRefreshLayout.setRefreshing(true);
    }
    public void hideProgressCircle(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public void updateListView(List<Wajibpajak> d){
        appRecyclerView.updateListView(d);
    }

    private void fetchData(long page, FilterParam fdp, final RequestParamConfig rpc){
        final PayTax1WPFragment frag = this;
        if(frag.isAdded()) swipeRefreshLayout.setRefreshing(true);

        rpc.reqPaging.page = page;
        rpc.reqPaging.field = fdp.field;
        rpc.reqPaging.query = fdp.query;
        rpc.reqPaging.order = fdp.column;
        ApiMain.requestWPList(payTaxActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Wajibpajak>, ResponseDTO>() {
            @Override
            public void onSuccess(List<Wajibpajak> data) {
                if(!frag.isAdded()) return;
                appRecyclerView.updateListView(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail(ResponseDTO data) {
                if(frag.isAdded()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
//        payTaxActivity.requestWPList(page, filterParam, reqParam, new SuccessFailCallback<List<Wajibpajak>, ResponseError>() {
//            @Override
//            public void onSuccess(List<Wajibpajak> data) {
//                if(!frag.isAdded()) return;
//                appRecyclerView.updateListView(data);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public boolean onFail(ResponseError data) {
//                if(frag.isAdded()){
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//                return reqParam.isShowFailToast;
//            }
//        });
    }

    private void clickRightMenu(int id){
        switch (id){
            case R.id.paytaxwp_btnmenu_quickhelp:
                clickQuickHelp();
                break;
        }
    }


    public void showQuickHelp(){
        if(payTaxActivity.pager.getCurrentItem() != PayTax1WPFragment.VIEW_PAGER_CODE) return;
        String sp = AppConstant.SP_QH_WP;
        boolean b = payTaxActivity.getSpBool(sp);
        if(b) return;
        clickQuickHelp();
        payTaxActivity.setSpBool(sp);
    }

    private void clickQuickHelp(){

        BubbleShowCaseBuilder sc;
        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();

        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxwp_qhelptitle_add), getString(R.string.paytaxwp_qhelpbody_add), (View)appCircleBtnAdd.getParent(), appCircleBtnAdd);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxwp_qhelptitle_wplist), getString(R.string.paytaxwp_qhelpbody_wplist), (View)invisView.getParent(), invisView);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    private void clickWPItem(Wajibpajak wajibpajak){
        NonSwipeableViewPager pager = getActivity().findViewById(R.id.paytax_pager);
        pager.setCurrentItem(PayTax2PeriodFragment.VIEW_PAGER_CODE);

        payTaxActivity.wrappedSsp.wpId = wajibpajak.id;
        payTaxActivity.wrappedSsp.npwp = wajibpajak.npwp;
        payTaxActivity.wrappedSsp.name = wajibpajak.name;
        payTaxActivity.wrappedSsp.address = wajibpajak.address;
        payTaxActivity.wrappedSsp.city = wajibpajak.city;
        payTaxActivity.wrappedSsp.npwpPenyetor = wajibpajak.npwp;
        payTaxActivity.invokeFragmentInit(PayTax2PeriodFragment.VIEW_PAGER_CODE);
    }

    private void clickFabNewWp(){
        Intent intent = new Intent(getContext(), AddEditWpActivity.class);
        intent.putExtra(AppConstant.ITN_XADDEDITWP_ACTIONMODE, AddEditWpActivity.MODE_ADD);
        startActivity(intent);
    }

}
