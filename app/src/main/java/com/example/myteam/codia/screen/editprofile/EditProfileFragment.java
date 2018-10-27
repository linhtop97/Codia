package com.example.myteam.codia.screen.editprofile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myteam.codia.R;
import com.example.myteam.codia.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentEditProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);
        return binding.getRoot();
    }
}
