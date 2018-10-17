package com.example.myteam.codia.data.source.remote.auth;

public interface DataCallback<T> {
    void onGetDataSuccess(T data);

    void onGetDataFailed(String msg);
}
