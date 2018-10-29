package com.example.myteam.codia.data.model;

public class Comment {
    private String mId;
    private String mContent;
    private String mDateCreated;
    private String mImage;
    private String mUidUser;

    public Comment(Builder builder) {
        this.mId = builder.mId;
        this.mContent = builder.mContent;
        this.mDateCreated = builder.mDateCreated;
        this.mImage = builder.mImage;
        this.mUidUser = builder.mUidUser;
    }

    public static class Builder{
        private String mId;
        private String mContent;
        private String mDateCreated;
        private String mImage;
        private String mUidUser;

        public Builder setId(String id) {
            mId = id;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public Builder setDateCreated(String dateCreated) {
            mDateCreated = dateCreated;
            return this;
        }

        public Builder setImage(String image) {
            mImage = image;
            return this;
        }

        public Builder setUidUser(String uidUser) {
            mUidUser = uidUser;
            return this;
        }

        public Comment build(){
            return new Comment(this);
        }
    }
    public String getId() {
        return mId;
    }

    public Comment setId(String id) {
        mId = id;
        return this;
    }

    public String getContent() {
        return mContent;
    }

    public Comment setContent(String content) {
        mContent = content;
        return this;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public Comment setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
        return this;
    }

    public String getImage() {
        return mImage;
    }

    public Comment setImage(String image) {
        mImage = image;
        return this;
    }

    public String getUidUser() {
        return mUidUser;
    }

    public Comment setUidUser(String uidUser) {
        mUidUser = uidUser;
        return this;
    }
}
