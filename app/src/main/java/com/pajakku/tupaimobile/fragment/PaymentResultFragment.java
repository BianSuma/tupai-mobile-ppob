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
public class PaymentResultFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 3;

    public static final int PAYMENT_SUCCESS = 1;
    public static final int PAYMENT_FAIL = 2;

    public PaymentResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_payment_result, container, false);

        Button btnClose = v.findViewById(R.id.paymentresult_btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentResultFragment.this.getActivity().finish();
            }
        });

        return v;
    }

    public void init(int payResultCode){

        final NonSwipeableViewPager pager = null;

        View.OnClickListener btnBackListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PaymentMethodFragment.VIEW_PAGER_CODE);
            }
        };
        int pagetitle = R.string.paymentresult_page_titletax;
        int btnDetailVisible = View.GONE;
        int sspStatus = TaxWPHeader.STATUS_BELUM_BAYAR;
        int strMsg = R.string.paymentresult_label_msgfail;
        if(payResultCode == PAYMENT_SUCCESS){
            btnBackListener = null;
            btnDetailVisible = View.VISIBLE;
            sspStatus = TaxWPHeader.STATUS_DONE;
        }

        AppActionBar actionBar = getView().findViewById(R.id.paymentresult_appactionbar);
        actionBar.init(pagetitle, btnBackListener, null);

        TaxWPHeader twhTop = getView().findViewById(R.id.paymentresult_taxwpheader);
        twhTop.init(R.drawable.ic_pph, "Entah Sifulan", "663427497324324", "PPh 21", "411121", "300", "Agt-agt 2018");

        TextView tvMsg = getView().findViewById(R.id.paymentresult_label_msg);
        tvMsg.setText(strMsg);

        Button btnDetail = getView().findViewById(R.id.paymentresult_btn_detail);
        btnDetail.setVisibility(btnDetailVisible);
    }

}
