package com.example.myteam.codia.screen.authentication.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.databinding.ActivityRegisterBinding;
import com.example.myteam.codia.screen.authentication.login.LoginActivity;
import com.example.myteam.codia.utils.navigator.Navigator;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RegisterContract.ViewModel mViewModel;
    private ActivityRegisterBinding binding;
    private SharedPrefsImpl mSharedPrefs;
    public static final int REQUEST_CODE = 1;

    public static Intent getInstance(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mViewModel = new RegisterViewModel(this, new Navigator(this));
        mSharedPrefs = new SharedPrefsImpl(this);
        AuthenicationRepository repository =
                new AuthenicationRepository(new AuthenicationRemoteDataSource());
        RegisterContract.Presenter presenter =
                new RegisterPresenter(mViewModel, repository, mSharedPrefs);
        mViewModel.setPresenter(presenter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setViewModel((RegisterViewModel) mViewModel);
        binding.signUpButton.setOnClickListener(this);
        binding.clearPwButton.setOnClickListener(this);
        binding.clearDisplayButton.setOnClickListener(this);
        binding.clearEmailButton.setOnClickListener(this);
        setUiOnFocusEditText(binding.textEmail, binding.clearEmailButton);
        setUiOnFocusEditText(binding.textPassword, binding.clearPwButton);
        setUiOnFocusEditText(binding.textDisplayName, binding.clearDisplayButton);
        //hide keybroad when click enter on keybroad on pw field
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return true;
    }

    //control UI when enter input data
    public void setUiOnFocusEditText(final EditText editText, final ImageButton imageButton) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setSizeIconApp(LoginActivity.WIDTH_MIN_IMG, LoginActivity.HEIGHT_MIN_IMG);
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

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        setSizeIconApp(LoginActivity.WIDTH_MAX_IMG, LoginActivity.HEIGHT_MAX_IMG);
        binding.textPassword.clearFocus();
        binding.textEmail.clearFocus();
        binding.textDisplayName.clearFocus();
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
            case R.id.clear_display_button:
                binding.textDisplayName.setText("");
                break;
            case R.id.sign_up_button:
                mViewModel.onRegisterClick();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //comback Login Activity
                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }
}
