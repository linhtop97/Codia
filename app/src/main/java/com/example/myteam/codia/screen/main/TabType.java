package com.example.myteam.codia.screen.main;

import android.support.annotation.IntDef;

import static com.example.myteam.codia.screen.main.TabType.FRIEND;
import static com.example.myteam.codia.screen.main.TabType.MESSAGE;
import static com.example.myteam.codia.screen.main.TabType.NEW_FEED;
import static com.example.myteam.codia.screen.main.TabType.NOTIFICATION;

@IntDef({NEW_FEED, MESSAGE, FRIEND, NOTIFICATION, TabType.MORE})
public @interface TabType {
    int NEW_FEED = 0;
    int MESSAGE = 1;
    int FRIEND = 2;
    int NOTIFICATION = 3;
    int MORE = 4;
}
