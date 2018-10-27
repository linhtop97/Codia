package com.example.myteam.codia.screen.more;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.screen.authentication.login.LoginActivity;
import com.example.myteam.codia.screen.editprofile.EditProfileFragment;
import com.example.myteam.codia.screen.main.MainActivity;
import com.example.myteam.codia.screen.profile.ProfileActivity;
import com.example.myteam.codia.utils.navigator.Navigator;

public class MoreViewModel extends BaseObservable implements MoreContract.ViewModel {

    private MoreContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;
    private MainActivity mMainActivity;

    public MoreViewModel(Context context, Navigator navigator) {
        mContext = context;
        mMainActivity = (MainActivity) context;
        mNavigator = navigator;
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.msg_loading));
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
    public void onShowProfileClick() {
        mPresenter.getProfile();
    }

    @Override
    public void getProfileSuccessful(User user) {
        //view profile, send user to user profile
        mNavigator.startActivity(ProfileActivity.getInstance(mMainActivity, user));
    }

    @Override
    public void getProfileError(int message) {

    }

    @Override
    public void onLogoutClick() {
        mPresenter.logout();
    }

    @Override
    public void onEditProfileClick() {
        //new edit profile fragment is heref
        EditProfileFragment fragment = EditProfileFragment.newInstance();
        mNavigator.addFragment(R.id.main_container, fragment, true, Navigator.NavigateAnim.BOTTOM_UP, fragment.getClass().getSimpleName());
    }

    @Override
    public void logoutSuccessful() {
        mNavigator.finishActivity();
        mNavigator.startActivity(LoginActivity.class);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(MoreContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
