package com.example.myteam.codia.screen.main;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_EMAIL;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PASSWORD;

public class MainViewModel implements MainContract.ViewModel, DataCallback<FirebaseUser> {
    private Context mContext;
    private Navigator mNavigator;
    private ProgressDialog mDialog;
    private SharedPrefsImpl mSharedPrefs;

    public MainViewModel(Context context, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mDialog = new ProgressDialog(context);
        mSharedPrefs = new SharedPrefsImpl(context);
        mDialog.setMessage(context.getString(R.string.msg_loading));
    }

    @Override
    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void onLoginCurrentUser() {
        String email = mSharedPrefs.get(PREF_EMAIL, String.class);
        String pw = mSharedPrefs.get(PREF_PASSWORD, String.class);
        new AuthenicationRemoteDataSource().signIn(email, pw, this);
        showDialog();
    }

    @Override
    public void onGetDataSuccess(FirebaseUser data) {
        DatabaseReference userReference = FirebaseDatabase.getInstance()
                .getReference().child(User.UserEntity.USERS)
                .child(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class));
        userReference.child(User.UserEntity.ISONLINE).setValue(true);
        dismissDialog();
    }

    @Override
    public void onGetDataFailed(String msg) {
        dismissDialog();

    }
}
