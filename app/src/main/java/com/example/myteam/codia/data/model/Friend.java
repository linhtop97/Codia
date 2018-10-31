package com.example.myteam.codia.data.model;

public class Friend {
    private String mIdUser;
    private String mIdFriend;
    private Long mSince;

    public Friend() {
    }

    public Friend(String idUser, String idFriend, Long since) {
        mIdUser = idUser;
        mIdFriend = idFriend;
        mSince = since;
    }

    public String getIdUser() {
        return mIdUser;
    }

    public Friend setIdUser(String idUser) {
        mIdUser = idUser;
        return this;
    }

    public String getIdFriend() {
        return mIdFriend;
    }

    public Friend setIdFriend(String idFriend) {
        mIdFriend = idFriend;
        return this;
    }

    public Long getSince() {
        return mSince;
    }

    public Friend setSince(Long since) {
        mSince = since;
        return this;
    }

    public static class FriendEntity {
        public static final String FRIENDS = "Friends";
        public static final String ID_FRIEND = "idFriend";
        public static final String SINCE = "since";
    }
}
