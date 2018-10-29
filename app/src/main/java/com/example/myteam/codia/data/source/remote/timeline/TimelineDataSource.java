package com.example.myteam.codia.data.source.remote.timeline;

import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.post.CreatePostCallBack;

import java.util.List;

public interface TimelineDataSource {
    interface RemoteDataSource {

        void pushPost(Post post, CreatePostCallBack callBack);

        void editPost(Post post);

        void getPost(String uidUser, String postId);

        void getListPost(String uidUser, DataCallback<List<Post>> callback);
    }
}
