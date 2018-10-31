package com.example.myteam.codia.screen.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.chat.ChatActivity;
import com.example.myteam.codia.utils.DateTimeUtils;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void setOffline() {
        DatabaseReference userReference = FirebaseDatabase.getInstance()
                .getReference().child(User.UserEntity.USERS)
                .child(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class));
        userReference.child(User.UserEntity.ISONLINE).setValue(false);
        userReference.child(User.UserEntity.LASTLOGIN).setValue(DateTimeUtils.getCurrentTime());
    }

    @Override
    public void setOnline() {
        DatabaseReference userReference = FirebaseDatabase.getInstance()
                .getReference().child(User.UserEntity.USERS)
                .child(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class));
        userReference.child(User.UserEntity.ISONLINE).setValue(true);
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
        getAllFriend();
    }

    public void ShowDialogChooseFriend(final List<User> mUserList) {
        // list displayname to show
        String[] mDisplayNameList = new String[mUserList.size()];
        // list checked to show
        final boolean[] mCheckedItem = new boolean[mUserList.size()];

        // list result when click OK
        final ArrayList<Integer> mResultSelected = new ArrayList<>();

        // list result UserId
        final List<String> mResult = new ArrayList<>();

        for (int i = 0; i < mUserList.size(); i++) {
            mDisplayNameList[i] = mUserList.get(i).getDisplayName();
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setTitle(R.string.title_create_group_chat);
        mBuilder.setMultiChoiceItems(mDisplayNameList, mCheckedItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which, boolean isChecked) {
                if (isChecked) {
                    if (!mResultSelected.contains(which)) {
                        mResultSelected.add(which);
                    } else {
                        for (int i = 0; i < mResultSelected.size(); i++) {
                            if (mResultSelected.get(i) == which) mResultSelected.remove(i);
                        }
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Integer i : mResultSelected) {
                    mResult.add(mUserList.get(i).getId());
                    Toast.makeText(mContext, mUserList.get(i).getDisplayName(), Toast.LENGTH_SHORT).show();
                }
                if (mResult.size() == 0) return;
                else if (mResult.size() == 1) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra(User.UserEntity.ID, mResult.get(0));
                    mNavigator.startActivity(intent);
                } else {

                }
                dialog.dismiss();
            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < mCheckedItem.length; i++) {
                    mCheckedItem[i] = false;
                    mResultSelected.clear();
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public void getAllFriend() {
        final List<User> mUserList = new ArrayList<>();
        final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        mReference.child(User.UserEntity.USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String id = data.getKey();
                    String name = (String) data.child(User.UserEntity.DISPLAYNAME).getValue();
                    String email = (String) data.child(User.UserEntity.EMAIL).getValue();
                    String avatar = (String) data.child(User.UserEntity.AVATAR).getValue();
                    User user = new User.Builder().setId(id)
                            .setDisplayName(name).setEmail(email)
                            .setAvatar(avatar).build();
                    mUserList.add(user);
                }
                ShowDialogChooseFriend(mUserList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
