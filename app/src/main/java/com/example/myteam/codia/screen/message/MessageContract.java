package com.example.myteam.codia.screen.message;

import android.os.Parcelable;

import com.example.myteam.codia.data.model.Chat;
import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

/**
 * Created by khanhjm on 22-10-2018.
 */
public class MessageContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void LoadAll();
    }

    interface Presenter extends BasePresenter {
        void LoadAll();

        void Load();
    }

    interface MessageListener extends Parcelable {
        void onSelect(Chat chat);
    }
}
