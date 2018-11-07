package com.example.myteam.codia.data.source.remote.friend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Friend;
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
    public void sentRequest(final String uidUser, final String userReceive, final FriendCallBack.FriendSentCallBack callBack) {
        //sent request stored to user send
        final Boolean success = false;
        FriendRequest friendRequest = new FriendRequest(null, false, DateTimeUtils.getCurrentTime(),
                FriendRequest.FriendRequestEntity.STATUS_PENDING, FriendRequest.FriendRequestEntity.TYPE_SEND);
        mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(uidUser).
                child(userReceive).setValue(friendRequest, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    //sent request stored to user receive
                    FriendRequest friendRequestReceive = new FriendRequest(null, false, DateTimeUtils.getCurrentTime(),
                            FriendRequest.FriendRequestEntity.STATUS_PENDING, FriendRequest.FriendRequestEntity.TYPE_RECEIVE);
                    mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(userReceive).
                            child(uidUser).setValue(friendRequestReceive, new DatabaseReference.CompletionListener() {
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
                        String id = childDataSnapshot.getKey();
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
        mDatabaseReference.child(Friend.FriendEntity.FRIENDS).child(uidUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(uidProfileUser)) {
                            callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_IS_FRIEND);
                        } else {
                            callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_NOT_YET);
                            checkRequest(uidUser, uidProfileUser, callBack);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }

    public void checkRequest(final String uidUser, final String uidProfileUser, final CheckFriendCallBack callBack) {
        mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uidUser)) {
                    if (dataSnapshot.child(uidUser).hasChild(uidProfileUser)) {
                        String type = (String) dataSnapshot.child(uidUser).child(uidProfileUser).child(FriendRequest.FriendRequestEntity.TYPE).getValue();
                        if (type.equals(FriendRequest.FriendRequestEntity.TYPE_RECEIVE)) {
                            //trả về đã  được nhận
                            callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_RECEIVE);
                        } else if (type.equals(FriendRequest.FriendRequestEntity.TYPE_SEND)) {
                            //trả về đã gửi
                            callBack.checkFriend(FriendRequest.FriendRequestEntity.TYPE_SEND);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void acceptFriendRequest(final String uidUser, final String uidUserRequest, final FriendCallBack.FriendAcceptCallBack callBack) {
        Long since = DateTimeUtils.getCurrentTime();
        final Friend friend = new Friend(null, null, since);
        mDatabaseReference.child(Friend.FriendEntity.FRIENDS).child(uidUser).child(uidUserRequest).setValue(friend, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    //deleted in friend request
                    mDatabaseReference.child(Friend.FriendEntity.FRIENDS).child(uidUserRequest).child(uidUser).setValue(friend);
                    mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(uidUser).child(uidUserRequest).removeValue();
                    mDatabaseReference.child(FriendRequest.FriendRequestEntity.FRIEND_REQUEST).child(uidUserRequest).child(uidUser).removeValue();
                    callBack.successful();
                } else {
                    callBack.failed(R.string.failed);
                }
            }
        });
    }

}
