package com.example.myteam.codia.screen.search;

import android.os.Parcelable;

import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

/**
 * Created by khanhjm on 21-10-2018.
 */
public interface SearchContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void search(String value);
    }

    interface Presenter extends BasePresenter {
        void search(String value);
    }

    interface SearchListener extends Parcelable {
        void onSelect(User user);
    }
}
