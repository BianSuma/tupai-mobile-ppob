package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.PayTaxActivity;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.dto.WrappedSsp;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayTax4SkNopFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 4;

    private PayTaxActivity payTaxActivity;
    private TaxWPHeader taxWPHeader;

    private AppEditText inpSk;
    private AppEditText inpNop;
    private AppEditText inpNpwpPenyetor;

    private Button btnNext;

    private Validator validator;

    public PayTax4SkNopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paytax4_sk_nop, container, false);

        final NonSwipeableViewPager pager = getActivity().findViewById(R.id.paytax_pager);

        AppActionBar actionBar = view.findViewById(R.id.paypphsksop_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(PayTax3SetorFragment.VIEW_PAGER_CODE);
            }
        }, new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.paytaxsknop_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                clickRightMenu(id);
            }
        });

        if((taxWPHeader = view.findViewById(R.id.paytaxsksop_taxwpheader)) == null) throw new RuntimeException();

        if((inpSk = view.findViewById(R.id.paypphsksop_inp_sk)) == null) throw new RuntimeException();
        inpSk.constraintMandatory = true;
        inpSk.setConstraintNoSK();
        if((inpNop = view.findViewById(R.id.paypphsksop_inp_nop)) == null) throw new RuntimeException();
        inpNop.constraintMandatory = true;
        inpNop.setConstraintNOP();
        if((inpNpwpPenyetor = view.findViewById(R.id.paypphsksop_inp_npwppenyetor)) == null) throw new RuntimeException();
        inpNpwpPenyetor.constraintMandatory = true;
        inpNpwpPenyetor.setConstraintNPWP();

        // TODO: @test
//        inpSk.setText("12344743827928");
//        inpNop.setText("8888392374242222");

        btnNext = view.findViewById(R.id.paypphsksop_btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });

        validator = new Validator();
        validator.addEdit(inpSk);
        validator.addEdit(inpNop);
        validator.addEdit(inpNpwpPenyetor);

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

    public void init(){

        WrappedSsp wrappedSsp = payTaxActivity.wrappedSsp;

        taxWPHeader.setIcon(wrappedSsp.taxType.fetchIcon(), wrappedSsp.fetchDefaultTaxType().code);
        taxWPHeader.setHeadName(wrappedSsp.name);
        taxWPHeader.setNpwp(wrappedSsp.npwp);
        taxWPHeader.setTaxAccountPeriod(wrappedSsp.taxType, wrappedSsp.taxSlipType, wrappedSsp.fetchTaxPeriod(getContext()));

        Kjs kjs = wrappedSsp.taxSlipType;

        if(kjs.noSkActive) {
            inpSk.setText(wrappedSsp.noSkNotNull());
            inpSk.setVisibility(View.VISIBLE);
        }else inpSk.setVisibility(View.GONE);

        if(kjs.nopActive) {
            inpNop.setText(wrappedSsp.nopNotNull());
            inpNop.setVisibility(View.VISIBLE);
        }else inpNop.setVisibility(View.GONE);

        if(kjs.subjekPajakActive){
            inpNpwpPenyetor.setText(wrappedSsp.npwpPenyetorNotNull());
            inpNpwpPenyetor.setVisibility(View.VISIBLE);
        }else inpNpwpPenyetor.setVisibility(View.GONE);

    }

    private void clickRightMenu(int id){
        switch (id){
            case R.id.paytaxsknop_btnmenu_quickhelp:
                clickQuickHelp();
                break;
        }
    }

    public boolean isInputShow(){
        return ( inpSk.isShown() || inpNop.isShown() || inpNpwpPenyetor.isShown() );
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsknop_qhelptitle_sk), getString(R.string.paytaxsknop_qhelpbody_sk), (View)inpSk.getParent(), inpSk);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsknop_qhelptitle_nop), getString(R.string.paytaxsknop_qhelpbody_nop), (View)inpNop.getParent(), inpNop);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsknop_qhelptitle_penyetor), getString(R.string.paytaxsknop_qhelpbody_penyetor), (View)inpNpwpPenyetor.getParent(), inpNpwpPenyetor);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsknop_qhelptitle_btnnext), getString(R.string.paytaxsknop_qhelpbody_btnnext), (View)btnNext.getParent(), btnNext);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    public void clickNext(){
        if( ! validator.check(getContext()) ) return;

        String val;

        if(inpSk.isShown()) val = inpSk.getOnlyNumber();
        else val = null;
        payTaxActivity.wrappedSsp.noSk = val;

        if(inpNop.isShown()) val = inpNop.getOnlyNumber();
        else val = null;
        payTaxActivity.wrappedSsp.nop = val;

        if(inpNpwpPenyetor.isShown()) val = inpNpwpPenyetor.getOnlyNumber();
        else val = payTaxActivity.wrappedSsp.npwp;
        payTaxActivity.wrappedSsp.npwpPenyetor = val;

        payTaxActivity.invokeFragmentInit(PayTax5SummaryFragment.VIEW_PAGER_CODE);

        payTaxActivity.pager.setCurrentItem(PayTax5SummaryFragment.VIEW_PAGER_CODE);
    }

}
