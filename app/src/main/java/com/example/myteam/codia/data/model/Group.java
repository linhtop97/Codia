package com.example.myteam.codia.data.model;

/**
 * Created by khanhjm on 31-10-2018.
 */
public class Group {
    public String Id;
    public String DisplayName;
    public String Email;
    public String Avatar;
    public boolean IsChecked;

    public Group() {
    }

    public static class GroupEntity {
        public static final String CLASS = "Users";
        public static final String GROUPMEMBER = "Users";
        public static final String ID = "id";
        public static final String DISPLAYNAME = "displayName";
        public static final String EMAIL = "email";
        public static final String AVATAR = "avatar";
        public static final String ISCHECKED = "isChecked";
    }
}
