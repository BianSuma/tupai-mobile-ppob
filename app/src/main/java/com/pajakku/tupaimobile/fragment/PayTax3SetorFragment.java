package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
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
import com.pajakku.tupaimobile.component.TaxWPHeader;
import com.pajakku.tupaimobile.model.Kjs;
import com.pajakku.tupaimobile.model.dto.TaxtypeAlias;
import com.pajakku.tupaimobile.model.dto.WrappedSsp;
import com.pajakku.tupaimobile.util.AppConstant;
import com.pajakku.tupaimobile.util.Utility;
import com.pajakku.tupaimobile.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayTax3SetorFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 3;

    private PayTaxActivity payTaxActivity;
    private TaxWPHeader taxWPHeader;
    private Button btnCalculateSetor;
    private AppEditText inpSetor;
    private AppEditText inpTerbilang;

    private Button btnNext;

    private Validator validator;

    public PayTax3SetorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paytax3_setor, container, false);

        AppActionBar actionBar = view.findViewById(R.id.paypphsetor_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payTaxActivity.pager.setCurrentItem(PayTax2PeriodFragment.VIEW_PAGER_CODE);
            }
        }, new AppActionBar.AppListener() {
            @Override
            public int menuResource() {
                return R.menu.paytaxsetor_rightmenu;
            }

            @Override
            public void onClickRightMenu(int id) {
                clickRightMenu(id);
            }
        });

        if((taxWPHeader = view.findViewById(R.id.paytaxsetor_taxwpheader)) == null) throw new RuntimeException();

        btnCalculateSetor = view.findViewById(R.id.paytaxsetor_btn_calculatesetor);
        btnCalculateSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetengahPersenDialog();
            }
        });
        if(payTaxActivity.wrappedSsp.wpType != TaxtypeAlias.WPTYPE_UKM) btnCalculateSetor.setVisibility(View.GONE);

        inpSetor = view.findViewById(R.id.paypphsetor_inp_setoramount);
        inpSetor.constraintMandatory = true;
        inpSetor.constraintNoZero = true;
        inpSetor.constraintMax = 9;

        inpTerbilang = view.findViewById(R.id.paypphsetor_inp_terbilang);

        inpSetor.setConstraintMoney(inpTerbilang);

        btnNext = view.findViewById(R.id.paypphsetor_btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });

        validator = new Validator();
        validator.addEdit(inpSetor);

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

    @Deprecated
    private void setengahPersen(){
        long setor = inpSetor.getMoneyLongNotNull();
        setor *= 0.005;
        inpSetor.setText(String.valueOf(setor));
    }

    private void showSetengahPersenDialog(){

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_setengahpersen, null);
        final AppEditText etBruto = customLayout.findViewById(R.id.paytax3setorsetengah_inp_bruto);
        etBruto.constraintNoZero = true;
        etBruto.setConstraintMoney(null);
        new AlertDialog.Builder(getActivity(), R.style.ConfirmDialogTheme)
                .setTitle(getString(R.string.paytax3setor_dialogsetengah_title))
                .setView(customLayout)
                .setPositiveButton(R.string.paytax3setor_dialogsetengah_btncalculate, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        long setor = etBruto.getMoneyLongNotNull();
                        setor *= 0.005;
                        inpSetor.setText(String.valueOf(setor));
                    }})
                .setNegativeButton(R.string.global_cancel, null).show();
    }

    public void init(){

        WrappedSsp wrappedSsp = payTaxActivity.wrappedSsp;

        taxWPHeader.setIcon(wrappedSsp.fetchDefaultTaxType().fetchIcon(), wrappedSsp.fetchDefaultTaxType().code);
        taxWPHeader.setHeadName(wrappedSsp.fetchDefaultName());
        taxWPHeader.setNpwp(wrappedSsp.npwpNotNull());
        taxWPHeader.setTaxAccountPeriod(wrappedSsp.fetchDefaultTaxType(), wrappedSsp.fetchDefaultTaxSlipType(), wrappedSsp.fetchTaxPeriod(getContext()));

        if(wrappedSsp.amount != 0) inpSetor.setText( String.valueOf(wrappedSsp.amount) );
    }

    private void clickRightMenu(int id){
        switch (id){
            case R.id.paytaxsetor_btnmenu_quickhelp:
                clickQuickHelp();
                break;
        }
    }

    public void showQuickHelp(){
        if(payTaxActivity.pager.getCurrentItem() != PayTax3SetorFragment.VIEW_PAGER_CODE) return;
        String sp = AppConstant.SP_QH_PAYSETOR;
        boolean b = payTaxActivity.getSpBool(sp);
        if(b) return;
        clickQuickHelp();
        payTaxActivity.setSpBool(sp);
    }

    private void clickQuickHelp(){

        List<BubbleShowCaseBuilder> listSc = new ArrayList<>();
        BubbleShowCaseBuilder sc;

        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsetor_qhelptitle_calculate), getString(R.string.paytaxsetor_qhelpbody_calculate), (View)btnCalculateSetor.getParent(), btnCalculateSetor);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsetor_qhelptitle_setor), getString(R.string.paytaxsetor_qhelpbody_setor), (View)inpSetor.getParent(), inpSetor);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsetor_qhelptitle_terbilang), getString(R.string.paytaxsetor_qhelpbody_terbilang), (View)inpTerbilang.getParent(), inpTerbilang);
        if(sc != null){
            listSc.add(sc);
        }
        sc = Utility.createShowCase(getActivity(), getString(R.string.paytaxsetor_qhelptitle_btnnext), getString(R.string.paytaxsetor_qhelpbody_btnnext), (View)btnNext.getParent(), btnNext);
        if(sc != null){
            listSc.add(sc);
        }

        BubbleShowCaseSequence scs = new BubbleShowCaseSequence();
        scs.addShowCases(listSc);
        scs.show();

    }

    private void clickNext(){
        if(!validator.check(getContext())) return;

        // set request SSP
        payTaxActivity.wrappedSsp.amount = inpSetor.getMoneyLongNotNull();

        Kjs kjs = payTaxActivity.wrappedSsp.taxSlipType;
        if(kjs.noSkActive || kjs.nopActive || kjs.subjekPajakActive){
            payTaxActivity.invokeFragmentInit(PayTax4SkNopFragment.VIEW_PAGER_CODE);
            payTaxActivity.pager.setCurrentItem(PayTax4SkNopFragment.VIEW_PAGER_CODE);
        }else{
            payTaxActivity.invokeFragmentInit(PayTax5SummaryFragment.VIEW_PAGER_CODE);
            payTaxActivity.pager.setCurrentItem(PayTax5SummaryFragment.VIEW_PAGER_CODE);
        }


    }

}
