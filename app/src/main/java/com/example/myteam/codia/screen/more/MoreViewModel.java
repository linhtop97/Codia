package com.example.myteam.codia.screen.more;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.utils.navigator.Navigator;

public class MoreViewModel extends BaseObservable implements MoreContract.ViewModel {

    private MoreContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;

    public MoreViewModel(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.msg_loading));
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void onShowProfileClick() {

    }

    @Override
    public void getProfileSuccessful(User user) {

    }

    @Override
    public void getProfileError(int message) {

    }

    @Override
    public void onLogoutClick() {

    }

    @Override
    public void logoutError(int message) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(MoreContract.Presenter presenter) {

    }
}
