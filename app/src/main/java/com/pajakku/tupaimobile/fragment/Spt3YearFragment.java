package com.pajakku.tupaimobile.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.SptActivity;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.model.SptDraft;
import com.pajakku.tupaimobile.model.dto.YearInt;
import com.pajakku.tupaimobile.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Spt3YearFragment extends Fragment {

    public static final int VIEW_PAGER_CODE = 3;

    private SptActivity sptActivity;

    private AppEditText etYear;
    private AppEditText etPembetulanKe;
    private RadioGroup radioGroup;

    private Validator validator;

    public Spt3YearFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_spt3_year, container, false);

        etYear = v.findViewById(R.id.spt1year_inp_year);
        etYear.constraintMandatory = true;
        etYear.constraintMin = 4;
        etYear.constraintMax = 4;
        etYear.setConstraintNumber();

        etPembetulanKe = v.findViewById(R.id.spt2year_inp_pembetulanke);
        etPembetulanKe.constraintMandatory = true;
        etPembetulanKe.constraintNoZero = true;
        etPembetulanKe.constraintMin = 1;
        etPembetulanKe.constraintMax = 2;
        etPembetulanKe.setConstraintNumber();
        etPembetulanKe.setText("1");
        etPembetulanKe.setVisibility(View.GONE);

        radioGroup = v.findViewById(R.id.spt1year_inp_pembetulan);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.spt1year_inp_pembetulanke){
                    etPembetulanKe.setVisibility(View.VISIBLE);
                }else{
                    etPembetulanKe.setVisibility(View.GONE);
                }
            }
        });

        Button btnNext = v.findViewById(R.id.spt2year_btn_createdraft);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });

        validator = new Validator();
        validator.addEdit(etYear);
        validator.addEdit(etPembetulanKe);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SptActivity) {
            sptActivity = (SptActivity) this.getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sptActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        List<YearInt> items = new ArrayList<>();
        YearInt yi;
        for(int i=1; i<4;i++){
            yi = new YearInt();
            yi.year = 2011 + i;
            items.add(yi);
        }

    }

    private void setPembetulan(int p){
        if( p == 0 ) {
            radioGroup.check(R.id.spt1year_inp_pembetulannormal);
            return;
        }

        radioGroup.check(R.id.spt1year_inp_pembetulanke);
        etPembetulanKe.setText(Integer.toString(p));
    }

    private int fetchPembetulan(){
        if( radioGroup.getCheckedRadioButtonId() != R.id.spt1year_inp_pembetulanke ){
            return 0;
        }

        return etPembetulanKe.getIntNotNull();
    }

    public void setViewData(){
        SptDraft sd = sptActivity.sptDraft;

        etYear.setText( Integer.toString(sd.year) );
        setPembetulan(sd.pembetulan);
    }

    public void clickNext(){
        if(!validator.check(getContext())) return;

        SptDraft sd = sptActivity.sptDraft;
        sd.year = etYear.getIntNotNull();
        sd.pembetulan = fetchPembetulan();

        sptActivity.viewPager.setCurrentItem(Spt4WpExtraFragment.VIEW_PAGER_CODE);
        sptActivity.invokeSetViewData(Spt4WpExtraFragment.VIEW_PAGER_CODE);
    }

}
