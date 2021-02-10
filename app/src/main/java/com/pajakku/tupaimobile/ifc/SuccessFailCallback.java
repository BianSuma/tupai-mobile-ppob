package com.pajakku.tupaimobile.ifc;

/**
 * Created by dul on 06/03/19.
 */
public interface SuccessFailCallback<T,U> {
    void onSuccess(T data);
    void onFail(U data);
}
