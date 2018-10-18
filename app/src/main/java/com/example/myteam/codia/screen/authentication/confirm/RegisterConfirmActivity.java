package com.example.myteam.codia.screen.authentication.confirm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.databinding.ActivityRegisterConfirmBinding;
import com.example.myteam.codia.utils.navigator.Navigator;

public class RegisterConfirmActivity extends AppCompatActivity {

    private RegisterConfirmViewModel mViewModel;
    private SharedPrefsImpl mSharedPrefs;

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, RegisterConfirmActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        ActivityRegisterConfirmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register_confirm);
        mViewModel = new RegisterConfirmViewModel(this, new Navigator(this));
        mSharedPrefs = new SharedPrefsImpl(this);
        AuthenicationRepository repository =
                new AuthenicationRepository(new AuthenicationRemoteDataSource());
        RegisterConfirmContract.Presenter presenter =
                new RegisterConfirmPresenter(mViewModel, repository, mSharedPrefs);
        mViewModel.setPresenter(presenter);
        binding.setViewModel(mViewModel);
    }

    @Override
    public void onBackPressed() {
        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
