package com.example.myteam.codia.screen.main;

import android.support.annotation.IntDef;

import static com.example.myteam.codia.screen.main.TabType.FRIEND;
import static com.example.myteam.codia.screen.main.TabType.MESSAGE;
import static com.example.myteam.codia.screen.main.TabType.NEW_FEED;
import static com.example.myteam.codia.screen.main.TabType.NOTIFICATION;
import static com.example.myteam.codia.screen.main.TabType.OPTION;

@IntDef({NEW_FEED, FRIEND, MESSAGE, NOTIFICATION, OPTION})
public @interface TabType {
    int NEW_FEED = 0;
    int FRIEND = 1;
    int MESSAGE = 2;
    int PROFILE = 3;
    int NOTIFICATION = 4;
    int OPTION = 5;
}
