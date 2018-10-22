package com.example.myteam.codia.screen.profile;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.databinding.FragmentProfileBinding;
import com.example.myteam.codia.screen.main.MainActivity;
import com.example.myteam.codia.utils.Constant;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private User mUser;
    private FragmentProfileBinding mBinding;

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARGUMENT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {

    }

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).showBottomNavigation();
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //init User is here
        mUser = getArguments().getParcelable(Constant.ARGUMENT_USER);
        if (mUser != null) {
            Log.d("hihi", mUser.getDisplayName());
        }

    }
}
