package com.example.myteam.codia.screen.authentication.confirm;

public interface CreateUserCallback {
    void onCreateUserSuccessful();

    void onCreateUserFailed(int message);
}
