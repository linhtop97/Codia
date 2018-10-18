package com.example.myteam.codia.screen.authentication.register;

import android.text.TextUtils;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsApi;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.utils.Constant;

public class RegisterPresenter implements RegisterContract.Presenter, DataCallback<String> {

    private RegisterContract.ViewModel mViewModel;
    private AuthenicationRepository mRepository;
    private SharedPrefsApi mSharedPrefs;

    public RegisterPresenter(RegisterContract.ViewModel viewModel, AuthenicationRepository repository,
                             SharedPrefsApi sharedPrefs) {
        mViewModel = viewModel;
        mRepository = repository;
        mSharedPrefs = sharedPrefs;
    }

    @Override
    public void checkEmailIsUsed(String email, EmailExistsCallback callback) {
        mRepository.checkEmailIsUsed(email, callback);
    }

    @Override
    public void sendConfirmCode(String email) {
        mRepository.sendConfirmCode(email, this);
        mViewModel.showDialog();
    }

    @Override
    public void register(String userName, String displayName, String password) {

    }

    @Override
    public boolean validateDataInput(String email, String displayName, String password) {
        if (TextUtils.isEmpty(email)) {
            mViewModel.onInputError(R.string.email_is_empty);
            return false;
        }
        if (!TextUtils.isEmpty(email) && !email.matches(Constant.EMAIL_FORMAT)) {
            mViewModel.onInputError(R.string.invalid_email_format);
            return false;
        }
        if (TextUtils.isEmpty(displayName)) {
            mViewModel.onInputError(R.string.display_name_is_empty);
            return false;
        }
        if (!TextUtils.isEmpty(displayName)
                && (displayName.length() < Constant.MINIMUM_CHARACTERS_PASSWORD)
                || (displayName.length() > Constant.MAXIMUM_CHARACTERS_PASSWORD)) {
            mViewModel.onInputError(R.string.least_6_characters_full_is_32);
            return false;
        }
        if (!TextUtils.isEmpty(displayName) && !displayName.matches(Constant.DISPLAY_NAME_FORMAT)) {
            mViewModel.onInputError(R.string.invalid_display_name_format);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mViewModel.onInputError(R.string.password_is_empty);
            return false;
        }
        if (!TextUtils.isEmpty(password)
                && password.length() < Constant.MINIMUM_CHARACTERS_PASSWORD) {
            mViewModel.onInputError(R.string.least_6_characters);
            return false;
        }
        if (!TextUtils.isEmpty(password) && !password.matches(Constant.PASSWORD_FORMAT)) {
            mViewModel.onInputError(R.string.invalid_password_format);
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onGetDataSuccess(String data) {
        mViewModel.dismissDialog();
        mViewModel.onSendEmailSuccessful(data);
    }

    @Override
    public void onGetDataFailed(String msg) {
        mViewModel.dismissDialog();
        mViewModel.onSendEmailError(msg);
    }
}
