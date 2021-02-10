package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.pager.HelpPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HelpFragment extends Fragment {

//    public static final int VIEW_PAGER_CODE = 3;  // TODO: @warn show SPT
    public static final int VIEW_PAGER_CODE = 2;  // TODO: @warn hide SPT

    public ViewPager viewPager;

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        List<Fragment> fragList = new ArrayList<>();
        fragList.add(new HelpHomeFragment());
        fragList.add(new HelpPayTaxFragment());
        fragList.add(new HelpSSPFragment());

        viewPager = view.findViewById(R.id.fraghelp_pager);
        final PagerAdapter adapter = new HelpPagerAdapter(getChildFragmentManager(), fragList);
        viewPager.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    public void onBackPressed() {
        viewPager.setCurrentItem(HelpHomeFragment.VIEW_PAGER_CODE);
    }

}
