package com.example.myteam.codia.screen.main;

import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

public interface MainContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void showDialog();

        void dismissDialog();

        void onLoginCurrentUser();

        void onLogout();
    }

    interface Presenter extends BasePresenter {
    }
}
