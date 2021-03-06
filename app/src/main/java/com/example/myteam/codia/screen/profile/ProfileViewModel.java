package com.example.myteam.codia.screen.profile;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.data.source.remote.friend.CheckFriendCallBack;
import com.example.myteam.codia.data.source.remote.friend.FriendRemoteDataSource;
import com.example.myteam.codia.data.source.remote.friend.FriendRepository;
import com.example.myteam.codia.data.source.remote.timeline.TimelineRemoteDataSource;
import com.example.myteam.codia.data.source.remote.timeline.TimelineRepository;
import com.example.myteam.codia.screen.friend.FriendCallBack;
import com.example.myteam.codia.screen.post.PostActivity;
import com.example.myteam.codia.utils.navigator.Navigator;

import java.util.List;

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
    public void onAddFriendClick(String uidUser, String userReceive, FriendCallBack.FriendSentCallBack callBack) {
        //send request
        new FriendRepository(new FriendRemoteDataSource()).sentRequest(uidUser, userReceive, callBack);

    }

    @Override
    public void onCancelRequestFriendClick() {
        //cancel request
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
    public void getAllPostSuccessFull(List<Post> posts) {

    }

    @Override
    public void getAllUserPost(String uidUser, DataCallback<List<Post>> callback) {
        new TimelineRepository(new TimelineRemoteDataSource()).getListPost(uidUser, callback);
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

    public void checkSendRequest(String uidUser, String uidProfileUser, CheckFriendCallBack callBack) {
        //check user đã gửi request tới profile user chưa
        new FriendRepository(new FriendRemoteDataSource()).checkFriend(uidUser, uidProfileUser, callBack);
    }

    public void setUserProfile(String uidProflieUser, UserDataCallBack callBack) {
        new AuthenicationRepository(new AuthenicationRemoteDataSource()).getUserCodia(uidProflieUser, callBack);
    }
}
