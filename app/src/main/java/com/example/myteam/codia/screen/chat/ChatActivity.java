package com.example.myteam.codia.screen.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.ActivityChatBinding;

/**
 * Created by khanhjm on 23-10-2018.
 */
public class ChatActivity extends AppCompatActivity {

    public static final String TAG = "ChatActivity";
    public static final int REQUEST_CODE_DONE = 200;

    private ChatViewModel mViewModel;
    private ActivityChatBinding mBinding;
    private ChatAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
