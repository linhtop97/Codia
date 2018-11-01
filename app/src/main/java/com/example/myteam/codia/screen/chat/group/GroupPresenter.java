package com.example.myteam.codia.screen.chat.group;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.data.model.Group;
import com.example.myteam.codia.data.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khanhjm on 31-10-2018.
 */
public class GroupPresenter implements GroupContract.Presenter {

    private GroupContract.ViewModel mViewModel;
    private String mUserLoginId;
    private DatabaseReference mReference;

    public GroupPresenter(GroupContract.ViewModel viewModel) {
        mViewModel = viewModel;
        mUserLoginId = ((GroupViewModel) mViewModel).getUserLoginId();
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void GetAllFriend() {
        final List<Group> listMember = new ArrayList<>();

        mReference.child(User.UserEntity.USERS)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String id = dataSnapshot.getKey();
                        String displayName = (String) dataSnapshot.child(Group.GroupEntity.DISPLAYNAME).getValue();
                        String email = (String) dataSnapshot.child(Group.GroupEntity.EMAIL).getValue();
                        String avatar = (String) dataSnapshot.child(Group.GroupEntity.AVATAR).getValue();

                        Group member = new Group();
                        member.Id = id;
                        member.DisplayName = displayName;
                        member.Email = email;
                        member.Avatar = avatar;

                        ((GroupViewModel) mViewModel).getGroupList().add(member);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        LoadAll();
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
    public void onStart() {
        GetAllFriend();
    }

    @Override
    public void onStop() {

    }
}
