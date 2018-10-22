package com.example.myteam.codia.screen.more;

import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;

public class MorePresenter implements MoreContract.Presenter {


    @Override
    public void getProfile(DataCallback<User> callback) {

    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
