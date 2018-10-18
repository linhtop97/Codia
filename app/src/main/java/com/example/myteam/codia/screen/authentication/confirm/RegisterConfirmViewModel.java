package com.example.myteam.codia.screen.authentication.confirm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;;

import com.android.databinding.library.baseAdapters.BR;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.auth.FirebaseUser;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_DISPLAYNAME;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_EMAIL_REGISTER;

public class RegisterConfirmViewModel extends BaseObservable implements RegisterConfirmContract.ViewModel, CreateUserCallback {
    private RegisterConfirmContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;
    private String mCode;
    private SharedPrefsImpl mSharedPrefs;
    private RegisterConfirmActivity mActivity;

    @Bindable
    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
        notifyPropertyChanged(BR.code);
    }

    RegisterConfirmViewModel(Context context, Navigator navigator) {
        mContext = context;
        mActivity = (RegisterConfirmActivity) context;
        mNavigator = navigator;
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.msg_register));
        mSharedPrefs = new SharedPrefsImpl(mContext);
        mCode = "";
    }

    @Override
    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onRegisterSuccessful(FirebaseUser firebaseUser) {
        mPresenter.registerUser(mSharedPrefs.get(PREF_EMAIL_REGISTER, String.class),
                mSharedPrefs.get(PREF_DISPLAYNAME, String.class), firebaseUser, this);
    }

    @Override
    public void onRegisterError(String message) {
        mNavigator.showToast(message);
    }

    @Override
    public void onVerifyCodeError(int message) {
        mNavigator.showToast(message);
    }

    public void confirmClick() {
        mPresenter.checkCode(getCode());
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(RegisterConfirmContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreateUserSuccessful() {
        //login and show result success
        mNavigator.showToast(R.string.create_account_successful);
        //comeback register scrren -> login
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }

    @Override
    public void onCreateUserFailed(int message) {
        mNavigator.showToast(message);
    }
}

