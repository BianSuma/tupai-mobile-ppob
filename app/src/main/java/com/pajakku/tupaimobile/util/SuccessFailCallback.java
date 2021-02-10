package com.pajakku.tupaimobile.util;

/**
 * Created by dul on 06/03/19.
 */

@Deprecated  // package ifc
public interface SuccessFailCallback<T,U> {
    void onSuccess(T data);
    boolean onFail(U data);
}
