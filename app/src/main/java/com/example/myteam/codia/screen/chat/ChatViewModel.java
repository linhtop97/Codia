package com.example.myteam.codia.screen.chat;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Message;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khanhjm on 22-10-2018.
 */
public class ChatViewModel extends BaseObservable implements ChatContract.ViewModel {

    private ChatContract.Presenter mPresenter;
    private Context mContext;
    private ChatAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressDialog mDialog;
    private String mUserChatId;
    private String mUserLoginId;
    private List<Message> mMessageList = new ArrayList<>();

    public ChatViewModel(Context context, String userChatId) {
        mContext = context;
        mUserChatId = userChatId;
        mUserLoginId = new SharedPrefsImpl(mContext).get(SharedPrefsKey.PREF_USER_ID, String.class);
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.msg_loading));
    }

    @Override
    public void ScrollRecycleView() {
        ((ChatActivity) mContext).ScrollRecycleView();
    }

    @Override
    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void LoadMessage() {
        mPresenter.LoadMessage();
    }

    @Override
    public void LoadMore() {
        mPresenter.LoadMore();
    }

    @Override
    public void sendMessage(String message) {
        if (!message.isEmpty()) {
            mPresenter.sendMessage(message);
        }
    }

    @Override
    public void upload(Uri uri) {
        mPresenter.upload(uri);
    }

    @Override
    public void getProfileSuccessful(User user) {
    }

    @Override
    public void getProfileError(int message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void StopRefresh() {
        ((ChatActivity) mContext).StopRefresh();
    }

    public String getUserLoginId() {
        return mUserLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        mUserLoginId = userLoginId;
    }

    public String getUserChatId() {
        return mUserChatId;
    }

    public void setChatUserId(String userChatId) {
        mUserChatId = userChatId;
    }

    public List<Message> getMessageList() {
        return mMessageList;
    }

    public void setMessageList(List<Message> messageList) {
        mMessageList = messageList;
    }

    public ChatAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ChatAdapter adapter) {
        mAdapter = adapter;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return mLinearLayoutManager;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }
}
