package com.example.myteam.codia.screen.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.FriendRequest;
import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.data.source.remote.friend.CheckFriendCallBack;
import com.example.myteam.codia.databinding.ActivityProfileBinding;
import com.example.myteam.codia.screen.base.adapter.OnItemClickListener;
import com.example.myteam.codia.screen.chat.ChatActivity;
import com.example.myteam.codia.screen.friend.FriendCallBack;
import com.example.myteam.codia.screen.post.PostAdapter;
import com.example.myteam.codia.utils.Constant;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements OnItemClickListener,
        DataCallback<List<Post>>, View.OnClickListener, FriendCallBack, CheckFriendCallBack, UserDataCallBack {
    private ActivityProfileBinding mBinding;
    private static final String TAG = "ProfileFragment";
    private User mUser;
    private List<String> stringArrayList;
    private RecyclerView recyclerView;
    private PostAdapter mPostAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ActionBar mActionBar;
    private ProfileViewModel mViewModel;
    private DatabaseReference mPostsRef;
    private List<Post> mPosts;
    private SharedPrefsImpl mSharedPrefs;
    private Navigator mNavigator;


    public static Intent getInstance(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_USER, user);
        intent.putExtra(Constant.EXTRA_BUNDLE_USER, bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPrefs = new SharedPrefsImpl(this);
        mNavigator = new Navigator(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        Bundle bundle = getIntent().getBundleExtra(Constant.EXTRA_BUNDLE_USER);
        if (bundle != null) {
            mUser = bundle.getParcelable(Constant.EXTRA_USER);
        }
        mBinding.addFriendButton.setOnClickListener(this);
        mPostsRef = FirebaseDatabase.getInstance().getReference()
                .child(Post.PostEntity.TIME_LINE).child(mUser.getId());
        initViews();
        mViewModel = new ProfileViewModel(this, new Navigator(this));
        mViewModel.getAllUserPost(mUser.getId(), this);
        mBinding.setViewModel(mViewModel);

        final String uidProflieUser = mUser.getId();
        String uidUser = mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class);
        if (!uidUser.equals(uidProflieUser)) {
            //set new user
            mViewModel.setUserProfile(uidProflieUser, this);
            mBinding.fab.setVisibility(View.GONE);
            checkSendRequest(uidUser, uidProflieUser, this);
        } else {
            mBinding.setUser(mUser);
            mBinding.messageButton.setVisibility(View.GONE);
            mBinding.planButton.setVisibility(View.VISIBLE);
            mBinding.editProfileButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkSendRequest(String uidUser, String uidProfileUser, CheckFriendCallBack callBack) {
        mViewModel.checkSendRequest(uidUser, uidProfileUser, callBack);
    }

    private void initViews() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.recycler.setHasFixedSize(true);
        mBinding.recycler.setLayoutManager(mLinearLayoutManager);
        //  setData(); //adding data to array list
        mPostAdapter = new PostAdapter(this, mBinding.recycler, null);
        mBinding.recycler.setAdapter(mPostAdapter);
        mBinding.recycler.setFocusable(false);
        mBinding.profileContainer.requestFocus();

        mBinding.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChatActivity.class);
                intent.putExtra(User.UserEntity.ID, mUser.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "hihi:" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetDataSuccess(List<Post> data) {
        mPosts = data;
        mPostAdapter.setData(mPosts);
        mLinearLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onGetDataFailed(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //mViewModel.getAllUserPost(mUser.getId(), this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_friend_button:
                mBinding.addFriendButton.setVisibility(View.GONE);
                mViewModel.onAddFriendClick(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class), mUser.getId(), this);
                break;
        }
    }

    @Override
    public void successful() {
        mNavigator.showToast(R.string.friend_request_was_sent);
    }

    @Override
    public void failed(int message) {
        mNavigator.showToast(R.string.friend_request_failed);
    }

    @Override
    public void checkFriend(String something) {
        if (something.equals(FriendRequest.FriendRequestEntity.TYPE_SEND)) {
            mBinding.addFriendRequested.setVisibility(View.VISIBLE);
        } else if (something.equals(FriendRequest.FriendRequestEntity.TYPE_RECEIVE)) {
            mBinding.confirmFriendContainer.setVisibility(View.VISIBLE);
            mBinding.answerFriendRequested.setVisibility(View.VISIBLE);
        } else if (something.equals(FriendRequest.FriendRequestEntity.TYPE_NOT_YET)) {
            mBinding.addFriendButton.setVisibility(View.VISIBLE);
        }
//        else if (something.equals(FriendRequest.FriendRequestEntity.TYPE_IS_FRIEND)) {
//            mBinding.confirmFriendContainer.setVisibility(View.GONE);
//            mBinding.answerFriendRequested.setVisibility(View.GONE);
//            mBinding.addFriendRequested.setVisibility(View.GONE);
//            mBinding.addFriendButton.setVisibility(View.GONE);
//        }
    }

    @Override
    public void getUserCodiaSuccessful(User user) {
        mBinding.setUser(user);
    }

    @Override
    public void getUserCodiaFailed() {

    }
}
