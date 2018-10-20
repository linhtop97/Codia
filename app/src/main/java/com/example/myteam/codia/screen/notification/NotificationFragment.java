package com.example.myteam.codia.screen.notification;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.FragmentMoreBinding;
import com.example.myteam.codia.databinding.FragmentNotificationBinding;
import com.example.myteam.codia.databinding.FragmentTimelineBinding;

public class NotificationFragment extends Fragment {

    public static final String TAG = "NotificationFragment";
    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        return binding.getRoot();
    }
}
