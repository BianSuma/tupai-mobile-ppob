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

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.AddEditWpActivity;
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.component.FilterView;
import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SuccessFailCallback;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ProfileWPFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 1;

    private MainActivity mainActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppRecyclerView appRecyclerView;
    public AppCircleButton appCircleButtonAdd;

    public ProfileWPFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile_wp, container, false);

        swipeRefreshLayout = v.findViewById(R.id.profwp_swipyrefreshlayout);

        appRecyclerView = v.findViewById(R.id.profwp_list_wp);
        appRecyclerView.init(true, swipeRefreshLayout, new AppRecyclerView.AppCallback<Wajibpajak>() {
            @Override
            public void onClick(Wajibpajak d) {
                clickItem(d);
            }

            @Override
            public void onRefresh() {
                mainActivity.resetFilterViewparam(FilterView.LISTTYPE_WP);
                RequestParamConfig r = new RequestParamConfig();
                r.isRelogin = true;
                fetchData(1, new FilterParam(), r);
            }

            @Override
            public void onLoadMore(long nextPage) {
                FilterParam fdp = mainActivity.getFilterViewParam(FilterView.LISTTYPE_WP);
                RequestParamConfig rpc = new RequestParamConfig();
                rpc.isRelogin = true;
                rpc.isCache = false;

                rpc.reqPaging.page = nextPage;
                rpc.reqPaging.field = fdp.field;
                rpc.reqPaging.query = fdp.query;
                rpc.reqPaging.order = fdp.column;
                ApiMain.requestWPList(mainActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Wajibpajak>, ResponseDTO>() {
                    @Override
                    public void onSuccess(List<Wajibpajak> data) {
                        appRecyclerView.loadMoreAddItem(data);
                    }

                    @Override
                    public void onFail(ResponseDTO data) {
                        appRecyclerView.stopLoadMore();
                    }
                });
//                mainActivity.requestWPList(nextPage, fdp, rpc, new SuccessFailCallback<List<Wajibpajak>, ResponseError>() {
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

        appCircleButtonAdd =  v.findViewById(R.id.profwp_circlebtn_add);
        appCircleButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAdd();
            }
        });

        AppCircleButton appCircleButton =  v.findViewById(R.id.profwp_circlebtn_find);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFilterViewCallback(FilterView.LISTTYPE_WP, new CommonCallback<FilterParam>() {
                    @Override
                    public void onSuccess(FilterParam data) {
                        RequestParamConfig rpc = new RequestParamConfig();
                        rpc.isRelogin = true;
                        fetchData(1, data, rpc);
                    }
                });
            }
        });

        appCircleButton = v.findViewById(R.id.profwp_circlebtn_sort);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFilterViewSort(FilterView.LISTTYPE_WP, new CommonCallback<FilterParam>() {
                    @Override
                    public void onSuccess(FilterParam data) {
                        RequestParamConfig rpc = new RequestParamConfig();
                        rpc.isRelogin = true;
                        fetchData(1, data, rpc);
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) this.getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public void onResume(){
        super.onResume();

        // TODO: @prod
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.isRelogin = true;
        rpc.forceRequest = false;
        fetchData(1, new FilterParam(), rpc);

        // TODO: @test
//        adapter.addAll(items);
//        List<Wajibpajak> items = new ArrayList<>();
//        for(int i=0; i<10;i++) items.add(new Wajibpajak("Jajang Hendra", "101019833424242", "Jln. Cigadung", "Bandung"));
    }

    public void showProgressCircle(){
        if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(true);
    }
    public void hideProgressCircle(){
        if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
    }

    public void updateListView(List<Wajibpajak> d){
        appRecyclerView.updateListView(d);
    }

    public void fetchData(long page, FilterParam fdp, final RequestParamConfig rpc){
        final ProfileWPFragment frag = this;
        if(frag.isAdded()) swipeRefreshLayout.setRefreshing(true);

        rpc.reqPaging.page = page;
        rpc.reqPaging.field = fdp.field;
        rpc.reqPaging.query = fdp.query;
        rpc.reqPaging.order = fdp.column;
        ApiMain.requestWPList(mainActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Wajibpajak>, ResponseDTO>() {
            @Override
            public void onSuccess(List<Wajibpajak> data) {
                if( ! frag.isAdded() ) return;
                appRecyclerView.updateListView(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail(ResponseDTO data) {
                if( frag.isAdded() ){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
//        mainActivity.requestWPList(page, filterParam, reqParam, new SuccessFailCallback<List<Wajibpajak>, ResponseError>() {
//            @Override
//            public void onSuccess(List<Wajibpajak> data) {
//                if( ! frag.isAdded() ) return;
//                appRecyclerView.updateListView(data);
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

    public void clickAdd(){
        Intent intent = new Intent(getContext(), AddEditWpActivity.class);
        intent.putExtra(AppConstant.ITN_XADDEDITWP_ACTIONMODE, AddEditWpActivity.MODE_ADD);
        startActivity(intent);
    }

    public void clickItem(Wajibpajak wajibpajak){
        Intent intent = new Intent(getContext(), AddEditWpActivity.class);
        intent.putExtra(AppConstant.ITN_XADDEDITWP_ORGANIZATION, wajibpajak);
        intent.putExtra(AppConstant.ITN_XADDEDITWP_ACTIONMODE, AddEditWpActivity.MODE_EDIT);
        startActivity(intent);
    }
}
