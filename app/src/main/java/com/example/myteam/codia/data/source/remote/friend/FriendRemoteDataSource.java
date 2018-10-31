package com.example.myteam.codia.data.source.remote.friend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.FriendRequest;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.friend.FriendCallBack;
import com.example.myteam.codia.utils.DateTimeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendRemoteDataSource implements FriendDataSource.RemoteDataSource {

    private static final String TAG = "FriendRemoteDataSource";
    protected FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private SharedPrefsImpl mSharedPrefs;

    public FriendRemoteDataSource() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mSharedPrefs = new SharedPrefsImpl(MainApplication.getInstance());
    }

    @Override
    public void sentRequest(final String uidUser, final String userReceive, final FriendCallBack callBack) {
        //sent request stored to user send
        final Boolean success = false;
        FriendRequest friendRequest = new FriendRequest(userReceive, false, DateTimeUtils.getCurrentTime(),
                FriendRequest.FriendRequestEntity.STATUS_PENDING, FriendRequest.FriendRequestEntity.TYPE_SEND);
        mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(uidUser).
                push().setValue(friendRequest, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    //sent request stored to user receive
                    FriendRequest friendRequestReceive = new FriendRequest(uidUser, false, DateTimeUtils.getCurrentTime(),
                            FriendRequest.FriendRequestEntity.STATUS_PENDING, FriendRequest.FriendRequestEntity.TYPE_RECEIVE);
                    mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(userReceive).
                            push().setValue(friendRequestReceive, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                callBack.failed(R.string.create_post_failed);
                            }
                        }
                    });
                    callBack.successful();
                    //show request to receiver
                } else {
                    callBack.failed(R.string.create_post_failed);
                }
            }
        });

    }

    @Override
    public void getAllFriendRequest(final String uidUser, final DataCallback<List<User>> callback) {
        //get all friend request is here
        mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(uidUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String type = (String) childDataSnapshot.child(FriendRequest.FriendRequestEntity.TYPE).getValue();
                    if (type != null && type.equals(FriendRequest.FriendRequestEntity.TYPE_RECEIVE)) {
                        String id = (String) childDataSnapshot.child(FriendRequest.FriendRequestEntity.UID_FRIEND).getValue();
                        User user = new User.Builder().setId(id).build();
                        users.add(user);
                    }
                }
                callback.onGetDataSuccess(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onGetDataFailed("can't get all friend request");
            }
        });
    }

    @Override
    public void checkFriend(final String uidUser, final String uidProfileUser, final CheckFriendCallBack callBack) {
        //lấy uid user -> nhận, gửi, chưa gửi
        mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).addValueEventListener(new ValueEventListener() {
            Boolean isExists = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String uid = (String) childDataSnapshot.getKey();
                    if (uid.equals(uidUser)) {
                        isExists = true;
                        mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(uidUser)
                                .addValueEventListener(new ValueEventListener() {
                                    boolean has = false;

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                            String uid = (String) childDataSnapshot.child(FriendRequest.FriendRequestEntity.UID_FRIEND).getValue();
                                            if (uid.equals(uidProfileUser)) {
                                                has = true;
                                                String type = (String) childDataSnapshot.child(FriendRequest.FriendRequestEntity.TYPE).getValue();
                                                if (type.equals(FriendRequest.FriendRequestEntity.TYPE_RECEIVE)) {
                                                    //trả về đã  được nhận
                                                    callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_RECEIVE);
                                                } else if (type.equals(FriendRequest.FriendRequestEntity.TYPE_SEND)) {
                                                    //trả về đã gửi
                                                    callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_SEND);
                                                }
                                            }
                                        }
                                        if (!has) {
                                            callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_NOT_YET);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                    }
                    if (isExists) {
                        break;
                    }
                }
                if (!isExists) {
                    //trả về chưa làm gì hết
                    callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_NOT_YET);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    private void checkRequest(String uidUser, final String uidProfileUser, final CheckFriendCallBack callBack) {
//        mDatabaseReference.child(Friend.FriendEntity.FRIENDS).child(uidUser)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                            String uid = (String) childDataSnapshot.child(Friend.FriendEntity.ID_FRIEND).getValue();
//                            if (uid.equals(uidProfileUser)) {
//                                callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_IS_FRIEND);
//                                return;
//                            }
//                            checkRequest(uidUser, uidProfileUser, callBack);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//
//    }
}
