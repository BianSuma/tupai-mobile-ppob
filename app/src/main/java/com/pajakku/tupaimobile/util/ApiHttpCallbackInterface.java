package com.pajakku.tupaimobile.util;

import com.pajakku.tupaimobile.api.Http;
import com.pajakku.tupaimobile.model.dto.response.ResponseError;

import retrofit2.Call;

/**
 * Created by dul on 16/01/19.
 */

public interface ApiHttpCallbackInterface<T,U> {

    Call<T> requestMethod(Http httpService, String bearerAuth);
    void onSuccess(U response);
    void onFail(ResponseError error);
}
