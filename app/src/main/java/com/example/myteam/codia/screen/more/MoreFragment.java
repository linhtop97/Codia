package com.example.myteam.codia.screen.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.FragmentMoreBinding;
import com.example.myteam.codia.databinding.FragmentTimelineBinding;

public class MoreFragment extends Fragment {

    public static final String TAG = "MoreFragment";
    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        return binding.getRoot();
    }
}
