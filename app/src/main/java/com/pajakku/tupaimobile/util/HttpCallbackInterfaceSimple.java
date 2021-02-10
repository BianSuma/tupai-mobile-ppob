package com.pajakku.tupaimobile.util;

import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;

import retrofit2.Call;

/**
 * Created by dul on 16/01/19.
 */

public interface HttpCallbackInterfaceSimple<T> {

    Call<T> requestMethod(Http httpService, String bearerAuth, long companyId, long subscriptionId);
    void onSuccess(T response);
    boolean onFail(ResponseError error);  // if return true throw default toast
}
