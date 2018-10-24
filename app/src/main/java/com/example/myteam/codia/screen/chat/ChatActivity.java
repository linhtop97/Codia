package com.example.myteam.codia.screen.chat;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.databinding.ActivityChatBinding;

/**
 * Created by khanhjm on 23-10-2018.
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "ChatActivity";
    public static final int REQUEST_CODE_DONE = 200;

    private ChatViewModel mViewModel;
    private ActivityChatBinding mBinding;
    private ChatAdapter mAdapter;

    private String mChatUserId;
    private User mChatUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mChatUserId = getIntent().getStringExtra(User.UserEntity.ID);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mBinding.btnCall.setOnClickListener(this);
        mBinding.btnVideo.setOnClickListener(this);
        mBinding.btnImage.setOnClickListener(this);
        mBinding.btnEmoji.setOnClickListener(this);
        mBinding.btnInfo.setOnClickListener(this);
        mBinding.btnSend.setOnClickListener(this);
        mBinding.btnBack.setOnClickListener(this);
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        mBinding.edtContent.clearFocus();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCall:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnVideo:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnImage:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnEmoji:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnInfo:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnSend:
                hideSoftKeyboard();
                NotifyFucntionUnavailable();
                break;
            case R.id.btnBack:
                hideSoftKeyboard();
                finish();
                break;
        }
    }

    public void NotifyFucntionUnavailable() {
        Toast.makeText(ChatActivity.this, "This feature will soon be released !", Toast.LENGTH_SHORT).show();
    }
}
