package com.example.myteam.codia.screen.chat;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Message;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.databinding.ActivityChatBinding;
import com.example.myteam.codia.screen.search.SearchAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by khanhjm on 23-10-2018.
 */
public class ChatActivity extends AppCompatActivity implements DataCallback<User>, View.OnClickListener {

    public static final String TAG = "ChatActivity";
    public static final int REQUEST_CODE_DONE = 200;

    private ChatViewModel mViewModel;
    private ActivityChatBinding mBinding;
    private ChatAdapter mAdapter;

    private String mUserChatId;
    private String mUserLoginId;
    private User mUserChat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mUserChatId = getIntent().getStringExtra(User.UserEntity.ID);
        mUserLoginId = new SharedPrefsImpl(this).get(User.UserEntity.ID, String.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mViewModel = new ChatViewModel(this, mUserChatId);
        ChatPresenter presenter = new ChatPresenter(mViewModel, this);
        mViewModel.setPresenter(presenter);
//        mBinding.setViewModel(mViewModel);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_message_receive, null);
        initializeRecyclerView(view);

        mBinding.btnCall.setOnClickListener(this);
        mBinding.btnVideo.setOnClickListener(this);
        mBinding.btnImage.setOnClickListener(this);
        mBinding.btnEmoji.setOnClickListener(this);
        mBinding.btnInfo.setOnClickListener(this);
        mBinding.btnSend.setOnClickListener(this);
        mBinding.btnBack.setOnClickListener(this);

        mBinding.edtContent.addTextChangedListener(sendTextWatcher);
    }

    private void initializeRecyclerView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new ChatAdapter(view.getContext(), mViewModel.getMessageList());
        RecyclerView recyclerView = mBinding.rvMessage;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mViewModel.LoadMessage(mAdapter);
    }

    @Override
    public void onGetDataSuccess(User data) {
        mUserChat = data;
        mBinding.textDisplayName.setText(mUserChat.getDisplayName());
        mBinding.textLastTime.setText(mUserChat.getLastLogin());
        String urlImage = mUserChat.getAvatar();
        if (urlImage == null || urlImage.isEmpty()) {
            mBinding.avatar.setImageResource(R.drawable.ic_profile);
        } else {
            Picasso.get().load(urlImage).into(mBinding.avatar);
        }
        mViewModel.getProfileSuccessful(mUserChat);
    }

    @Override
    public void onGetDataFailed(String msg) {

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

    private Timer timer;
    private TextWatcher sendTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
            // user typed: start the timer
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // do your actual work here
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mBinding.edtContent.getText().toString().isEmpty()) {
                                mBinding.btnSend.setImageResource(R.drawable.ic_like);
                            } else {
                                mBinding.btnSend.setImageResource(R.drawable.ic_send);
                            }
                        }
                    });
                }
            }, 1); // 500ms delay before the timer executes the "run" method from TimerTask
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // user is typing: reset already started timer (if existing)
            if (timer != null) {
                timer.cancel();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
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
                mViewModel.sendMessage(mBinding.edtContent.getText().toString().trim());
                mBinding.edtContent.setText("");
                ScrollRecycleView();
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

    public void ScrollRecycleView() {
        mBinding.rvMessage.scrollToPosition(mBinding.rvMessage.getAdapter().getItemCount() - 1);
    }
}
