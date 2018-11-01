package com.example.myteam.codia.screen.profile;

import com.example.myteam.codia.data.model.User;

public interface UserDataCallBack {
    void getUserCodiaSuccessful(User user);

    void  getUserCodiaFailed();
}
