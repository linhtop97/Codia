package com.example.myteam.codia.data.model;

public class User {
    private String mId;
    private String mEmail;
    private String mDisplayName;
    private String mAvatar;

    public User() {
    }

    public User(Builder builder) {
        this.mId = builder.mId;
        this.mEmail = builder.mEmail;
        this.mDisplayName = builder.mDisplayName;
        this.mAvatar = builder.mAvatar;
    }

    public static class Builder {
        private String mId;
        private String mEmail;
        private String mDisplayName;
        private String mAvatar;

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


    public static class UserEntity {
        public static final String USERS = "Users";
        public static final String ID = "id";
        public static final String DISPLAYNAME = "displayName";
        public static final String EMAIL = "email";
        public static final String AVATAR = "avatar";
    }
}
