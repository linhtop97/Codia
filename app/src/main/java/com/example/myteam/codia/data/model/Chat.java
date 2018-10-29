package com.example.myteam.codia.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khanhjm on 26-10-2018.
 */

public class Chat implements Parcelable {
    private String mId;
    private String mChatId;
    private String mDateCreated;
    private String mDisplayName;
    private boolean mSeen;
    private String mType;
    private String mLastMessage;
    private String mLastTime;
    private String mBackground;
    private String mColor;

    public Chat() {
    }

    public Chat(Builder builder) {
        this.mId = builder.mId;
        this.mChatId = builder.mChatId;
        this.mDateCreated = builder.mDateCreated;
        this.mDisplayName = builder.mDisplayName;
        this.mSeen = builder.mSeen;
        this.mType = builder.mType;
        this.mLastMessage = builder.mLastMessage;
        this.mLastTime = builder.mLastTime;
        this.mBackground = builder.mBackground;
        this.mColor = builder.mColor;
    }

    protected Chat(Parcel in) {
        mId = in.readString();
        mChatId = in.readString();
        mDateCreated = in.readString();
        mDisplayName = in.readString();
        mSeen = in.readByte() != 0;
        mType = in.readString();
        mLastMessage = in.readString();
        mLastTime = in.readString();
        mBackground = in.readString();
        mColor = in.readString();
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getChatId() {
        return mChatId;
    }

    public void setChatId(String chatId) {
        mChatId = chatId;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public boolean getSeen() {
        return mSeen;
    }

    public void setSeen(boolean seen) {
        mSeen = seen;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(String lastMessage) {
        mLastMessage = lastMessage;
    }

    public String getLastTime() {
        return mLastTime;
    }

    public void setLastTime(String lastTime) {
        mLastTime = lastTime;
    }

    public String getBackground() {
        return mBackground;
    }

    public void setBackground(String background) {
        mBackground = background;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mChatId);
        dest.writeString(mDateCreated);
        dest.writeString(mDisplayName);
        dest.writeByte((byte) (mSeen ? 1 : 0));
        dest.writeString(mType);
        dest.writeString(mLastMessage);
        dest.writeString(mLastTime);
        dest.writeString(mBackground);
        dest.writeString(mColor);
    }

    public static class Builder {
        private String mId;
        private String mChatId;
        private String mDateCreated;
        private String mDisplayName;
        private boolean mSeen;
        private String mType;
        private String mLastMessage;
        private String mLastTime;
        private String mBackground;
        private String mColor;


        public Builder setId(String id) {
            mId = id;
            return this;
        }

        public Builder setChatId(String chatId) {
            mChatId = chatId;
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

        public Builder setSeen(boolean seen) {
            mSeen = seen;
            return this;
        }

        public Builder setType(String type) {
            mType = type;
            return this;
        }

        public Builder setLastMessage(String lastMessage) {
            mLastMessage = lastMessage;
            return this;
        }

        public Builder setLastTime(String lastTime) {
            mLastTime = lastTime;
            return this;
        }

        public Builder setBackground(String background) {
            mBackground = background;
            return this;
        }

        public Builder setColor(String color) {
            mColor = color;
            return this;
        }

        public Chat build() {
            return new Chat(this);
        }
    }

    public static class ChatEntity {
        public static final String CLASS = "Chats";
        public static final String CHATS = "Chats";
        public static final String ID = "id";
        public static final String CHAT_ID = "chatId";
        public static final String DATECREATED = "dateCreated";
        public static final String DISPLAYNAME = "displayName";
        public static final String SEEN = "seen";
        public static final String TYPE = "type";
        public static final String LASTMESSAGE = "lastMessage";
        public static final String LASTTIME = "lastTime";
        public static final String BACKGROUND = "background";
        public static final String COLOR = "color";

        public static final String TYPE_PERSON = "person";
        public static final String TYPE_GROUP = "group";
    }
}