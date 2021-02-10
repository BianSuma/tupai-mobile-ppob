package com.pajakku.tupaimobile.util;

import android.os.AsyncTask;

import com.pajakku.tupaimobile.ifc.QueryCallback;


public class QueryAsyncTask<T> extends AsyncTask<Void, Void, T> {

    private QueryCallback<T> listener;

    public QueryAsyncTask(QueryCallback<T> listener) {
        this.listener = listener;
    }

    @Override
    protected T doInBackground(Void... params) {
        return listener.onProcess();
    }

    @Override
    protected void onPostExecute(T result) {
        listener.onSuccess(result);
    }
}
