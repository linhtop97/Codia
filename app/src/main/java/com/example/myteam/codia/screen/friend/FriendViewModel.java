package com.example.myteam.codia.screen.friend;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.data.source.remote.friend.FriendRemoteDataSource;
import com.example.myteam.codia.data.source.remote.friend.FriendRepository;

import java.util.List;

public class FriendViewModel {
    private FriendRepository mRepository;
    private SharedPrefsImpl mSharedPrefs;

    public FriendViewModel() {
        mRepository = new FriendRepository(new FriendRemoteDataSource());
        mSharedPrefs = new SharedPrefsImpl(MainApplication.getInstance());
    }

    public void getAllFriendRequest(DataCallback<List<User>> callback) {
        mRepository.getAllFriendRequest(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class), callback);
    }

    public void acceptFriendRequest(String uiUser, String uidUserRequest, FriendCallBack.FriendAcceptCallBack callBack) {
        mRepository.acceptFriendRequest(uiUser, uidUserRequest, callBack);
    }
}
