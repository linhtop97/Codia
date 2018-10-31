package com.example.myteam.codia.data.source.remote.timeline;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.post.CreatePostCallBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimelineRemoteDataSource implements TimelineDataSource.RemoteDataSource {

    private static final String TAG = "TimelineRemoteDataSourc";
    protected FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private SharedPrefsImpl mSharedPrefs;

    public TimelineRemoteDataSource() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mSharedPrefs = new SharedPrefsImpl(MainApplication.getInstance());
    }


    @Override
    public void pushPost(Post post, final CreatePostCallBack callBack) {
        //push post is here
        String uidUser = mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class);
        if (!TextUtils.isEmpty(uidUser)) {
            mDatabaseReference.child(Post.PostEntity.TIME_LINE).child(uidUser).push()
                    .setValue(post, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                callBack.onCreatePostSuccessful();

                            } else {
                                callBack.onCreatePostFailed(R.string.create_post_failed);
                            }
                        }
                    });
        }
    }

    @Override
    public void editPost(Post post) {

    }

    @Override
    public void getPost(String uidUser, String postId) {

    }

    @Override
    public void getListPost(final String uidUser, final DataCallback<List<Post>> callback) {
        final List<Post> posts = new ArrayList<>();
        //get list all uer post at here
        if (!TextUtils.isEmpty(uidUser)) {
            DatabaseReference ref = mDatabaseReference.child(Post.PostEntity.TIME_LINE).child(uidUser);
            ref.orderByChild(Post.PostEntity.DATE_CREATED).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        String id = childDataSnapshot.getKey();
                        String uidUser = (String) childDataSnapshot.child(Post.PostEntity.UID_USER).getValue();
                        String dateCreated = (String) childDataSnapshot.child(Post.PostEntity.DATE_CREATED).getValue();
                        String content = (String) childDataSnapshot.child(Post.PostEntity.CONTENT).getValue();
                        Boolean edited = (Boolean) childDataSnapshot.child(Post.PostEntity.IS_EDITED).getValue();
                        String image = (String) childDataSnapshot.child(Post.PostEntity.IMAGE).getValue();
                        String privacy = (String) childDataSnapshot.child(Post.PostEntity.PRIVACY).getValue();
                        Post post = new Post.Builder().setId(id)
                                .setUidUser(uidUser)
                                .setDateCreated(dateCreated)
                                .setContent(content)
                                .setEdited(edited)
                                .setImage(image)
                                .setPrivacy(privacy)
                                .build();
                        posts.add(post);
                    }
                    callback.onGetDataSuccess(posts);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
