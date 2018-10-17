package com.example.myteam.codia.data.source.remote.auth;

import android.net.Uri;
import com.google.firebase.auth.FirebaseUser;

public interface AuthenicationDataSource {
    interface RemoteDataSource {
        void register(String email, String password, DataCallback<FirebaseUser> callBack);

        void registerUser();

        boolean checkCode(int code);

        void getCurrentUser(DataCallback<FirebaseUser> callBack);

        void signIn(String email, String password, DataCallback<FirebaseUser> callBack);

        void signOut(DataCallback<FirebaseUser> callback);

        void resetPassword(String email, DataCallback callback);

        void updateProfile(String userName, Uri photo, DataCallback callback);

        void changePassword(FirebaseUser firebaseUser, String newPassword,
                            ChangePasswordCallBack callback);
    }
}
