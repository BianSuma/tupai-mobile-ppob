package com.pajakku.tupaimobile.fragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.component.TaxWPHeader;

/**
 * A simple {@link Fragment} subclass.
 */
@Deprecated
public class PayPPhResultFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 7;

    public PayPPhResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_pph_result, container, false);

        Button btnDetail = view.findViewById(R.id.paypphresult_btn_detail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btnClose = view.findViewById(R.id.paypphresult_btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPPhResultFragment.this.getActivity().finish();
            }
        });

        return view;
    }

    public void init(int strMsg){

        final NonSwipeableViewPager pager = getActivity().findViewById(R.id.paytax_pager);

        View.OnClickListener btnBackListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PayPPhMethodFragment.VIEW_PAGER_CODE);
            }
        };
        int btnDetailVisible = View.GONE;
        int sspStatus = TaxWPHeader.STATUS_BELUM_BAYAR;
        if(strMsg == R.string.paypphresult_label_msgsuccess){
            btnBackListener = null;
            btnDetailVisible = View.VISIBLE;
            sspStatus = TaxWPHeader.STATUS_DONE;
        }

        AppActionBar actionBar = getView().findViewById(R.id.paypphresult_actionbar);
        actionBar.init(0, btnBackListener, null);

        TaxWPHeader twhTop = getView().findViewById(R.id.paypphresult_twh_top);
        twhTop.init(R.drawable.ic_pph, "Entah Sifulan", "663427497324324", "PPh 21", "411121", "300", "Agt-agt 2018");

        TextView tvMsg = getView().findViewById(R.id.paypphresult_label_msg);
        tvMsg.setText(strMsg);

        Button btnDetail = getView().findViewById(R.id.paypphresult_btn_detail);
        btnDetail.setVisibility(btnDetailVisible);
    }

}
