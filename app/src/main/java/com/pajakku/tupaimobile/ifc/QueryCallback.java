package com.pajakku.tupaimobile.ifc;

/**
 * Created by dul on 06/03/19.
 */
public interface QueryCallback<T> {
    T onProcess();
    void onSuccess(T data);
}
