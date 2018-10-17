package com.example.myteam.codia.screen.authentication.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.ImageView;

import com.example.myteam.codia.BR;
import com.example.myteam.codia.R;
import com.example.myteam.codia.screen.main.MainActivity;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends BaseObservable implements LoginContract.ViewModel {

    private LoginContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;
    private ImageView mImageView;
    private String mEmail;
    private String mPassword;
    private String mEmailError;
    private String mPasswordError;
    private boolean mIsRememberAccount;
    private LoginActivity mActivity;

    LoginViewModel(Context context, Navigator navigator) {
        mContext = context;
        mActivity = (LoginActivity) context;
        mNavigator = navigator;
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.msg_loading));
    }

    @Override
    public void onRegisterClick() {

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
    public void onGetCurrentUserError(String message) {

    }

    @Override
    public void onGetUserSuccessful(FirebaseUser firebaseUser) {
        mNavigator.finishActivity();
        mNavigator.startActivity(MainActivity.getInstance(mContext));
    }

    @Override
    public void onLoginError(String msg) {
        mNavigator.showToast(msg);
    }

    @Override
    public void onLoginClick() {
        if (!mPresenter.validateDataInput(mEmail, mPassword)) {
            return;
        }
        mPresenter.login(mEmail, mPassword, mIsRememberAccount);
    }

    @Override
    public void onForgotPasswordClick() {

    }

    @Override
    public void onInputError(int message) {
        mNavigator.showToast(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onGetLastEmail(String s) {
        setEmail(s);
    }

    @Override
    public void onGetLastPassword(String s) {
        setPassword(s);
    }

    @Override
    public void onGetIsRememberAccount(Boolean b) {
        setRememberAccount(b);
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
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    @Bindable
    public boolean isRememberAccount() {
        return mIsRememberAccount;
    }

    @Bindable
    public String getEmailError() {
        return mEmailError;
    }

    @Bindable
    public String getPasswordError() {
        return mPasswordError;
    }

    public void setEmail(String email) {
        mEmail = email;
        notifyPropertyChanged(BR.email);
    }

    public void setPassword(String password) {
        mPassword = password;
        notifyPropertyChanged(BR.password);
    }

    public void setRememberAccount(boolean rememberAccount) {
        mIsRememberAccount = rememberAccount;
        notifyPropertyChanged(BR.rememberAccount);
    }

    public void setEmailError(String emailError) {
        mEmailError = emailError;
        notifyPropertyChanged(BR.emailError);
    }

    public void setPasswordError(String passwordError) {
        mPasswordError = passwordError;
        notifyPropertyChanged(BR.passwordError);
    }

    @Bindable
    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
        notifyPropertyChanged(BR.imageView);
    }
}
