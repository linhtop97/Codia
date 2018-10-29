package com.example.myteam.codia.screen.post;

import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.screen.base.BasePresenter;
import com.example.myteam.codia.screen.base.BaseViewModel;

public interface PostContract {
    interface ViewModel extends BaseViewModel<Presenter> {
        void showDialog();

        void dismissDialog();

        void onPrivacyClick();

        void onPostClick(Post post, CreatePostCallBack callBack);

        void onBackClick();
    }

    interface Presenter extends BasePresenter {

        void createPost(Post post);
    }
}
