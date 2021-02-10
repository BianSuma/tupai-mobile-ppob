package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.activity.PayBPJSActivity;
import com.pajakku.tupaimobile.activity.PayPascaBayarActivity;
import com.pajakku.tupaimobile.activity.PayPulsaActivity;
import com.pajakku.tupaimobile.activity.PayTagihanPLNActivity;
import com.pajakku.tupaimobile.activity.PayTaxActivity;
import com.pajakku.tupaimobile.activity.PayTokenPLNActivity;
import com.pajakku.tupaimobile.activity.RegistrasiNpwpActivity;
import com.pajakku.tupaimobile.activity.TaxTypeListActivity;
import com.pajakku.tupaimobile.api.ApiMain;
import com.pajakku.tupaimobile.api.ApiReqBilling;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppCircleButton;
import com.pajakku.tupaimobile.component.AppGridView;
import com.pajakku.tupaimobile.component.GridCollection;
import com.pajakku.tupaimobile.component.TopupWidget;
import com.pajakku.tupaimobile.model.GridItem;
import com.pajakku.tupaimobile.model.Taxtype;
import com.pajakku.tupaimobile.model.dto.ProfileUserCompany;
import com.pajakku.tupaimobile.model.dto.RequestParamConfig;
import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.tester.AppTester;
import com.pajakku.tupaimobile.util.AppConf;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.util.SuccessFailCallback;
import com.pajakku.tupaimobile.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class HomeFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 0;

    private MainActivity mainActivity;
    private ScrollView scrollView;
    private SwipeRefreshLayout swipyRefreshLayout;
    private TopupWidget topupWidget;
    private GridCollection gridCollection;
    private AppCircleButton appCircleButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        AppActionBar appActionBar = view.findViewById(R.id.fraghome_appactionbar);
        appActionBar.setRightMenu(new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.fraghome_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                switch (id){
//                    case R.id.fraghome_btnmenu_quickhelp:
//                        clickQuickHelp();
//                        break;
//                    case R.id.fraghome_toprightbtn_about:
//                        Utility.showAbout(mainActivity);
//                        break;
                    case R.id.fraghome_btnmenu_logout:
                        mainActivity.logoutClearCacheConfirm();
                        break;
                }
            }
        });
        appActionBar.setVer( mainActivity.getPInfo().versionName );

        AppCompatButton btnDaftarNpwp = view.findViewById(R.id.fraghome_btn_daftarnpwp);
        btnDaftarNpwp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_person, 0, 0, 0);
        btnDaftarNpwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoEregWeb();
            }
        });
        if(!AppConf.NO_LONG_CLICK) {
            btnDaftarNpwp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    RegistrasiNpwpActivity.startAct(mainActivity);
                    return true;
                }
            });
        }

        topupWidget = view.findViewById(R.id.fraghome_topupwidget);
        if((scrollView = view.findViewById(R.id.fraghome_scrollview)) == null) throw new RuntimeException();

        topupWidget.setVisibility(View.GONE);  // TODO: @warn finnet disable dulu, pakai BRI Epay dulu

        swipyRefreshLayout = view.findViewById(R.id.fraghome_swipyrefreshlayout);
        swipyRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(1, true);
            }
        });

        gridCollection = view.findViewById(R.id.fraghome_gridcollection);
        gridCollection.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData(1, true);
            }
        }, new AppGridView.AppListener() {
            @Override
            public void onClickItem(GridItem gi) {
                if(gi.getType() == GridItem.TYPE_OTHER) clickGridItemOther(gi);
                else clickGridItemTax(gi);
            }
        });

        appCircleButton =  view.findViewById(R.id.fraghome_fab);
        appCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFab();
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

        if( mainActivity.isCacheNotExpire(AppConstant.SP_CACHEKEY_TAXTYPE) ){
            mainActivity.getDataCacheList(AppConstant.SP_CACHEKEY_TAXTYPE, new CommonCallback<List<Taxtype>>() {
                @Override
                public void onSuccess(List<Taxtype> data) {
                    gridCollection.setTaxType(data);

                    scrollToUp();
                    reloadEmonPanel();
                }
            });
        }else{

            fetchData(1, true);

        }
    }

    private void reloadEmonPanel(){
        if(true) return;  // TODO: @warn utk sementara, finnet disable, pakai BRI Epay dulu

        ProfileUserCompany puc = mainActivity.getProfileUserCompany();
        topupWidget.setUserName(puc.fullname);

        final HomeFragment f = (HomeFragment)mainActivity.getFragment(HomeFragment.VIEW_PAGER_CODE);

        if (mainActivity.isCacheNotExpire(AppConstant.SP_CACHEKEY_EMONBALANCE)) {
            if(f != null) f.updateUiBalanceStartProgress();
            if( mainActivity.getSpBool(AppConstant.SP_CACHEEMONBALANCE_IS_MC_ACTIVE)) {
                mainActivity.getDataCacheSingle(AppConstant.SP_CACHEKEY_EMONBALANCE, new CommonCallback<ResponseFinnet>() {
                    @Override
                    public void onSuccess(ResponseFinnet data) {
                        if (f != null) f.updateBalance(data);
                    }
                });
            }else{
                if(f != null) f.updateUiSetTopupDeactivated();
            }
        } else {
            if(f != null) f.updateUiBalanceStartProgress();
            mainActivity.requestGetBalance(false, false, new SuccessFailCallback<ResponseFinnet, ResponseError>() {
                @Override
                public void onSuccess(ResponseFinnet data) {
                    if(f != null) f.updateBalance(data);
                }

                @Override
                public boolean onFail(ResponseError data) {
                    if(f != null) f.updateUiSetTopupDeactivated();
                    return false;
                }
            });
        }
    }

    private void gotoEregWeb(){

        RequestParamConfig rpc = new RequestParamConfig();
        rpc.forceRequest = false;
        ApiMain.httpFirst(mainActivity, rpc, new com.pajakku.tupaimobile.ifc.SuccessFailCallback<AppStatusData, ResponseDTO>() {
            @Override
            public void onSuccess(AppStatusData appStatusData) {

//                Utility.gotoWebsite(mainActivity, appStatusData.api.eregHost);
                Utility.gotoWebsite(mainActivity, "http://103.218.165.160");

            }

            @Override
            public void onFail(ResponseDTO data) {

            }
        });
    }

    private void scrollToUp(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_UP);
                showQuickHelp();
            }
        });
    }

    public void hideProgressBar(){
        if(swipyRefreshLayout != null) swipyRefreshLayout.setRefreshing(false);
    }

    public void updateListView(List<Taxtype> data){
        hideProgressBar();
        gridCollection.setTaxType(data);
    }

    public void updateUiSetTopupDeactivated(){
        topupWidget.setModeMcashBelumActivation();
    }

    public void updateUiBalanceStartProgress(){
        topupWidget.startProgressBar();
    }

    private void fetchData(final long page, boolean isRelogin){
        Fragment f = null;
        if(mainActivity != null) {
            f = mainActivity.getFragment(HomeFragment.VIEW_PAGER_CODE);
        }
        final HomeFragment frag = (HomeFragment) f;

        RequestParamConfig rpc = new RequestParamConfig();
        rpc.swipeRefreshLayout = swipyRefreshLayout;
        ApiReqBilling.findKjp(mainActivity, rpc, new CommonCallback<List<RespListTaxType>>() {
            @Override
            public void onSuccess(List<RespListTaxType> data) {
                if(frag == null) return;
                frag.updateListView( RespListTaxType.toTaxType(data) );

                scrollToUp();
                reloadEmonPanel();
            }
        });
//        ApiMain.getTaxType(mainActivity, rpc, new CommonCallback<List<RespListTaxType>>() {
//            @Override
//            public void onSuccess(List<RespListTaxType> data) {
//                if(frag == null) return;
//                frag.updateListView( RespListTaxType.toTaxType(data) );
//
//                scrollToUp();
//                reloadEmonPanel();
//            }
//        });

    }

    public void updateBalance(ResponseFinnet rf){
        if(topupWidget == null) return;
        topupWidget.setSaldo( rf.custBalance );
        topupWidget.setModeMcashSudahActivation();
    }

    private void showQuickHelp(){
        String sp = AppConstant.SP_QH_MAINHOME;
        boolean b = mainActivity.getSpBool(sp);
        if(b) return;
        clickQuickHelp();
        mainActivity.setSpBool(sp);
    }

    private void clickQuickHelp(){

        View tabLayout = getActivity().findViewById(R.id.main_tab_layout);

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();

        BubbleShowCaseBuilder sc = Utility.createShowCase(getActivity(), getString(R.string.fraghome_qhelptitle_topup), getString(R.string.fraghome_qhelpbody_topup), scrollView, topupWidget.btnTopUp);
        if(sc != null) listSc.add(sc);
        sc = Utility.createShowCase(getActivity(), getString(R.string.fraghome_qhelptitle_wptype), getString(R.string.fraghome_qhelpbody_wptype), scrollView, gridCollection.agvWp);
        if(sc != null) listSc.add(sc);
        sc = Utility.createShowCase(getActivity(), getString(R.string.fraghome_qhelptitle_tax), getString(R.string.fraghome_qhelpbody_tax), scrollView, gridCollection.invisView);
        if(sc != null) listSc.add(sc);
        sc = Utility.createShowCase(getActivity(), getString(R.string.fraghome_qhelptitle_taxtypelist), getString(R.string.fraghome_qhelpbody_taxtypelist), (View)appCircleButton.getParent(), appCircleButton);
        if(sc != null) listSc.add(sc);
        sc = Utility.createShowCase(getActivity(), getString(R.string.fraghome_qhelptitle_mainmenu), getString(R.string.fraghome_qhelpbody_mainmenu), (View)tabLayout.getParent(), tabLayout);
        if(sc != null) listSc.add(sc);

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    public void clickGridItemTax(final GridItem gi){

        mainActivity.removeCache(AppConstant.SP_CACHEKEY_STREAMKJS, true, new CommonCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                gotoPayTaxActivity(gi);
            }
        });

    }

    private void gotoPayTaxActivity(GridItem gi){
        gridCollection.addRecent(gi.taxtype);

        mainActivity.removeCache(AppConstant.SP_BACKMARK_PAYTAXBEGIN, false, null);

        Intent intent = new Intent(getContext(), PayTaxActivity.class);
        intent.putExtra(AppConstant.ITN_FRAGHOMEPAYTAX_WPTYPE, gridCollection.getWpType());
        intent.putExtra(AppConstant.ITN_FRAGHOMEPAYTAX_TAXTYPE, gi.taxtype);
        getActivity().startActivityForResult(intent, AppConstant.ACTRES_CREATE_SSP);
    }

    private void clickFab(){

        Intent itn = new Intent(getContext(), TaxTypeListActivity.class);
        getActivity().startActivityForResult(itn, AppConstant.ACTRES_TAXTYPE_AS_LIST);

    }

    private void clickGridItemOther(GridItem gi){
        Class<?> cls;
        switch (gi.icon){
            case R.drawable.fraghome_griditem_pulse:
                cls = PayPulsaActivity.class;
                break;
            case R.drawable.fraghome_griditem_pascabayar:
                cls = PayPascaBayarActivity.class;
                break;
            case R.drawable.fraghome_griditem_tokenpln:
                cls = PayTokenPLNActivity.class;
                break;
            case R.drawable.fraghome_griditem_tagihanpln:
                cls = PayTagihanPLNActivity.class;
                break;
            case R.drawable.fraghome_griditem_bpjs:
                cls = PayBPJSActivity.class;
                break;
            case R.drawable.fraghome_griditem_indihome:
                cls = PayPascaBayarActivity.class;
                break;
            default:
                return;
        }
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }
}
