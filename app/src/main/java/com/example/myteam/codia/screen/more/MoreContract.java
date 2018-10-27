package com.example.myteam.codia.screen.more;

import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

public interface MoreContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void showDialog();

        void dismissDialog();

        void onShowProfileClick();

        void getProfileSuccessful(User user);

        void getProfileError(int message);

        void onLogoutClick();

        void onEditProfileClick();

        void logoutSuccessful();

    }

    interface Presenter extends BasePresenter {
        void getProfile();

        void logout();
    }
}
