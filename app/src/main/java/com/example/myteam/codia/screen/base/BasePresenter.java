package com.example.myteam.codia.screen.base;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
