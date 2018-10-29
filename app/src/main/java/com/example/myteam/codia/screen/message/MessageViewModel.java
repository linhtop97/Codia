package com.example.myteam.codia.screen.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.data.model.Chat;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khanhjm on 22-10-2018.
 */
public class MessageViewModel implements MessageContract.ViewModel {

    private static final String TAG = "MessageViewModel";

    private MessageContract.Presenter mPresenter;
    private Context mContext;
    private MessageAdapter mAdapter;

    private String mUserLoginId;

    private List<Chat> mChats;

    public MessageViewModel(Context context) {
        mContext = context;
        mChats = new ArrayList<>();
        mUserLoginId = new SharedPrefsImpl(mContext).get(SharedPrefsKey.PREF_USER_ID, String.class);
    }

    @Override
    public void LoadAll() {
        mPresenter.LoadAll();
        mPresenter.Load();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public List<Chat> getChats() {
        return mChats;
    }

    public void setChats(List<Chat> chats) {
        mChats = chats;
    }

    public String getUserLoginId() {
        return mUserLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        mUserLoginId = userLoginId;
    }

    public MessageAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(MessageAdapter adapter) {
        mAdapter = adapter;
    }
}
