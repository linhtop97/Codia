package com.example.myteam.codia.screen.authentication.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.databinding.ActivityLoginBinding;
import com.example.myteam.codia.utils.navigator.Navigator;

import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_EMAIL_REGISTER;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_KEEP_LOGIN;
import static com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey.PREF_PASSWORD_REGISTER;

;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LoginContract.ViewModel mViewModel;
    public static final int WIDTH_MIN_IMG = 200;
    public static final int WIDTH_MAX_IMG = 256;
    public static final int HEIGHT_MIN_IMG = 100;
    public static final int HEIGHT_MAX_IMG = 128;
    private ActivityLoginBinding binding;
    private SharedPrefsImpl mSharedPrefs;
    public static final int REQUEST_CODE = 1000;

    public static Intent getInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mViewModel = new LoginViewModel(this, new Navigator(this));
        mSharedPrefs = new SharedPrefsImpl(this);
        if (mSharedPrefs.get(PREF_KEEP_LOGIN, Boolean.class)) {
            mViewModel.onLoginCurrentUser();
        }
        AuthenicationRepository repository =
                new AuthenicationRepository(new AuthenicationRemoteDataSource());
        LoginContract.Presenter presenter =
                new LoginPresenter(mViewModel, repository, mSharedPrefs);
        mViewModel.setPresenter(presenter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel((LoginViewModel) mViewModel);
        binding.clearPwButton.setOnClickListener(this);
        binding.clearEmailButton.setOnClickListener(this);
        binding.signInButton.setOnClickListener(this);
        binding.textSignUp.setOnClickListener(this);
        binding.rememberChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSharedPrefs.put(PREF_KEEP_LOGIN, isChecked);
            }
        });
        setUiOnFocusEditText(binding.textEmail, binding.clearEmailButton);
        setUiOnFocusEditText(binding.textPassword, binding.clearPwButton);
        binding.textPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            hideSoftKeyboard();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    public void setUiOnFocusEditText(final EditText editText, final ImageButton imageButton) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setSizeIconApp(WIDTH_MIN_IMG, HEIGHT_MIN_IMG);
                    if (!TextUtils.isEmpty(editText.getText())) {
                        imageButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    imageButton.setVisibility(View.GONE);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    imageButton.setVisibility(View.VISIBLE);
                } else {
                    imageButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return true;
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        setSizeIconApp(WIDTH_MAX_IMG, HEIGHT_MAX_IMG);
        binding.textPassword.clearFocus();
        binding.textEmail.clearFocus();
    }

    private void setSizeIconApp(int width, int height) {
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        int h = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        binding.imgApp.requestLayout();
        binding.imgApp.getLayoutParams().height = h;
        binding.imgApp.getLayoutParams().width = w;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_email_button:
                binding.textEmail.setText("");
                break;
            case R.id.clear_pw_button:
                binding.textPassword.setText("");
                break;
            case R.id.sign_in_button:
                mViewModel.onLoginClick();
                break;
            case R.id.text_sign_up:
                mViewModel.onRegisterClick();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //login execute
                binding.textEmail.setText(mSharedPrefs.get(PREF_EMAIL_REGISTER, String.class));
                binding.textPassword.setText(mSharedPrefs.get(PREF_PASSWORD_REGISTER, String.class));
                mViewModel.onLoginClick();

            }
        }
    }
}
