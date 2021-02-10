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
import com.pajakku.tupaimobile.activity.SSPDetailDoneActivity;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.component.AppRecyclerView;
import com.pajakku.tupaimobile.component.FilterView;
import com.pajakku.tupaimobile.model.Sspdone;
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
 * create an instance of this fragment.
 */
public class SSPDoneFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AppRecyclerView appRecyclerView;
    private MainActivity mainActivity;

    public SSPDoneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ssp_done, container, false);

        swipeRefreshLayout = view.findViewById(R.id.sspdone_swipyrefreshlayout);

//        SSPAdapterDone sspAdapterDone = new SSPAdapterDone(getContext());

        appRecyclerView = view.findViewById(R.id.sspdone_listmultiselect);
        appRecyclerView.init(true, swipeRefreshLayout, new AppRecyclerView.AppCallback<Sspdone>() {
            @Override
            public void onClick(Sspdone d) {
                clickItem(d);
            }

            @Override
            public void onRefresh() {
                mainActivity.resetFilterViewparam(FilterView.LISTTYPE_SSPDONE);
                RequestParamConfig rpc = new RequestParamConfig();
                rpc.isRelogin = true;
                fetchData(1, null, null, null, null, rpc);
            }

            @Override
            public void onLoadMore(long nextPage) {
                FilterParam fdp = mainActivity.getFilterViewParam(FilterView.LISTTYPE_SSPDONE);
                RequestParamConfig rpc = new RequestParamConfig();
                rpc.isCache = false;
                rpc.reqPaging.page = nextPage;
                rpc.reqPaging.field = fdp.field;
                rpc.reqPaging.query = fdp.query;
                rpc.reqPaging.order = fdp.order;
                rpc.reqPaging.column = fdp.column;
                ApiMain.getSspDone(mainActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Sspdone>, ResponseDTO>() {
                    @Override
                    public void onSuccess(List<Sspdone> data) {
                        appRecyclerView.loadMoreAddItem(data);
                    }

                    @Override
                    public void onFail(ResponseDTO data) {
                        appRecyclerView.stopLoadMore();
                    }
                });
//                mainActivity.requestSSPDone(nextPage, fdp.field, fdp.query, fdp.order, fdp.column, rpc, new SuccessFailCallback<List<Sspdone>,ResponseError>() {
//                    @Override
//                    public void onSuccess(List<Sspdone> data) {
//                        appRecyclerView.loadMoreAddItem(data);
//                    }
//                    @Override
//                    public boolean onFail(ResponseError er){
//                        appRecyclerView.stopLoadMore();
//                        return true;
//                    }
//                });
            }
        });

        AppCircleButton appCircleButton =  view.findViewById(R.id.sspdone_circlebtn_find);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFilterViewCallback(FilterView.LISTTYPE_SSPDONE, new CommonCallback<FilterParam>() {
                    @Override
                    public void onSuccess(FilterParam data) {
                        RequestParamConfig rpc = new RequestParamConfig();
                        rpc.isRelogin = true;
                        fetchData(1, data.field, data.query, data.order, data.column, rpc);
                    }
                });
            }
        });

        appCircleButton = view.findViewById(R.id.sspdone_circlebtn_sort);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFilterViewSort(FilterView.LISTTYPE_SSPDONE, new CommonCallback<FilterParam>() {
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
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
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
//                resSsp.name = "Nana Maulana";
//                resSsp.npwp = "347294732984732897423";
//                resSsp.address = "Jln. Pajakku";
//                resSsp.amount = 5000;
//                resSsp.referenceNo = "11229876543"+i;
//
//                BillingDTO billingDTO = new BillingDTO();
//                billingDTO.responseCode = TaxSlipResponse.RESPONSECODE_OK;
//                billingDTO.idBilling = "7379832999992987"+i;
//                billingDTO.expiredDate = "2022-12-08T16:21:24.307692";
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
//                MPNPaymentResponse payResp = new MPNPaymentResponse();
//                payResp.ntpn = "4839748324200000000"+i;
//                payResp.transactionDateTime = "2019-03-25T20:31:39.236";
//                resSsp.payment = payResp;
//
//                AuditDTO auditDTO = new AuditDTO();
//                auditDTO.updatedAt = ("2019-06-0" + (i+1));
//                resSsp.audit = auditDTO;
//                respSspList.add(resSsp);
//            }
//            mainActivity.setCache(AppConstant.SP_CACHEKEY_SSPDONE, respSspList);
//            appRecyclerView.updateListView( Sspdone.getInstanceListSspdone(respSspList) );

    }

    public void hideProgressBar(){
        if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
    }

    public void updateListView(List<Sspdone> data){
        appRecyclerView.updateListView( data );
        hideProgressBar();
    }

    private void fetchData(final long page, final String field, final String query, String order, String column, final RequestParamConfig reqParam){
        final SSPDoneFragment frag = this;
        if( frag.isAdded() ) swipeRefreshLayout.setRefreshing(true);
        reqParam.reqPaging.page = page;
        reqParam.reqPaging.field = field;
        reqParam.reqPaging.query = query;
        reqParam.reqPaging.order = order;
        reqParam.reqPaging.column = column;
        ApiMain.getSspDone(mainActivity, reqParam, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<List<Sspdone>, ResponseDTO>() {
            @Override
            public void onSuccess(List<Sspdone> data) {
                if( ! frag.isAdded() ) return;
                appRecyclerView.updateListView( data );
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail(ResponseDTO data) {
                if ( frag.isAdded() ) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
//        mainActivity.requestSSPDone(page, field, query, order, column, reqParam, new SuccessFailCallback<List<Sspdone>,ResponseError>() {
//            @Override
//            public void onSuccess(List<Sspdone> data) {
//                if( ! frag.isAdded() ) return;
//                appRecyclerView.updateListView( data );
//                swipeRefreshLayout.setRefreshing(false);
//            }
//            @Override
//            public boolean onFail(ResponseError er){
//                if ( frag.isAdded() ) {
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//                return reqParam.isShowFailToast;
//            }
//        });
    }

    public void clickItem(Sspdone sspdone){
//        SSPDetailDoneActivity.startAct(mainActivity, sspdone);
        SSPDetailDoneActivity.startAct(mainActivity, sspdone.id);
    }
}
