package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.EditProfileActivity;
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.activity.TransactionLogActivity;
import com.pajakku.tupaimobile.component.ProfileHomeButton;
import com.pajakku.tupaimobile.component.ProfileHomeLabel;
import com.pajakku.tupaimobile.model.dto.ProfileUserCompany;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseMe;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.CommonCallback;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.SuccessFailCallback;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class ProfileHomeFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 0;

    private MainActivity mainActivity;
    private ProgressBar progressBar;

//    private TopupWidget topupWidget;

    private ProfileHomeLabel labelProfileName;
    private ProfileHomeLabel labelProfilePhone;
    private ProfileHomeLabel labelProfileEmail;

    public ProfileHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_home, container, false);

        progressBar = view.findViewById(R.id.profhome_progressbar);

//        AppActionBar actionBar = view.findViewById(R.id.profhome_actionbar);
        /*actionBar.init(0, null, null*/ /*new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.fragprof_profile;
            }

            @Override
            public void onClickRightMenu(int id) {
                switch (id) {
//                    case R.id.helphome_btnmenu_editprofile:
//                        clickEditProfile();
//                        break;
                    case R.id.helphome_btnmenu_reloadprofile:
                        clickReloadProfile();
                        break;
                }
            }
        }*/ /* ); */

        if((labelProfileName = view.findViewById(R.id.profhome_quickrow_name)) == null) throw new RuntimeException();
        if((labelProfilePhone = view.findViewById(R.id.profhome_quickrow_phone)) == null) throw new RuntimeException();
        if((labelProfileEmail = view.findViewById(R.id.profhome_quickrow_email)) == null) throw new RuntimeException();

//        topupWidget = view.findViewById(R.id.profhome_topupwidget);

//        final ViewPager viewPager = getActivity().findViewById(R.id.fragprof_pager);

//        ProfileHomeButton btnWp = view.findViewById(R.id.profhome_btn_wp);
//        btnWp.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                viewPager.setCurrentItem(ProfileWPFragment.VIEW_PAGER_CODE);
//            }
//        });

        ProfileHomeButton profHomeBtn;

        // TODO: tunggu API transaksi log
//        profHomeBtn = view.findViewById(R.id.profhome_btn_transactionlog);
//        profHomeBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                clickTransactionLog();
//            }
//        });

//        profHomeBtn = view.findViewById(R.id.profhome_btn_logout);
//        profHomeBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                clickLogout();
//            }
//        });

        return view;
    }

    public void onCreateViewData(){
        ProfileUserCompany puc =  mainActivity.getProfileUserCompany();
        labelProfileName.init( mainActivity.getSpString(AppConstant.SP_USERNAME) );
        labelProfilePhone.init(puc.phone);
        labelProfileEmail.init(puc.email);

//        topupWidget.setUserName( puc.fullname );

        Fragment f = mainActivity.getFragment(ProfileFragment.VIEW_PAGER_CODE);
        if(f != null){
            f = ((ProfileFragment)f).getFragment(ProfileHomeFragment.VIEW_PAGER_CODE);
        }
        final ProfileHomeFragment ff = (ProfileHomeFragment)f;

        if (mainActivity.isCacheNotExpire(AppConstant.SP_CACHEKEY_EMONBALANCE)) {
            if(ff != null) ff.updateUiBalanceStartProgress();
            if( mainActivity.getSpBool(AppConstant.SP_CACHEEMONBALANCE_IS_MC_ACTIVE)) {
                mainActivity.getDataCacheSingle(AppConstant.SP_CACHEKEY_EMONBALANCE, new CommonCallback<ResponseFinnet>() {
                    @Override
                    public void onSuccess(ResponseFinnet data) {
                        if (ff != null) ff.updateUiBalance(data);
                    }
                });
            }else{
                if(ff != null) ff.updateUiSetTopupDeactivated();
            }
        }else{
            if(ff != null) ff.updateUiBalanceStartProgress();
            mainActivity.requestGetBalance(false, false, new SuccessFailCallback<ResponseFinnet, ResponseError>() {
                @Override
                public void onSuccess(ResponseFinnet data) {
                    if(ff != null) ff.updateUiBalance(data);
                }

                @Override
                public boolean onFail(ResponseError data) {
                    if(ff != null) ff.updateUiSetTopupDeactivated();
                    return false;
                }
            });
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        onCreateViewData();
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

    public void updateUiBalanceStartProgress(){
//        topupWidget.startProgressBar();
    }

    public void updateUiBalance(ResponseFinnet rf){
//        if(topupWidget == null) return;
//        topupWidget.setSaldo( rf.custBalance );
//        topupWidget.setModeMcashSudahActivation();
    }
    public void updateUiSetTopupDeactivated(){
//        if(topupWidget == null) return;
//        topupWidget.setModeMcashBelumActivation();
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    public void fetchData(){
        progressBar.setVisibility(View.VISIBLE);
        mainActivity.requestHttpSimple(true, 0, true, AppConstant.SP_CACHEKEY_ME, true, new HttpCallbackInterfaceSimple<ResponseMe>() {
            @Override
            public Call<ResponseMe> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId) {
                return httpService.me(bearerAuth);
            }

            @Override
            public void onSuccess(ResponseMe response) {
                Fragment f = mainActivity.getFragment(ProfileFragment.VIEW_PAGER_CODE);
                if( f != null ) {
                    f = ((ProfileFragment)f).getFragment(ProfileHomeFragment.VIEW_PAGER_CODE);
                    if( f != null ) {
                        ((ProfileHomeFragment) f).onCreateViewData();
                        ((ProfileHomeFragment) f).hideProgressBar();
                    }
                }

            }

            @Override
            public boolean onFail(ResponseError re){
                Fragment f = mainActivity.getFragment(ProfileFragment.VIEW_PAGER_CODE);
                if( f != null ) {
                    f = ((ProfileFragment)f).getFragment(ProfileHomeFragment.VIEW_PAGER_CODE);
                    if( f != null ) {
                        ((ProfileHomeFragment) f).hideProgressBar();
                    }
                }
                return true;
            }
        });
    }

    private void clickEditProfile(){
        Intent intent = new Intent(getContext(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void clickTransactionLog(){
        Intent intent = new Intent(getContext(), TransactionLogActivity.class);
        startActivity(intent);
    }

    private void clickReloadProfile(){
        fetchData();
    }

//    public void clickLogout(){
//        mainActivity.logoutClearCacheConfirm();
//    }

}
