package com.example.myteam.codia.screen.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.BR;
import com.example.myteam.codia.data.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khanhjm on 21-10-2018.
 */
public class SearchViewModel extends BaseObservable implements SearchContract.ViewModel {

    private static final String TAG = "SearchViewModel";
    private String mValueSearch;
    private List<User> mResultSearch;

    @Bindable
    public String getValueSearch() {
        return mValueSearch;
    }

    public void setValueSearch(String value) {
        this.mValueSearch = value;
        notifyPropertyChanged(BR.valueSearch);
    }

    @Bindable
    public List<User> getResultSearch() {
        return mResultSearch;
    }

    public void setResultSearch(List<User> mResultSearch) {
        this.mResultSearch = mResultSearch;
        notifyPropertyChanged(BR.resultSearch);
    }

    public SearchViewModel() {
        mResultSearch = new ArrayList<>();
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

    public void FindAllFriend() {
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.getKey();
                String name = (String) dataSnapshot.child(User.UserEntity.DISPLAYNAME).getValue();
                String email = (String) dataSnapshot.child(User.UserEntity.EMAIL).getValue();
                String avatar = (String) dataSnapshot.child(User.UserEntity.IMAGE).getValue();
                User user = new User.Builder().setId(id)
                        .setDisplayName(name).setEmail(email)
                        .setAvatar(avatar).build();
                mResultSearch.add(user);
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
}
