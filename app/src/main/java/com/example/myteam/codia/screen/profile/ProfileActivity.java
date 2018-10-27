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
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.databinding.ActivityProfileBinding;
import com.example.myteam.codia.screen.base.adapter.OnItemClickListener;
import com.example.myteam.codia.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityProfileBinding mBinding;
    private static final String TAG = "ProfileFragment";
    private User mUser;
    private List<String> stringArrayList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ActionBar mActionBar;

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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        Bundle bundle = getIntent().getBundleExtra(Constant.EXTRA_BUNDLE_USER);
        if (bundle != null) {
            mUser = bundle.getParcelable(Constant.EXTRA_USER);
            mBinding.setUser(mUser);
            mBinding.toolbar.setTitle(mUser.getDisplayName());
        }
        initViews();
    }

    private void initViews() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recycler.setLayoutManager(layoutManager);
        setData(); //adding data to array list
        adapter = new RecyclerAdapter(this, stringArrayList, this);
        mBinding.recycler.setAdapter(adapter);
        mBinding.recycler.setFocusable(false);
        mBinding.profileContainer.requestFocus();

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
}
