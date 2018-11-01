package com.example.myteam.codia.screen.chat.group;

import android.os.Parcelable;

import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

/**
 * Created by khanhjm on 31-10-2018.
 */
public class GroupContract {
    interface ViewModel extends BaseViewModel<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void GetAllFriend();
    }

    interface SearchListener extends Parcelable {
        void onSelect();
    }
}
