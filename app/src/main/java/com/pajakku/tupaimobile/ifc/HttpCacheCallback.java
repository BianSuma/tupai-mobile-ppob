package com.pajakku.tupaimobile.ifc;


import com.pajakku.tupaimobile.model.dto.ResponseDTO;
import com.pajakku.tupaimobile.util.CommonCallback;

import retrofit2.Call;

/**
 * Created by dul on 16/01/19.
 */

public interface HttpCacheCallback<T, U> {

    Call<T> requestMethod(String bearerAuth);
    void onSuccess(U data);
    void onFail(ResponseDTO error);
    U apiConvertResponse(T d);
    void setCache(final U d);
    void getCache(final CommonCallback<U> cacheCb);
}
