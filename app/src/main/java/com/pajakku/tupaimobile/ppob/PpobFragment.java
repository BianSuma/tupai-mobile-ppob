package com.pajakku.tupaimobile.ppob;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.MainActivity;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.fragment.HomeFragment;
import com.pajakku.tupaimobile.ppob.bpjs.BpjsActivity;
import com.pajakku.tupaimobile.ppob.dokuwallet.DokuWalletActivity;
import com.pajakku.tupaimobile.ppob.internet.InternetActivity;
import com.pajakku.tupaimobile.ppob.multifinance.MultiFinanceActivity;
import com.pajakku.tupaimobile.ppob.paketdata.PaketDataActivity;
import com.pajakku.tupaimobile.ppob.pdam.PdamActivity;
import com.pajakku.tupaimobile.ppob.pulsa.PulsaActivity;
import com.pajakku.tupaimobile.ppob.tokenpostpaid.TokenPostPaidActivity;
import com.pajakku.tupaimobile.ppob.tokenprepaid.TokenPrePaidActivity;
import com.pajakku.tupaimobile.ppob.tvkabel.TvKabelActivity;
import com.pajakku.tupaimobile.util.Utility;


public class PpobFragment extends Fragment {
    private MainActivity mainActivity;



    public PpobFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ppob, container, false);

        AppActionBar appActionBar = root.findViewById(R.id.fraghome_appactionbar);
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

        LinearLayout pulsaLinearLayout = (LinearLayout)root.findViewById(R.id.Pulsa);
        pulsaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PulsaActivity.class);
                startActivity(i);
            }
        });

        LinearLayout paketDataLinearLayout = (LinearLayout)root.findViewById(R.id.PaketData);
        paketDataLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PaketDataActivity.class);
                startActivity(i);
            }
        });

        LinearLayout tokenPostPaidLinearLayout = (LinearLayout)root.findViewById(R.id.TokenPostPaid);
        tokenPostPaidLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TokenPostPaidActivity.class);
                startActivity(i);
            }
        });

        LinearLayout nonTagListLayout = (LinearLayout)root.findViewById(R.id.NonTagList);
        nonTagListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PulsaActivity.class);
                startActivity(i);
            }
        });

        LinearLayout tokenPrePaidLinearLayout = (LinearLayout)root.findViewById(R.id.TokenPrePaid);
        tokenPrePaidLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TokenPrePaidActivity.class);
                startActivity(i);
            }
        });

        LinearLayout pdamLinearLayout = (LinearLayout)root.findViewById(R.id.Pdam);
        pdamLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PdamActivity.class);
                startActivity(i);
            }
        });

        LinearLayout internetLinearLayout = (LinearLayout)root.findViewById(R.id.Internet);
        internetLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), InternetActivity.class);
                startActivity(i);
            }
        });

        LinearLayout tvKabelLinearLayout = (LinearLayout)root.findViewById(R.id.TvKabel);
        tvKabelLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TvKabelActivity.class);
                startActivity(i);
            }
        });

        LinearLayout multiFinanceLinearLayout = (LinearLayout)root.findViewById(R.id.MultiFinance);
        multiFinanceLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MultiFinanceActivity.class);
                startActivity(i);
            }
        });

        LinearLayout bpjsLinearLayout = (LinearLayout)root.findViewById(R.id.Bpjs);
        bpjsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), BpjsActivity.class);
                startActivity(i);
            }
        });

        LinearLayout dokuWalletLinearLayout = (LinearLayout)root.findViewById(R.id.DokuWallet);
        dokuWalletLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DokuWalletActivity.class);
                getView().getContext().startActivity(i);
                getActivity();
            }
        });

        return root;
    }
}