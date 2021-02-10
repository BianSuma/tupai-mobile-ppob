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
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.activity.SSPDetailUnpaidActivity;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.component.FilterView;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.model.Sspunpaid;
import com.pajakku.tupaimobile.model.dto.FilterParam;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SuccessFailCallback;

import java.util.List;

//import pdi.jwt.Jwt;
//import pdi.jwt.JwtAlgorithm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class SSPUnpaidFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 0;

    private SwipeRefreshLayout swipyRefreshLayout;
//    private ListMultiSelect appRecyclerView;
    private AppRecyclerView appRecyclerView;

    private MainActivity mainActivity;

    public SSPUnpaidFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ssp_unpaid, container, false);

        swipyRefreshLayout = view.findViewById(R.id.sspunpaid_swipyrefreshlayout);

        appRecyclerView = view.findViewById(R.id.sspunfinish_listmultiselect);
        appRecyclerView.init(true, swipyRefreshLayout, new AppRecyclerView.AppCallback<Sspunpaid>() {
            @Override
            public void onClick(Sspunpaid d) {
                clickItem(d);
            }

            @Override
            public void onRefresh() {
                listReload();
//                mainActivity.resetFilterViewparam(FilterView.LISTTYPE_SSPUNPAID);
//                RequestParamConfig rpc = new RequestParamConfig();
//                rpc.isRelogin = true;
//                fetchData(1, null, null, null, null, rpc);
            }

            @Override
            public void onLoadMore(final long nextPage) {
                FilterParam fdp = mainActivity.getFilterViewParam(FilterView.LISTTYPE_SSPUNPAID);
                RequestParamConfig rpc = new RequestParamConfig();
                rpc.isCache = false;
                rpc.reqPaging.page = nextPage;
                rpc.reqPaging.field = fdp.field;
                rpc.reqPaging.query = fdp.query;
                rpc.reqPaging.order = fdp.order;
                rpc.reqPaging.column = fdp.column;

                ApiMain.getSspUnpaid(mainActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Sspunpaid>, ResponseDTO>() {
                    @Override
                    public void onSuccess(List<Sspunpaid> data) {
                        appRecyclerView.loadMoreAddItem(data);
                    }

                    @Override
                    public void onFail(ResponseDTO data) {
                        appRecyclerView.stopLoadMore();

                    }
                });

            }
        });

        AppCircleButton appCircleButton =  view.findViewById(R.id.sspunpaid_circlebtn_find);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFilterViewCallback(FilterView.LISTTYPE_SSPUNPAID, new CommonCallback<FilterParam>() {
                    @Override
                    public void onSuccess(FilterParam data) {
                        RequestParamConfig rpc = new RequestParamConfig();
                        rpc.isRelogin = true;
                        fetchData(1, data.field, data.query, data.order, data.column, rpc);
                    }
                });
            }
        });

        appCircleButton = view.findViewById(R.id.sspunpaid_circlebtn_sort);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFilterViewSort(FilterView.LISTTYPE_SSPUNPAID, new CommonCallback<FilterParam>() {
                    @Override
                    public void onSuccess(FilterParam data) {
                        RequestParamConfig rpc = new RequestParamConfig();
                        rpc.isRelogin = true;
                        fetchData(1, data.field, data.query, data.order, data.column, rpc);
                    }
                });
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
                    + " must implement BaseActivity");
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        // TODO: @prod
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.forceRequest = false;
        fetchData(1, null, null, null, null, rpc);

        // TODO: @test
//            List<ResponseSsp> respSspList = new ArrayList<>();
//            for(int i=0; i<1;i++){
//                ResponseSsp resSsp = new ResponseSsp();
//                resSsp.id = (i+1);
//                resSsp.name = "Andi Sugandi";
//                resSsp.npwp = "347294732984732897423";
//                resSsp.address = "Jln. Pajakku";
//                resSsp.amount = 40000;
//                resSsp.referenceNo = "11229876543"+i;
//
//                BillingDTO billingDTO = new BillingDTO();
//                billingDTO.responseCode = ( i % 2 == 0 ? TaxSlipResponse.RESPONSECODE_OK: null );
//                billingDTO.idBilling = ( i % 2 == 0 ? "7379832999992987" : null );
//                billingDTO.expiredDate = ( i % 2 == 0 ? "2022-12-08T16:21:24.307692" : null );
//                resSsp.billing = billingDTO;
//
//                TaxSlipResponse tsr = new TaxSlipResponse();
//                tsr.idbillingPajakku = "73798329824349878";
//                resSsp.taxSlipResponse = tsr;
//
//                resSsp.month1 = "01";
//                resSsp.month2 = "02";
//                resSsp.year = "2019";
//
//                Taxtype tt = new Taxtype();
//                tt.code = "411121";
//                tt.name = "PPh 21";
//                resSsp.taxType = tt;
//
//                Kjs kjs = new Kjs();
//                kjs.code = "300";
//                kjs.name = "STP";
//                resSsp.taxSlipType = kjs;
//
//                AuditDTO auditDTO = new AuditDTO();
//                auditDTO.updatedAt = ("2019-01-" + (i+1));
//                resSsp.audit = auditDTO;
//                respSspList.add(resSsp);
//            }
//            mainActivity.setCache(AppConstant.SP_CACHEKEY_SSPUNPAID, respSspList);
//            appRecyclerView.updateListView( Sspunpaid.getInstanceList(respSspList) );


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public void updateListView(List<Sspunpaid> data){
        appRecyclerView.updateListView( data );
    }

    private void fetchData(final long page, final String field, final String query, final String order, final String column, final RequestParamConfig reqParam){
        final SSPUnpaidFragment frag = this;
        if(frag.isAdded()) swipyRefreshLayout.setRefreshing(true);

        reqParam.reqPaging.page = page;
        reqParam.reqPaging.field = field;
        reqParam.reqPaging.query = query;
        reqParam.reqPaging.order = order;
        reqParam.reqPaging.column = column;
        ApiMain.getSspUnpaid(mainActivity, reqParam, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Sspunpaid>, ResponseDTO>() {
            @Override
            public void onSuccess(List<Sspunpaid> data) {
                if( ! frag.isAdded() ) return;
                appRecyclerView.updateListView( data );
                swipyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail(ResponseDTO data) {
                if( frag.isAdded() ) {
                    swipyRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void clickItem(final Sspunpaid sspunpaid){

//        Intent intent = new Intent(getContext(), SSPDetailUnpaidActivity.class);
//        intent.putExtra(AppConstant.ITN_SSPDETAILUNPAID_SSPUNPAID, sspunpaid);
//        mainActivity.startActivityForResult(intent, AppConstant.ACTRES_SSPUNPAID_DETAIL_TO_LIST);

        SSPDetailUnpaidActivity.startAct(mainActivity, AppConstant.ACTRES_SSPUNPAID_DETAIL_TO_LIST, sspunpaid);
    }

    public void updateListData(){
//        mainActivity.getDataCacheList(AppConstant.SP_CACHEKEY_SSPUNPAID, new CommonCallback<List<Sspunpaid>>() {
//            @Override
//            public void onSuccess(List<Sspunpaid> data) {
//                updateListView( data );
//            }
//        });
        listReload();
    }

    public void listReload(){
        mainActivity.resetFilterViewparam(FilterView.LISTTYPE_SSPUNPAID);
        RequestParamConfig rpc = new RequestParamConfig();
        rpc.isRelogin = true;
        fetchData(1, null, null, null, null, rpc);
    }

}
