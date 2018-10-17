package com.example.myteam.codia.data.source.remote.auth;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenicationRemoteDataSource implements AuthenicationDataSource.RemoteDataSource {

    protected FirebaseAuth mFirebaseAuth;

    public AuthenicationRemoteDataSource() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void register(String email, String password, final DataCallback<FirebaseUser> callBack) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        getResponse(task, callBack);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onGetDataFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void registerUser() {

    }

    @Override
    public boolean checkCode(int code) {
        return false;
    }

    @Override
    public void getCurrentUser(final DataCallback<FirebaseUser> callBack) {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user == null) {
            callBack.onGetDataFailed(null);
        } else {
            callBack.onGetDataSuccess(user);
        }
    }

    @Override
    public void signIn(String email, String password, final DataCallback<FirebaseUser> callBack) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        getResponse(task, callBack);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onGetDataFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void signOut(DataCallback<FirebaseUser> callback) {

    }

    @Override
    public void resetPassword(String email, DataCallback callback) {

    }

    @Override
    public void updateProfile(String userName, Uri photo, DataCallback callback) {

    }

    @Override
    public void changePassword(FirebaseUser firebaseUser, String newPassword, ChangePasswordCallBack callback) {

    }

    private void getResponse(Task<AuthResult> authResultTask, DataCallback callback) {
        if (authResultTask == null) {
            callback.onGetDataFailed(null);
            return;
        }
        if (!authResultTask.isSuccessful()) {
            String message = authResultTask.getException().getMessage();
            callback.onGetDataFailed(message);
            return;
        }
        callback.onGetDataSuccess(authResultTask.getResult().getUser());
    }
}
