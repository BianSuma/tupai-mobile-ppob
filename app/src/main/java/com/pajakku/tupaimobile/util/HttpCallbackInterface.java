package com.pajakku.tupaimobile.util;

import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by dul on 16/01/19.
 */

@Deprecated  // ApiHttpCallbackInterface
public interface HttpCallbackInterface<T,U> {

    Call<T> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId);
    Call<ResponseBody> requestMethodStream(Http httpService, String bearerAuth, long companyId, long subscriptionId);
    void onSuccess(T response);
    void onSuccessStream(List<U> response);
    boolean onFail(ResponseError error);  // if return true throw default toast
}
