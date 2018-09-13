package com.example.myteam.codia.screen.authentication.login;

import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
public class LoginContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void onLoginError(String msg);

        void onLoginSuccess();

        void onInputUserNameError();

        void onInputPasswordError();

        void showProgressbar();

        void hideProgressbar();

        void onCachedAccountLoaded(String user, String passWord);

        boolean isRememberAccount();
    }

    interface Presenter extends BasePresenter {
        void login(String userName, String passWord);

        boolean validateDataInput(String username, String password);
    }
}
