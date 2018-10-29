package com.example.myteam.codia.screen.message;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Chat;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.databinding.FragmentMessageBinding;
import com.example.myteam.codia.databinding.FragmentTimelineBinding;
import com.example.myteam.codia.screen.chat.ChatActivity;
import com.example.myteam.codia.screen.chat.ChatContract;
import com.example.myteam.codia.screen.main.MainActivity;

public class MessageFragment extends Fragment {

    public static final String TAG = "MessageFragment";
    private MessageViewModel mViewModel;
    private MainActivity mMainActivity;
    private FragmentMessageBinding mBinding;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    public MessageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_message, container, false);
        mViewModel = new MessageViewModel(mMainActivity);
        MessagePresenter presenter = new MessagePresenter(mViewModel);
        mViewModel.setPresenter(presenter);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        initializeRecyclerView(view);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    private void initializeRecyclerView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        MessageAdapter adapter = new MessageAdapter(view.getContext(), mViewModel.getChats());
        adapter.setListener(mMessageListener);
        RecyclerView recyclerView = mBinding.rvMessage;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        mViewModel.setAdapter(adapter);
        mViewModel.LoadAll();
    }

    private MessageContract.MessageListener mMessageListener = new MessageContract.MessageListener() {
        @Override
        public void onSelect(Chat chat) {
            Intent intent = new Intent(mMainActivity, ChatActivity.class);
            intent.putExtra(User.UserEntity.ID, chat.getId());
            startActivity(intent);
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
