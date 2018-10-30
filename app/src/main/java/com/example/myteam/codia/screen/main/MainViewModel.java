package com.example.myteam.codia.screen.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.utils.DateTimeUtils;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
    public void onLogout() {
        new AuthenicationRepository(new AuthenicationRemoteDataSource()).signOut();
        DatabaseReference userReference = FirebaseDatabase.getInstance()
                .getReference().child(User.UserEntity.USERS)
                .child(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class));
        userReference.child(User.UserEntity.ISONLINE).setValue(false);
        userReference.child(User.UserEntity.LASTLOGIN).setValue(DateTimeUtils.getCurrentTime());
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


    @Override
    public void createChat() {
        List<User> mUserList = getAllFriend();
//
//        boolean[] mCheckedList = new boolean[mUserList.size()];
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
//        mBuilder.setTitle(R.string.title_create_group_chat);
//        mBuilder.setMultiChoiceItems(mUserList.toArray(String.class), mCheckedList, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//
//            }
//        });
    }

    public List<User> getAllFriend() {
        final List<User> mUserList = new ArrayList<>();
        final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        mReference.child(User.UserEntity.USERS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.getKey();
                String name = (String) dataSnapshot.child(User.UserEntity.DISPLAYNAME).getValue();
                String email = (String) dataSnapshot.child(User.UserEntity.EMAIL).getValue();
                String avatar = (String) dataSnapshot.child(User.UserEntity.AVATAR).getValue();
                User user = new User.Builder().setId(id)
                        .setDisplayName(name).setEmail(email)
                        .setAvatar(avatar).build();
                mUserList.add(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mUserList;
    }
}
