package com.example.myteam.codia.screen.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.BR;
import com.example.myteam.codia.data.model.Default;
import com.example.myteam.codia.data.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khanhjm on 21-10-2018.
 */
public class SearchViewModel extends BaseObservable implements SearchContract.ViewModel {

    private static final String TAG = "SearchViewModel";
    private List<User> mResult;
    private List<User> mUserList;

    @Bindable
    public List<User> getResult() {
        return mResult;
    }

    public void setResult(List<User> value) {
        this.mResult = value;
        notifyPropertyChanged(BR.result);
    }

    @Bindable
    public List<User> getUserList() {
        return mUserList;
    }

    public void setUserList(List<User> mResultSearch) {
        this.mUserList = mResultSearch;
        notifyPropertyChanged(BR.userList);
    }

    public SearchViewModel() {
        mResult = new ArrayList<>();
        mUserList = new ArrayList<>();
        FindAllFriend();
    }

    @Override
    public void search(String value) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {

    }

    @Override
    public void FindAllFriend() {
        mUserList.clear();
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child(User.UserEntity.USERS).addChildEventListener(new ChildEventListener() {
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
    }

    @Override
    public void FindFriend(String value) {
        mResult.clear();
        for (User item : mUserList
                ) {
            if (item.getDisplayName().toLowerCase().contains(value.toLowerCase())
                    || item.getEmail().toLowerCase().contains(value.toLowerCase()))
                mResult.add(item);
        }
    }
}
