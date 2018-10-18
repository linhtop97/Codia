package com.example.myteam.codia.screen.authentication.register;

import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface RegisterContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void onRegisterClick();

        void showDialog();

        void dismissDialog();

        void onSendEmailSuccessful(String message);

        void onSendEmailError(String message);

        void onInputError(int message);
    }

    interface Presenter extends BasePresenter {
        void checkEmailIsUsed(String email, EmailExistsCallback callback);

        void sendConfirmCode(String email);

        void register(String userName, String displayName, String password);

        boolean validateDataInput(String email, String displayName, String password);
    }
}
