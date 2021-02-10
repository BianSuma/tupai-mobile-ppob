package com.pajakku.tupaimobile.api;

import com.pajakku.tupaimobile.model.Wajibpajak;
import com.pajakku.tupaimobile.model.dto.TaxSlipResponse;
import com.pajakku.tupaimobile.model.dto.request.FirebaseToken;
import com.pajakku.tupaimobile.model.dto.request.RequestFinnet;
import com.pajakku.tupaimobile.model.dto.request.RequestRegister;
import com.pajakku.tupaimobile.model.dto.request.RequestResetPass;
import com.pajakku.tupaimobile.model.dto.request.RequestSsp;
import com.pajakku.tupaimobile.model.dto.request.UpdateTaxReceiptDTO;
import com.pajakku.tupaimobile.model.dto.response.AppStatusData;
import com.pajakku.tupaimobile.model.dto.response.ResponseAccessToken;
import com.pajakku.tupaimobile.model.dto.response.ResponseAuthorize;
import com.pajakku.tupaimobile.model.dto.response.ResponseFinnet;
import com.pajakku.tupaimobile.model.dto.response.ResponseMe;
import com.pajakku.tupaimobile.model.dto.response.ResponseRegister;
import com.pajakku.tupaimobile.model.dto.response.ResponseSsp;
import com.pajakku.tupaimobile.model.dto.response.TransLog;
import com.pajakku.tupaimobile.model.response.RespListTaxType;
import com.pajakku.tupaimobile.util.AppConstant;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dul on 17/12/18.
 */

// header stream list
//  x-pagination-page: 1
//  x-pagination-count: 33
//  x-pagination-limit: 50

// uri dont start with slash / (prefix)
@Deprecated  // @test
public interface ApiCallTest {

        @Deprecated
        @GET(AppConstant.TUPAIBACKEND_PREFIXURL +"testauth")
        Call<ResponseBody> testAuth(@Header(AppConstant.HEADER_AUTHORIZATION) String auth);
        //


}
