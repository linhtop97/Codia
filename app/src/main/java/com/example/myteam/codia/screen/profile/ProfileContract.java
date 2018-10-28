package com.example.myteam.codia.screen.profile;

import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

public interface ProfileContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void showDialog();

        void dismissDialog();

        void onAddTimeLineClick();

        void onAddFriendClick();

        void onFriendClick();

        void onMessageClick();

        void onPlanClick();

        void onEditProfileClick();

        void onMoreClick();

        void onViewPictureClick();

        void onViewFriendClick();
    }

    interface Presenter extends BasePresenter {

    }
}
