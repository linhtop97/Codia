package com.example.myteam.codia.data.model;

public class User {
    private String mEmail;
    private String mDisplayName;

    public User() {
    }

    public User(Builder builder) {
        this.mEmail = builder.mEmail;
        this.mDisplayName = builder.mDisplayName;
    }

    public static class Builder {
        private String mEmail;
        private String mDisplayName;

        public Builder setEmail(String email) {
            mEmail = email;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            mDisplayName = displayName;
            return this;
        }

        public User build() {
            return new User(this);
        }
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
}
