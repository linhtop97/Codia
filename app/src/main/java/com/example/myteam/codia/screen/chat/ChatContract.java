package com.example.myteam.codia.screen.chat;

import android.widget.Adapter;

import com.example.myteam.codia.data.model.Message;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

import java.util.List;

/**
 * Created by khanhjm on 22-10-2018.
 */
public interface ChatContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void showDialog();

        void dismissDialog();

        void ScrollRecycleView();

        void LoadMessage(ChatAdapter adapter);

        void sendMessage(String message);

        void getProfileSuccessful(User user);

        void getProfileError(int message);
    }

    interface Presenter extends BasePresenter {
        void LoadMessage(ChatAdapter adapter);

        void sendMessage(String message);
    }
}
