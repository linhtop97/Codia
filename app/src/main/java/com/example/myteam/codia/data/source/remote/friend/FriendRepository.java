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
    public void sentRequest(String uidUser, String userReceive, FriendCallBack callBack) {
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
}
