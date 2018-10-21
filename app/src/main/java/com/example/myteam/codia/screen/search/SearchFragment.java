package com.example.myteam.codia.screen.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.databinding.FragmentSearchBinding;
import com.example.myteam.codia.screen.main.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private SearchViewModel mViewModel;
    private MainActivity mMainActivity;
    private FragmentSearchBinding mBinding;
    private SearchAdapter mAdapter;
    List<User> listFriend = new ArrayList<>();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_search_friend, container, false);
        mViewModel = new SearchViewModel();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        initializeRecyclerView(view);

        SearchContract.Presenter presenter = new SearchPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
        mBinding.setViewModel(mViewModel);

        // Set button
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Fragment fragment = fm.findFragmentByTag(SearchFragment.TAG);
                FragmentTransaction ft_remo = fm.beginTransaction();
                ft_remo.remove(fragment).commit();
                mMainActivity.showBottomNavigation();
            }
        });
        mBinding.edtSearchFriend.addTextChangedListener(searchTextWatcher);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    private void initializeRecyclerView(View view) {
        mViewModel.FindAllFriend();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new SearchAdapter(view.getContext(), listFriend);
        mAdapter.setListener(mSearchListener);

        RecyclerView recyclerView = mBinding.rvFriend;
        recyclerView.setLayoutManager(layoutManager);
        // gạch dưới item
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    public void Search(String value) {
        if (value.isEmpty()) {
            mAdapter.getData().clear();
        } else {
            FindFriend(value);
        }
        CheckSizeFriend();
    }

    private void FindFriend(String value) {
        listFriend.clear();
        for (User item : mViewModel.getResultSearch()
                ) {
            if (item.getDisplayName().toLowerCase().contains(value.toLowerCase())
                    || item.getEmail().toLowerCase().contains(value.toLowerCase()))
                listFriend.add(item);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void CheckSizeFriend() {
        if (listFriend.size() > 0) {
            mBinding.rvFriend.setVisibility(View.VISIBLE);
            mBinding.notifyResult.setVisibility(View.GONE);
        } else {
            mBinding.rvFriend.setVisibility(View.GONE);
            mBinding.notifyResult.setVisibility(View.VISIBLE);
        }
    }

    private Timer timer;
    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
            // user typed: start the timer
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // do your actual work here
                    mMainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Search(mBinding.edtSearchFriend.getText().toString());
//                            Toast.makeText(mMainActivity, mBinding.edtSearchFriend.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }, 1000); // 1000ms delay before the timer executes the "run" method from TimerTask
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // user is typing: reset already started timer (if existing)
            if (timer != null) {
                timer.cancel();
            }
        }
    };

    private SearchContract.SearchListener mSearchListener = new SearchContract.SearchListener() {
        @Override
        public void onSelect(User user) {
            mBinding.edtSearchFriend.clearFocus();
            Toast.makeText(mMainActivity, user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    };
}
