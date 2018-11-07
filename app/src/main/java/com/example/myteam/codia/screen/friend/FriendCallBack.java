package com.example.myteam.codia.screen.friend;

public interface FriendCallBack {

    interface FriendSentCallBack {
        void successful();

        void failed(int message);
    }

    interface FriendAnswerCallBack {
        void successful();

        void failed(int message);
    }
}
