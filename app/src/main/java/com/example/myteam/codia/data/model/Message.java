package com.example.myteam.codia.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by khanhjm on 26-10-2018.
 */
public class Message implements Parcelable {
    private String mId;
    private String mMessage;
    private String mTime;
    private String mType;
    private boolean mSeen;
    private String mTimeSeen;
    private String mFrom;
    public boolean ShowAvatar = true;
    public boolean ShowTime = true;

    public Message() {
    }

    public Message(Builder builder) {
        this.mId = builder.mId;
        this.mMessage = builder.mMessage;
        this.mTime = builder.mTime;
        this.mType = builder.mType;
        this.mSeen = builder.mSeen;
        this.mTimeSeen = builder.mTimeSeen;
        this.mTime = builder.mTime;
        this.mFrom = builder.mFrom;
    }

    protected Message(Parcel in) {
        mId = in.readString();
        mMessage = in.readString();
        mTime = in.readString();
        mType = in.readString();
        mSeen = in.readByte() != 0;
        mTimeSeen = in.readString();
        mFrom = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public boolean getSeen() {
        return mSeen;
    }

    public void setSeen(boolean seen) {
        mSeen = seen;
    }

    public String getTimeSeen() {
        return mTimeSeen;
    }

    public void setTimeSeen(String timeSeen) {
        mTimeSeen = timeSeen;
    }

    public String getFrom() {
        return mFrom;
    }

    public void setFrom(String from) {
        mFrom = from;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mMessage);
        dest.writeString(mTime);
        dest.writeString(mType);
        dest.writeByte((byte) (mSeen ? 1 : 0));
        dest.writeString(mTimeSeen);
        dest.writeString(mFrom);
    }

    public static class Builder {
        private String mId;
        private String mMessage;
        private String mTime;
        private String mType;
        private boolean mSeen;
        private String mTimeSeen;
        private String mFrom;
        public boolean ShowAvatar = true;
        public boolean ShowTime = true;

        public Builder setId(String id) {
            mId = id;
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setTime(String time) {
            mTime = time;
            return this;
        }

        public Builder setType(String type) {
            mType = type;
            return this;
        }

        public Builder setSeen(boolean seen) {
            mSeen = seen;
            return this;
        }

        public Builder setTimeSeen(String timeSeen) {
            mTimeSeen = timeSeen;
            return this;
        }

        public Builder setFrom(String from) {
            mFrom = from;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

    public static class MessageEntity {
        public static final String CLASS = "Messages";
        public static final String MESSAGES = "Messages";
        public static final String ID = "id";
        public static final String MESSAGE = "message";
        public static final String TIME = "time";
        public static final String Type = "type";
        public static final String SEEN = "seen";
        public static final String TIMESEEN = "timeseen";
        public static final String FROM = "from";

        public static final String TypeText = "text";
        public static final String TypeImage = "image";
        public static final String TypeVideo = "video";

        public static final String STORAGE_IMAGE = "Messages_Image";
        public static final String STORAGE_VIDEO = "Messages_Video";
    }
}
