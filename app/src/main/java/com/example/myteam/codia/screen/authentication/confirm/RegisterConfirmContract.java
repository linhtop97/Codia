package com.example.myteam.codia.screen.authentication.confirm;

import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;
import com.google.firebase.auth.FirebaseUser;

public interface RegisterConfirmContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void showDialog();

        void dismissDialog();

        void onRegisterSuccessful(FirebaseUser firebaseUser);

        void onRegisterError(String message);

        void onVerifyCodeError(int message);

    }

    interface Presenter extends BasePresenter {
        void checkCode(String code);

        void registerUser(String email, String displayName, FirebaseUser user, CreateUserCallback callback);
    }
}
