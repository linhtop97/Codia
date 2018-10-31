package com.example.myteam.codia.screen.chat;

import android.net.Uri;
import android.os.Parcelable;

import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

/**
 * Created by khanhjm on 22-10-2018.
 */
public interface ChatContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void showDialog();

        void dismissDialog();

        void ScrollRecycleView();

        void StopRefresh();

        void LoadMessage();

        void LoadMore();

        void sendMessage(String message);

        void upload(Uri uri);

        void getProfileSuccessful(User user);

        void getProfileError(int message);

        void setOnline();

        void setOffline();
    }

    interface Presenter extends BasePresenter {
        void LoadMessage();

        void LoadMore();

        void sendMessage(String message);

        void upload(Uri uri);
    }

    interface ChatListener extends Parcelable {
        void onSelect(String link);
    }
}
