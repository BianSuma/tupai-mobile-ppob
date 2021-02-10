package com.pajakku.tupaimobile.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.activity.BaseActivity;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.component.AppEditText;
import com.pajakku.tupaimobile.component.NonSwipeableViewPager;
import com.pajakku.tupaimobile.model.dto.request.RequestFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;
import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.util.HttpCallbackInterfaceSimple;
import com.pajakku.tupaimobile.util.Validator;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HelpCallUsFragment extends Fragment {
    public static final int VIEW_PAGER_CODE = 3;

    private BaseActivity baseActivity;

//    private AppEditText inpProblem;
    private AppEditText inpProblemDetail;

    private Validator validator;

    public HelpCallUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_call_us, container, false);

        final NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.fraghelp_pager);

        AppActionBar actionBar = view.findViewById(R.id.helpcallus_actionbar);
        actionBar.init(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(HelpHomeFragment.VIEW_PAGER_CODE);
            }
        }, null);

//        if((inpProblem = view.findViewById(R.id.helpcallus_input_problem)) == null) throw new RuntimeException();
//        inpProblem.constraintMandatory = true;
        if((inpProblemDetail = view.findViewById(R.id.helpcallus_inp_problemdetail)) == null) throw new RuntimeException();
        inpProblemDetail.constraintMandatory = true;

        Button btnSend = view.findViewById(R.id.helpcallus_btn_send);
        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            clickSend();
            }
        });

        validator = new Validator();
//        validator.addEdit(inpProblem);
        validator.addEdit(inpProblemDetail);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            baseActivity = (BaseActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActivity = null;
    }

    private void clickSend(){
        if( ! validator.check(getContext()) ) return;

        final RequestFinnet msg = new RequestFinnet();
        msg.requestType = RequestFinnet.REQUESTTYPE_SENDPROBLEM;
//        msg.transType = inpProblem.getText();
        msg.transDesc = inpProblemDetail.getText();

        baseActivity.requestHttpSimple(true, R.string.progressdialog_sendproblem, true, null, true, new HttpCallbackInterfaceSimple<ResponseBody>() {
            @Override
            public Call<ResponseBody> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId){
                return httpService.callUs(bearerAuth, msg);
            }

            @Override
            public void onSuccess(ResponseBody response){
                Toast.makeText(baseActivity, R.string.helpcallus_toast_sentmsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onFail(ResponseError re){
                return true;
            }
        });
    }
}
