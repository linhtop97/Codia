package com.example.myteam.codia.screen.authentication.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    private LoginContract.ViewModel mViewModel;

    public static Intent getInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_login);
      //  binding.setViewModel((LoginViewModel) mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
      //  mViewModel.onStop();
        super.onStop();
    }
}
