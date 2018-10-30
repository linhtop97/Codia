package com.example.myteam.codia.data.source.remote.timeline;

import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.post.CreatePostCallBack;

import java.util.List;

public class TimelineRepository implements TimelineDataSource.RemoteDataSource {
    private TimelineRemoteDataSource mTimelineRemoteDataSource;

    public TimelineRepository(TimelineRemoteDataSource timelineRemoteDataSource) {
        mTimelineRemoteDataSource = timelineRemoteDataSource;
    }

    @Override
    public void pushPost(Post post, CreatePostCallBack callBack) {
        mTimelineRemoteDataSource.pushPost(post, callBack);
    }

    @Override
    public void editPost(Post post) {

    }

    @Override
    public void getPost(String uidUser, String postId) {

    }

    @Override
    public void getListPost(String uidUser, DataCallback<List<Post>> callback) {
        mTimelineRemoteDataSource.getListPost(uidUser, callback);
    }
}
