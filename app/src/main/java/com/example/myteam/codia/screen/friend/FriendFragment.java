package com.example.myteam.codia.screen.friend;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.FragmentFriendBinding;
import com.example.myteam.codia.databinding.FragmentTimelineBinding;

public class FriendFragment extends Fragment {

    public static final String TAG = "FriendFragment";
    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentFriendBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false);
        return binding.getRoot();
    }
}
