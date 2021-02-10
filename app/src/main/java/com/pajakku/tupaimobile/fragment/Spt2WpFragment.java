package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.SptActivity;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.SuccessFailCallback;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Spt2WpFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 2;

    private SptActivity sptActivity;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppRecyclerView appRecyclerView;

    public Spt2WpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_spt1_wp, container, false);

//        AppActionBar appActionBar = v.findViewById(R.id.spt0wp_appactionbar);
//        appActionBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(Spt3YearFragment.VIEW_PAGER_CODE);
//            }
//        });

        swipeRefreshLayout = v.findViewById(R.id.spt0wp_swipyrefreshlayout);

        appRecyclerView = v.findViewById(R.id.spt0wp_list_wp);
        appRecyclerView.init(true, swipeRefreshLayout, new AppRecyclerView.AppCallback<Wajibpajak>() {
            @Override
            public void onClick(Wajibpajak d) {
                clickItem(d);
            }

            @Override
            public void onRefresh() {
//                mainActivity.resetFilterViewparam(FilterView.LISTTYPE_WP);
                RequestParamConfig r = new RequestParamConfig();
                r.isRelogin = true;
                fetchData(1, new FilterParam(), r);
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


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SptActivity) {
            sptActivity = (SptActivity) this.getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sptActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO: @prod
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.isRelogin = true;
        rpc.forceRequest = false;
        fetchData(1, new FilterParam(), rpc);

        // TODO: @test
//        List<Wajibpajak> items = new ArrayList<>();
//        Wajibpajak wp;
//        for(int i=1; i<4;i++){
//            wp = new Wajibpajak();
//            switch (i){
//                case 1:
//                    wp.name = "Andi Sugandi";
//                    break;
//                case 2:
//                    wp.name = "Budi Sentosa";
//                    break;
//                case 3:
//                    wp.name = "Citra Lestari";
//                    break;
//                default:
//                    wp.name = "Person "+i;
//            }
//            wp.npwp = "12345678905555"+i;
//            wp.address = "Cigadung";
//            wp.city = "Bandung";
//            items.add(wp);
//        }
//        appRecyclerView.updateListView( items );

    }

    public void fetchData(long page, FilterParam fdp, final RequestParamConfig rpc){
        final Spt2WpFragment frag = this;
        if(frag.isAdded()) swipeRefreshLayout.setRefreshing(true);

        rpc.reqPaging.page = page;
        rpc.reqPaging.field = fdp.field;
        rpc.reqPaging.query = fdp.query;
        rpc.reqPaging.order = fdp.column;
        ApiMain.requestWPList(sptActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Wajibpajak>, ResponseDTO>() {
            @Override
            public void onSuccess(List<Wajibpajak> data) {
                if( ! frag.isAdded() ) return;
                appRecyclerView.updateListView( data );
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail(ResponseDTO data) {
                if( frag.isAdded() ){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
//        sptActivity.requestWPList(page, filterParam, reqParam, new SuccessFailCallback<List<Wajibpajak>, ResponseError>() {
//            @Override
//            public void onSuccess(List<Wajibpajak> data) {
//                if( ! frag.isAdded() ) return;
//                appRecyclerView.updateListView( data );
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public boolean onFail(ResponseError data) {
//                if( frag.isAdded() ){
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//                return reqParam.isShowFailToast;
//            }
//        });

    }

    public void clickItem(Wajibpajak wajibpajak){
        sptActivity.sptDraft.npwp = wajibpajak.npwp;
        sptActivity.sptDraft.wpName = wajibpajak.name;
        sptActivity.viewPager.setCurrentItem(Spt3YearFragment.VIEW_PAGER_CODE);
        sptActivity.invokeSetViewData(Spt3YearFragment.VIEW_PAGER_CODE);
    }

}
