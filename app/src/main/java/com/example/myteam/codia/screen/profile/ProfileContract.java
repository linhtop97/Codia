package com.example.myteam.codia.screen.profile;

import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

import java.util.List;

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

        void getAllPostSuccessFull(List<Post> posts);

        void getAllUserPost(String uidUser, DataCallback<List<Post>> callback);
    }

    interface Presenter extends BasePresenter {
        void getAllUserPost(String uidUser, DataCallback<List<Post>> callback);
    }
}
