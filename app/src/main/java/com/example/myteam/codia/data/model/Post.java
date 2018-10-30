package com.example.myteam.codia.data.model;

import java.util.List;

public class Post {
    private String mId;
    private String mUidUser;
    private String mDateCreated;
    private String mContent;
    private String mImage;
    private Boolean mIsEdited;
    private String mPrivacy;
    private List<Comment> mComments;

    public Post() {

    }

    public Post(Builder builder) {
        this.mId = builder.mId;
        this.mUidUser = builder.mUidUser;
        this.mDateCreated = builder.mDateCreated;
        this.mImage = builder.mImage;
        this.mIsEdited = builder.mIsEdited;
        this.mPrivacy = builder.mPrivacy;
        this.mComments = builder.mComments;
        this.mContent = builder.mContent;
    }


    public static class Builder {
        private String mId;
        private String mUidUser;
        private String mDateCreated;
        private String mImage;
        private Boolean mIsEdited;
        private String mPrivacy;
        private String mContent;

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        private List<Comment> mComments;

        public Builder setId(String id) {
            mId = id;
            return this;
        }

        public Builder setUidUser(String uidUser) {
            mUidUser = uidUser;
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

        public Builder setEdited(Boolean edited) {
            mIsEdited = edited;
            return this;
        }

        public Builder setPrivacy(String privacy) {
            mPrivacy = privacy;
            return this;
        }

        public Builder setComments(List<Comment> comments) {
            mComments = comments;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public String getContent() {
        return mContent;
    }

    public Post setContent(String content) {
        mContent = content;
        return this;
    }


    public String getUidUser() {
        return mUidUser;
    }

    public Post setUidUser(String uidUser) {
        mUidUser = uidUser;
        return this;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public Boolean getEdited() {
        return mIsEdited;
    }

    public void setEdited(Boolean edited) {
        mIsEdited = edited;
    }

    public String getPrivacy() {
        return mPrivacy;
    }

    public void setPrivacy(String privacy) {
        mPrivacy = privacy;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public static class PostEntity {
        public static final String TIME_LINE = "Timelines";
        public static final String DATE_CREATED = "dateCreated";
        public static final String PRIVACY = "privacy";
        public static final String COMMENT = "comments";
        public static final String CONTENT = "content";
        public static final String IMAGE = "image";
        public static final String IS_EDITED = "edited";
        public static final String UID_USER = "uidUser";
    }
}
