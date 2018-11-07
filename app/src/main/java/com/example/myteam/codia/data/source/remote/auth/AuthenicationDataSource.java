package com.example.myteam.codia.data.source.remote.auth;

import android.net.Uri;

import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.screen.authentication.confirm.CreateUserCallback;
import com.example.myteam.codia.screen.authentication.register.EmailExistsCallback;
import com.example.myteam.codia.screen.profile.UserDataCallBack;
import com.google.firebase.auth.FirebaseUser;

public interface AuthenicationDataSource {
    interface RemoteDataSource {

        void getUserCodia(String userId, DataCallback<User> callback);

        void getUserCodia(String uidProfileUser, UserDataCallBack callback);

        void checkEmailIsUsed(String email, EmailExistsCallback callback);

        void sendConfirmCode(String email, DataCallback<String> callBack);

        void register(String email, String password, DataCallback<FirebaseUser> callBack);

        void registerUser(String email, String displayName, FirebaseUser user, CreateUserCallback callback);

        void getCurrentUser(DataCallback<FirebaseUser> callBack);

        void signIn(String email, String password, DataCallback<FirebaseUser> callBack);

        void signOut();

        void resetPassword(String email, DataCallback callback);

        void updateProfile(String userName, Uri photo, DataCallback callback);

        void changePassword(FirebaseUser firebaseUser, String newPassword,
                            ChangePasswordCallBack callback);


        void deleteUserFireBase(String uid);
    }
}
