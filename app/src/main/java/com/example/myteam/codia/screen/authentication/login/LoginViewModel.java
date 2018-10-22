package com.example.myteam.codia.screen.authentication.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

import com.example.myteam.codia.BR;
import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.screen.authentication.register.RegisterActivity;
import com.example.myteam.codia.screen.main.MainActivity;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginViewModel extends BaseObservable implements LoginContract.ViewModel {

    private LoginContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;
    private String mEmail;
    private String mPassword;
    private boolean mIsRememberAccount;
    private SharedPrefsImpl mSharedPrefs;

    public LoginViewModel(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDialog = new ProgressDialog(context);
        mSharedPrefs = new SharedPrefsImpl(context);
        mDialog.setMessage(context.getString(R.string.msg_loading));
    }

    @Override
    public void onRegisterClick() {
        mNavigator.startActivityForResult(RegisterActivity.getInstance(mContext), LoginActivity.REQUEST_CODE);
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
        try {
            String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mSharedPrefs.put(SharedPrefsKey.PREF_USER_ID, UserId);
            DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference().child(User.UserEntity.USERS).child(UserId);
            mUserRef.child(User.UserEntity.ISONLINE).setValue(true);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
        }
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
}
