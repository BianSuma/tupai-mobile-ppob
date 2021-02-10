package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class HelpPayTaxFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 1;

    public HelpPayTaxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_payssp, container, false);

        final NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.fraghelp_pager);

        AppActionBar actionBar = view.findViewById(R.id.helppayssp_appactionbar);
        actionBar.init(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(HelpHomeFragment.VIEW_PAGER_CODE);
            }
        }, null);

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

}
