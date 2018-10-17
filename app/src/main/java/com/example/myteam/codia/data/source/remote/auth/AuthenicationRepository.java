package com.example.myteam.codia.data.source.remote.auth;

import com.google.firebase.auth.FirebaseUser;

public class AuthenicationRepository {
    private AuthenicationDataSource.RemoteDataSource mRemoteDataSource;

    public AuthenicationRepository(AuthenicationDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public void register(String email, String password, DataCallback<FirebaseUser> callback) {
        mRemoteDataSource.register(email, password, callback);
    }

    public void signIn(String email, String password, DataCallback<FirebaseUser> callback) {
        mRemoteDataSource.signIn(email, password, callback);
    }

    public void getCurrentUser(DataCallback<FirebaseUser> callback) {
        mRemoteDataSource.getCurrentUser(callback);
    }
}
