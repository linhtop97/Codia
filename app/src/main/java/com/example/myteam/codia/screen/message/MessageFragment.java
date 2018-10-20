package com.example.myteam.codia.screen.message;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.FragmentMessageBinding;
import com.example.myteam.codia.databinding.FragmentTimelineBinding;

public class MessageFragment extends Fragment {

    public static final String TAG = "MessageFragment";
    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMessageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        return binding.getRoot();
    }
}
