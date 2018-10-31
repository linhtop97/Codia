package com.example.myteam.codia.data.source.remote.friend;

import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.friend.FriendCallBack;

import java.util.List;

public interface FriendDataSource {
    interface RemoteDataSource {

        void sentRequest(String uidUser, String userReceive, FriendCallBack callback);

        void getAllFriendRequest(String uidUser, DataCallback<List<User>> callback);

        void checkFriend(String uidUser, String uidProfileUser, CheckFriendCallBack callBack);
    }
}
