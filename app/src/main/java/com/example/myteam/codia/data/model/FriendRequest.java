package com.example.myteam.codia.data.model;

public class FriendRequest {
    private String mUidFriend;
    private Boolean mIsSeen;
    private Long mSince;
    private String mStatus;
    private String mType;

    public FriendRequest() {
    }

    public FriendRequest(String uidFriend, Boolean isSeen, Long since, String status, String type) {
        mUidFriend = uidFriend;
        mIsSeen = isSeen;
        mSince = since;
        mStatus = status;
        mType = type;
    }

    public String getUidFriend() {
        return mUidFriend;
    }

    public FriendRequest setUidFriend(String uidFriend) {
        mUidFriend = uidFriend;
        return this;
    }

    public Boolean getSeen() {
        return mIsSeen;
    }

    public FriendRequest setSeen(Boolean seen) {
        mIsSeen = seen;
        return this;
    }

    public Long getSince() {
        return mSince;
    }

    public FriendRequest setSince(Long since) {
        mSince = since;
        return this;
    }

    public String getStatus() {
        return mStatus;
    }

    public FriendRequest setStatus(String status) {
        mStatus = status;
        return this;
    }

    public String getType() {
        return mType;
    }

    public FriendRequest setType(String type) {
        mType = type;
        return this;
    }

    public static class FriendRequestEntity {
        public static final String FRIEND_REQUEST = "FriendRequest";
        public static final String UID_FRIEND = "uidFriend";
        public static final String TYPE_SEND = "send";
        public static final String TYPE_RECEIVE = "receive";
        public static final String TYPE_NOT_YET = "notYet";
        public static final String TYPE_IS_FRIEND = "isFriend";
        public static final String STATUS_PENDING = "pending";
        public static final String STATUS_ACCEPTED = "accepted";
        public static final String STATUS_DELINE = "deline";
        public static final String TYPE = "type";
    }
}
