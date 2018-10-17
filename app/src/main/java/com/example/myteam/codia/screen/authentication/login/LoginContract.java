package com.example.myteam.codia.screen.authentication.login;

import android.content.Intent;

import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;
import com.google.firebase.auth.FirebaseUser;

/**
 * This specifies the contract between the view and the presenter.
 */
public class LoginContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void onRegisterClick();

        void showDialog();

        void dismissDialog();

        void onGetCurrentUserError(String message);

        void onGetUserSuccessful(FirebaseUser firebaseUser);

        void onLoginError(String message);

        void onLoginClick();

        void onForgotPasswordClick();

        void onInputError(int message);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onGetLastEmail(String s);

        void onGetLastPassword(String s);

        void onGetIsRememberAccount(Boolean b);
    }

    interface Presenter extends BasePresenter {
        void login(String userName, String passWord, boolean isRememberAccount);

        boolean validateDataInput(String email, String password);
    }
}
