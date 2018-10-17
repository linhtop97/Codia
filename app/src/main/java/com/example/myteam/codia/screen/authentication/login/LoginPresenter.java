package com.example.myteam.codia.screen.authentication.login;

import android.text.TextUtils;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsApi;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.utils.Constant;
import com.google.firebase.auth.FirebaseUser;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_EMAIL;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_KEEP_LOGIN;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PASSWORD;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.ViewModel mViewModel;
    private AuthenicationRepository mRepository;
    private SharedPrefsApi mSharedPrefs;

    public LoginPresenter(LoginContract.ViewModel viewModel, AuthenicationRepository repository,
                          SharedPrefsApi sharedPrefs) {
        mViewModel = viewModel;
        mRepository = repository;
        mSharedPrefs = sharedPrefs;
    }

    @Override
    public void login(final String email, final String password, final boolean isRememberAccount) {
        mViewModel.showDialog();
        mRepository.signIn(email, password, new DataCallback<FirebaseUser>() {
            @Override
            public void onGetDataSuccess(FirebaseUser data) {
                mViewModel.onGetUserSuccessful(data);
                mViewModel.onInputError(R.string.dang_nhap_thanh_cong);
                mViewModel.dismissDialog();
                if (isRememberAccount) {
                    mSharedPrefs.put(PREF_EMAIL, email);
                    mSharedPrefs.put(PREF_PASSWORD, password);
                } else {
                    mSharedPrefs.put(PREF_EMAIL, "");
                    mSharedPrefs.put(PREF_PASSWORD, "");
                }
                mSharedPrefs.put(PREF_KEEP_LOGIN, isRememberAccount);
            }

            @Override
            public void onGetDataFailed(String msg) {
                mViewModel.dismissDialog();
                mViewModel.onLoginError(msg);
            }
        });
    }

    @Override
    public boolean validateDataInput(String email, String password) {
        boolean isValid = true;
        if (TextUtils.isEmpty(email)) {
            isValid = false;
            mViewModel.onInputError(R.string.is_empty);
        }
        if (TextUtils.isEmpty(password)) {
            isValid = false;
            mViewModel.onInputError(R.string.is_empty);
        }
        if (!TextUtils.isEmpty(email) && !email.matches(Constant.EMAIL_FORMAT)) {
            isValid = false;
            mViewModel.onInputError(R.string.invalid_email_format);
        }
        if (!TextUtils.isEmpty(password)
                && password.length() < Constant.MINIMUM_CHARACTERS_PASSWORD) {
            isValid = false;
            mViewModel.onInputError(R.string.least_6_characters);
        }
        return isValid;
    }

    @Override
    public void onStart() {
        if (mSharedPrefs.get(PREF_KEEP_LOGIN, Boolean.class)) {
            mViewModel.onGetIsRememberAccount(true);
            mViewModel.onGetLastEmail(mSharedPrefs.get(PREF_EMAIL, String.class));
            mViewModel.onGetLastPassword(mSharedPrefs.get(PREF_PASSWORD, String.class));
            mViewModel.onLoginClick();
        }
    }
    @Override
    public void onStop() {

    }
}
