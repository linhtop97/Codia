package com.example.myteam.codia.screen.authentication.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.myteam.codia.BR;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.screen.authentication.confirm.RegisterConfirmActivity;
import com.example.myteam.codia.utils.navigator.Navigator;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_DISPLAYNAME;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_EMAIL_REGISTER;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PASSWORD_REGISTER;

public class RegisterViewModel extends BaseObservable implements RegisterContract.ViewModel, EmailExistsCallback {

    private RegisterContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;
    private String mEmail;
    private String mPassword;
    private String mDisplayname;
    private SharedPrefsImpl mSharedPrefs;

    RegisterViewModel(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.msg_register));
        mSharedPrefs = new SharedPrefsImpl(mContext);
        mEmail = "";
        mDisplayname = "";
        mPassword = "";
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getDisplayname() {
        return mDisplayname;
    }

    public void setDisplayname(String displayname) {
        mDisplayname = displayname;
        notifyPropertyChanged(BR.displayname);
    }


    @Override
    public void onRegisterClick() {
        if (!mPresenter.validateDataInput(mEmail, mDisplayname, mPassword)) {
            return;
        }
        mPresenter.checkEmailIsUsed(mEmail, this);
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
    public void onSendEmailSuccessful(String message) {
        //save infor account register
        mSharedPrefs.put(PREF_EMAIL_REGISTER, mEmail);
        mSharedPrefs.put(PREF_PASSWORD_REGISTER, mPassword);
        mSharedPrefs.put(PREF_DISPLAYNAME, mDisplayname);
        //go to confirm register activity
        mNavigator.startActivityForResult(RegisterConfirmActivity
                .getInstance(mContext), RegisterActivity.REQUEST_CODE);
    }

    @Override
    public void onSendEmailError(String message) {
        mNavigator.showToast(message);
    }

    @Override
    public void onInputError(int message) {
        mNavigator.showToast(message);
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
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onEmailExists(Boolean b) {
        if (b) {
            mNavigator.showToast(R.string.email_is_used);
        } else {
            mPresenter.sendConfirmCode(mEmail);
        }
    }
}
