package com.example.myteam.codia.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String mId;
    private String mAvatar;
    private String mDateCreated;
    private String mDisplayName;
    private String mEmail;
    private String mAddress;
    private boolean mIsOnline;
    private String mLastLogin;

    private String mStatus;

    public User() {
    }

    public User(Builder builder) {
        this.mId = builder.mId;
        this.mAvatar = builder.mAvatar;
        this.mDateCreated = builder.mDateCreated;
        this.mDisplayName = builder.mDisplayName;
        this.mEmail = builder.mEmail;
        this.mAddress = builder.mAddress;
        this.mIsOnline = builder.mIsOnline;
        this.mLastLogin = builder.mLastLogin;
        this.mStatus = builder.mStatus;
    }

    protected User(Parcel in) {
        mId = in.readString();
        mAvatar = in.readString();
        mDateCreated = in.readString();
        mDisplayName = in.readString();
        mEmail = in.readString();
        mAddress = in.readString();
        mIsOnline = in.readByte() != 0;
        mLastLogin = in.readString();
        mStatus = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public boolean isOnline() {
        return mIsOnline;
    }

    public void setOnline(boolean online) {
        mIsOnline = online;
    }

    public String getLastLogin() {
        return mLastLogin;
    }

    public void setLastLogin(String lastLogin) {
        mLastLogin = lastLogin;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mAvatar);
        dest.writeString(mDateCreated);
        dest.writeString(mDisplayName);
        dest.writeString(mEmail);
        dest.writeString(mAddress);
        dest.writeByte((byte) (mIsOnline ? 1 : 0));
        dest.writeString(mLastLogin);
        dest.writeString(mStatus);
    }

    public static class Builder {
        private String mId;
        private String mAvatar;
        private String mDateCreated;
        private String mDisplayName;
        private String mEmail;
        private String mAddress;
        private boolean mIsOnline;
        private String mLastLogin;
        private String mStatus;


        public Builder setId(String id) {
            mId = id;
            return this;
        }

        public Builder setAvatar(String avatar) {
            mAvatar = avatar;
            return this;
        }

        public Builder setDateCreated(String dateCreated) {
            mDateCreated = dateCreated;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            mDisplayName = displayName;
            return this;
        }

        public Builder setEmail(String email) {
            mEmail = email;
            return this;
        }

        public Builder setAddress(String address) {
            mAddress = address;
            return this;
        }

        public Builder setIsOnline(boolean isOnline) {
            mIsOnline = isOnline;
            return this;
        }

        public Builder setLastLogin(String lastLogin) {
            mLastLogin = lastLogin;
            return this;
        }

        public Builder setStatus(String status) {
            mStatus = status;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public static class UserEntity {
        public static final String USERS = "Users";
        public static final String ID = "id";
        public static final String AVATAR = "avatar";
        public static final String DATECREATED = "dateCreated";
        public static final String DISPLAYNAME = "displayName";
        public static final String EMAIL = "email";
        public static final String ADDRESS = "address";
        public static final String ISONLINE = "isOnline";
        public static final String STATUS = "status";
        public static final String LASTLOGIN = "lastLogin";
    }
}