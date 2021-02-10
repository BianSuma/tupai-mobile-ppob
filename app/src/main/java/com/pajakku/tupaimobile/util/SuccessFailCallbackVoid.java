package com.pajakku.tupaimobile.util;

/**
 * Created by dul on 06/03/19.
 */

@Deprecated  // package ifc
public interface SuccessFailCallbackVoid<T,U> {
    void onSuccess(T data);
    void onFail(U data);
}
