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
import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.databinding.ActivityProfileBinding;
import com.example.myteam.codia.screen.base.adapter.OnItemClickListener;
import com.example.myteam.codia.screen.chat.ChatActivity;
import com.example.myteam.codia.screen.post.PostAdapter;
import com.example.myteam.codia.utils.Constant;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements OnItemClickListener, DataCallback<List<Post>> {
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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        Bundle bundle = getIntent().getBundleExtra(Constant.EXTRA_BUNDLE_USER);
        if (bundle != null) {
            mUser = bundle.getParcelable(Constant.EXTRA_USER);
            mBinding.setUser(mUser);
        }
        mPostsRef = FirebaseDatabase.getInstance().getReference()
                .child(Post.PostEntity.TIME_LINE).child(mUser.getId());
        initViews();
        mViewModel = new ProfileViewModel(this, new Navigator(this));
        mViewModel.getAllUserPost(mUser.getId(), this);
        mBinding.setViewModel(mViewModel);
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
    private void setData() {
        stringArrayList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            stringArrayList.add("Item " + (i + 1));
        }
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
}
