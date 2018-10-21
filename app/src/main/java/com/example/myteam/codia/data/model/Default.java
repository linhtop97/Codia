package com.example.myteam.codia.data.model;

/**
 * Created by khanhjm on 21-10-2018.
 */
public class Default {
    private String mAvatar;

    public Default() {
    }

    public Default(Builder builder) {
        this.mAvatar = builder.mAvatar;
    }

    public static class Builder {
        private String mAvatar;

        public Builder setAvatar(String avatar) {
            mAvatar = avatar;
            return this;
        }

        public Default build() {
            return new Default(this);
        }
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }


    public static class DefaultEntity {
        public static final String DEFAULTS = "Defaults";
        public static final String AVATAR = "avatar";
        public static final String MEN = "men";
        public static final String WOMEN = "women";
    }
}
