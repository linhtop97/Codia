package com.example.myteam.codia.data.source.remote.friend;

import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.friend.FriendCallBack;

import java.util.List;

public class FriendRepository implements FriendDataSource.RemoteDataSource {

    private static final String TAG = "FriendRepository";
    private FriendRemoteDataSource mFriendRemoteDataSource;

    public FriendRepository(FriendRemoteDataSource friendRemoteDataSource) {
        mFriendRemoteDataSource = friendRemoteDataSource;
    }

    @Override
    public void sentRequest(String uidUser, String userReceive, FriendCallBack.FriendSentCallBack callBack) {
        mFriendRemoteDataSource.sentRequest(uidUser, userReceive, callBack);
    }

    @Override
    public void getAllFriendRequest(String uidUser, DataCallback<List<User>> callback) {
        mFriendRemoteDataSource.getAllFriendRequest(uidUser, callback);
    }

    @Override
    public void checkFriend(String uidUser, String uidProfileUser, CheckFriendCallBack callBack) {
        mFriendRemoteDataSource.checkFriend(uidUser, uidProfileUser, callBack);
    }

    @Override
    public void acceptFriendRequest(String uidUser, String uidUserRequest, FriendCallBack.FriendAnswerCallBack callBack) {
        mFriendRemoteDataSource.acceptFriendRequest(uidUser, uidUserRequest, callBack);
    }

    @Override
    public void declineFriendRequest(String uidUser, String uidUserRequest, FriendCallBack.FriendAnswerCallBack callBack) {
        mFriendRemoteDataSource.declineFriendRequest(uidUser, uidUserRequest, callBack);
    }

    @Override
    public void getAllFriend(String uidUser, DataCallback<List<User>> callback) {
        mFriendRemoteDataSource.getAllFriend(uidUser, callback);
    }
}
