package com.example.myteam.codia.screen.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.ActivityMainBinding;
import com.example.myteam.codia.screen.friend.FriendFragment;
import com.example.myteam.codia.screen.message.MessageFragment;
import com.example.myteam.codia.screen.more.MoreFragment;
import com.example.myteam.codia.screen.notification.NotificationFragment;
import com.example.myteam.codia.screen.search.SearchFragment;
import com.example.myteam.codia.screen.timeline.TimeLineFragment;
import com.example.myteam.codia.utils.BottomNavigationViewHelper;
import com.example.myteam.codia.utils.Constant;
import com.example.myteam.codia.utils.navigator.Navigator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MainContract.ViewModel mViewModel;
    private ActivityMainBinding mBinding;
    private Navigator mNavigator;

    public static Intent getInstance(Context context, Boolean isLogin) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constant.EXTRA_LOGIN_CURRENT_USER, isLogin);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mViewModel = new MainViewModel(this, new Navigator(this));
        //get is Login at here
        Boolean isLoginCurrentUser = getIntent().getBooleanExtra(Constant.EXTRA_LOGIN_CURRENT_USER, false);
        if (isLoginCurrentUser) {
            mViewModel.onLoginCurrentUser();
        }
        mNavigator = new Navigator(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationViewHelper.disableShiftMode(mBinding.bottomNav);
        setupBottomNav(mBinding.bottomNav.getSelectedItemId());
        mBinding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setupBottomNav(item.getItemId());
                return true;
            }
        });

        mBinding.searchContainer.setOnClickListener(this);
        mBinding.addButton.setOnClickListener(this);
    }

    private void setupBottomNav(int id) {
        switch (id) {
            case R.id.action_timeline:
                mNavigator.addFragment(R.id.content_main, TimeLineFragment.newInstance(),
                        false, Navigator.NavigateAnim.FADED, TimeLineFragment.TAG);
                mBinding.articleButton.setVisibility(View.VISIBLE);
                mBinding.cameraButton.setVisibility(View.GONE);
                mBinding.addButton.setVisibility(View.GONE);
                mBinding.settingButton.setVisibility(View.GONE);
                break;
            case R.id.action_msg:
                mNavigator.addFragment(R.id.content_main, MessageFragment.newInstance(),
                        false, Navigator.NavigateAnim.FADED, MessageFragment.TAG);
                mBinding.articleButton.setVisibility(View.GONE);
                mBinding.cameraButton.setVisibility(View.VISIBLE);
                mBinding.addButton.setVisibility(View.VISIBLE);
                mBinding.settingButton.setVisibility(View.GONE);
                break;
            case R.id.action_friend:
                mNavigator.addFragment(R.id.content_main, FriendFragment.newInstance(),
                        false, Navigator.NavigateAnim.FADED, FriendFragment.TAG);
                mBinding.articleButton.setVisibility(View.GONE);
                mBinding.cameraButton.setVisibility(View.GONE);
                mBinding.addButton.setVisibility(View.GONE);
                mBinding.settingButton.setVisibility(View.GONE);
                break;
            case R.id.action_notifi:
                mNavigator.addFragment(R.id.content_main, NotificationFragment.newInstance(),
                        false, Navigator.NavigateAnim.FADED, NotificationFragment.TAG);
                mBinding.articleButton.setVisibility(View.GONE);
                mBinding.cameraButton.setVisibility(View.GONE);
                mBinding.addButton.setVisibility(View.GONE);
                mBinding.settingButton.setVisibility(View.GONE);
                break;
            case R.id.action_more:
                mNavigator.addFragment(R.id.content_main, MoreFragment.newInstance(),
                        false, Navigator.NavigateAnim.FADED, MoreFragment.TAG);
                mBinding.articleButton.setVisibility(View.GONE);
                mBinding.cameraButton.setVisibility(View.GONE);
                mBinding.addButton.setVisibility(View.GONE);
                mBinding.settingButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_container:
                //add search fragment
                mNavigator.addFragment(R.id.main_container, SearchFragment.newInstance(),
                        true, Navigator.NavigateAnim.FADED, SearchFragment.TAG);
                mBinding.bottomNav.setVisibility(View.GONE);
                break;
            case R.id.add_button:
                mViewModel.createChat();
                break;
        }
    }

    public void showBottomNavigation() {
        mBinding.bottomNav.setVisibility(View.VISIBLE);
    }

    public void hideBottomNavigation() {
        mBinding.bottomNav.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(SearchFragment.TAG);
        //if search fragment is showed, show bottom nav
        if (fragment != null) {
            showBottomNavigation();
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    //    mViewModel.onLoginCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  mViewModel.setOnline();
    }

    @Override
    protected void onStop() {
        super.onStop();
 //       mViewModel.onLogout();
//        mViewModel.setOffline();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
