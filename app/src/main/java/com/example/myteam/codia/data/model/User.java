package com.example.myteam.codia.data.model;

public class User {
    private String mId;
    private String mEmail;
    private String mDisplayName;
    private String mAvatar;
    private boolean mIsOnline;

    public User() {
    }

    public User(Builder builder) {
        this.mId = builder.mId;
        this.mEmail = builder.mEmail;
        this.mDisplayName = builder.mDisplayName;
        this.mAvatar = builder.mAvatar;
        this.mIsOnline = builder.mIsOnline;
    }

    public static class Builder {
        private String mId;
        private String mEmail;
        private String mDisplayName;
        private String mAvatar;
        private boolean mIsOnline;

        public Builder setId(String id) {
            mId = id;
            return this;
        }

        public Builder setEmail(String email) {
            mEmail = email;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            mDisplayName = displayName;
            return this;
        }

        public Builder setAvatar(String avatar) {
            mAvatar = avatar;
            return this;
        }

        public Builder setIsOnline(boolean isOnline) {
            mIsOnline = isOnline;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public boolean getIsOnline() {
        return mIsOnline;
    }

    public void setIsOnline(boolean isonline) {
        mIsOnline = isonline;
    }

    public static class UserEntity {
        public static final String USERS = "Users";
        public static final String ID = "id";
        public static final String DISPLAYNAME = "displayName";
        public static final String EMAIL = "email";
        public static final String AVATAR = "avatar";
        public static final String ISONLINE = "isOnline";
    }
}
