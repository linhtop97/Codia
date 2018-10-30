package com.example.myteam.codia.screen.more;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.databinding.FragmentMoreBinding;
import com.example.myteam.codia.utils.navigator.Navigator;
import com.squareup.picasso.Picasso;

public class MoreFragment extends Fragment implements DataCallback<User>, View.OnClickListener {

    public static final String TAG = "MoreFragment";
    private MoreViewModel mViewModel;
    private User mUser;
    private FragmentMoreBinding mBinding;

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        mViewModel = new MoreViewModel(getContext(), new Navigator(getActivity()));
        AuthenicationRepository repository = new AuthenicationRepository(new AuthenicationRemoteDataSource());
        MorePresenter morePresenter = new MorePresenter(mViewModel, repository, new SharedPrefsImpl(getContext()), this);
        mViewModel.setPresenter(morePresenter);
        mBinding.setViewModel(mViewModel);
        mBinding.profileContainer.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onGetDataSuccess(User data) {
        mUser = data;
        mBinding.txtName.setText(mUser.getDisplayName());
        String urlImage = mUser.getAvatar();
        if (urlImage == null) {
            mBinding.imgAvartar.setImageResource(R.drawable.ic_profile);
        } else {
            Picasso.get().load(urlImage).placeholder(R.drawable.default_avatar)
                    .into(mBinding.imgAvartar);
        }
    }

    @Override
    public void onGetDataFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        mViewModel.onShowProfileClick();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }
}
