package com.example.myteam.codia.screen.authentication.confirm;

import android.text.TextUtils;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsApi;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.google.firebase.auth.FirebaseUser;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_CODE;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_EMAIL_REGISTER;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PASSWORD_REGISTER;

public class RegisterConfirmPresenter implements RegisterConfirmContract.Presenter, DataCallback<FirebaseUser> {

    private RegisterConfirmContract.ViewModel mViewModel;
    private AuthenicationRepository mRepository;
    private SharedPrefsApi mSharedPrefs;

    public RegisterConfirmPresenter(RegisterConfirmContract.ViewModel viewModel, AuthenicationRepository repository,
                                    SharedPrefsApi sharedPrefs) {
        mViewModel = viewModel;
        mRepository = repository;
        mSharedPrefs = sharedPrefs;
    }

    @Override
    public void checkCode(String code) {
        if (TextUtils.isEmpty(code)) {
            mViewModel.onVerifyCodeError(R.string.is_empty);
        } else if (Integer.parseInt(code) == (mSharedPrefs.get(PREF_CODE, Integer.class))) {
            mRepository.register(mSharedPrefs.get(PREF_EMAIL_REGISTER, String.class),
                    mSharedPrefs.get(PREF_PASSWORD_REGISTER, String.class), this);
            mViewModel.showDialog();
        } else {
            mViewModel.onVerifyCodeError(R.string.do_not_match);

        }
    }

    @Override
    public void registerUser(String email, String displayName, FirebaseUser user, CreateUserCallback callback) {
        mRepository.registerUser(email, displayName, user, callback);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onGetDataSuccess(FirebaseUser data) {
        mViewModel.onRegisterSuccessful(data);
        mViewModel.dismissDialog();
    }

    @Override
    public void onGetDataFailed(String msg) {
        mViewModel.onRegisterError(msg);
        mViewModel.dismissDialog();
    }
}
