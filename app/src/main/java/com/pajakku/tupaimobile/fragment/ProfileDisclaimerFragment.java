package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.os.Bundle;
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
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ProfileDisclaimerFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 2;

//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public ProfileDisclaimerFragment() {
        // Required empty public constructor
    }


//    public static ProfileDisclaimerFragment newInstance(String param1, String param2) {
//        ProfileDisclaimerFragment fragment = new ProfileDisclaimerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_disclaimer, container, false);

        final NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.fragprof_pager);

        AppActionBar actionBar =  view.findViewById(R.id.profdisclaimer_actionbar);
        actionBar.init(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(ProfileHomeFragment.VIEW_PAGER_CODE);
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
