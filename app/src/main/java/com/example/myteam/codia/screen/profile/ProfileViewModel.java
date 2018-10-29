package com.example.myteam.codia.screen.profile;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.myteam.codia.R;
import com.example.myteam.codia.screen.post.PostActivity;
import com.example.myteam.codia.utils.navigator.Navigator;

public class ProfileViewModel implements ProfileContract.ViewModel {

    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;
    private ProfileActivity mProfileActivity;

    public ProfileViewModel(Context context, Navigator navigator) {
        mContext = context;
        mProfileActivity = (ProfileActivity) context;
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
    public void onAddTimeLineClick() {
        mNavigator.startActivity(PostActivity.class);
    }

    @Override
    public void onAddFriendClick() {

    }

    @Override
    public void onFriendClick() {

    }

    @Override
    public void onMessageClick() {

    }

    @Override
    public void onPlanClick() {

    }

    @Override
    public void onEditProfileClick() {

    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onViewPictureClick() {

    }

    @Override
    public void onViewFriendClick() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {

    }
}
