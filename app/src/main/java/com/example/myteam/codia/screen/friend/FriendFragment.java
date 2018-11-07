package com.example.myteam.codia.screen.friend;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myteam.codia.MainApplication;
import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.databinding.FragmentFriendBinding;
import com.example.myteam.codia.screen.main.MainActivity;
import com.example.myteam.codia.utils.navigator.Navigator;

import java.util.List;

public class FriendFragment extends Fragment implements FriendRequestAdapter.OnFriendRequestClickListener, DataCallback<List<User>>, FriendCallBack.FriendAcceptCallBack {

    public static final String TAG = "FriendFragment";
    private FragmentFriendBinding mBinding;
    private FriendRequestAdapter mFriendRequestAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private FriendViewModel mViewModel;
    private SharedPrefsImpl mSharedPrefs;
    private Navigator mNavigator;
    private List<User> mUsers;
    private int mPosition;

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false);
        mViewModel = new FriendViewModel();
        mSharedPrefs = new SharedPrefsImpl(MainApplication.getInstance());
        getAllFriendRequest();
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.recyclerFriendRequest.setHasFixedSize(true);
        mBinding.recyclerFriendRequest.setLayoutManager(mLinearLayoutManager);
        //  setData(); //adding data to array list
        mFriendRequestAdapter = new FriendRequestAdapter(getActivity(), null, this);
        mBinding.recyclerFriendRequest.setAdapter(mFriendRequestAdapter);
    }

    private void getAllFriendRequest() {
        mViewModel.getAllFriendRequest(this);
    }

    @Override
    public void onAccept(int position) {
        //accept friend here
        mPosition = position;
        String uidUserRequest = mUsers.get(position).getId();
        mViewModel.acceptFriendRequest(mSharedPrefs.get(SharedPrefsKey.PREF_USER_ID, String.class)
                , uidUserRequest, this);
    }

    @Override
    public void onDecline() {

    }

    @Override
    public void onGetDataSuccess(List<User> data) {
        mUsers = data;
        mFriendRequestAdapter.setData(data);
    }

    @Override
    public void onGetDataFailed(String msg) {
        mNavigator.showToast(msg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNavigator = new Navigator((MainActivity) context);
    }

    @Override
    public void successful() {
        //remove item
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        mUsers.remove(mPosition);
        mFriendRequestAdapter.notifyItemRemoved(mPosition);

    }

    @Override
    public void failed(int message) {
        //toast something like: "accept failed"
    }
}
