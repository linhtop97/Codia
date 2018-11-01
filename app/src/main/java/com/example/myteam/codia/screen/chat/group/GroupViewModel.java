package com.example.myteam.codia.screen.chat.group;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Group;
import com.example.myteam.codia.data.model.Message;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.screen.chat.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khanhjm on 31-10-2018.
 */
public class GroupViewModel extends BaseObservable implements GroupContract.ViewModel {

    private GroupContract.Presenter mPresenter;
    private Context mContext;
    private ChatAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressDialog mDialog;
    private String mUserChatId;
    private String mUserLoginId;
    private List<Group> mGroupList = new ArrayList<>();

    public GroupViewModel(Context context) {
        mContext = context;
        mUserLoginId = new SharedPrefsImpl(mContext).get(SharedPrefsKey.PREF_USER_ID, String.class);
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(context.getString(R.string.msg_loading));
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void setPresenter(GroupContract.Presenter presenter) {
        mPresenter = presenter;
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

    public List<Group> getGroupList() {
        return mGroupList;
    }

    public void setGroupList(List<Group> groupList) {
        mGroupList = groupList;
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
