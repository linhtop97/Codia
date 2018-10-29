package com.example.myteam.codia.screen.message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myteam.codia.data.model.Chat;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.screen.chat.ChatViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by khanhjm on 22-10-2018.
 */
public class MessagePresenter implements MessageContract.Presenter {

    private MessageContract.ViewModel mViewModel;
    private String mUserLoginId;
    private DatabaseReference mReference;

    public MessagePresenter(MessageContract.ViewModel viewModel) {
        mViewModel = viewModel;
        mUserLoginId = ((MessageViewModel) mViewModel).getUserLoginId();
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void LoadAll() {
        final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        mReference.child(Chat.ChatEntity.CLASS).child(mUserLoginId)
                .orderByChild(Chat.ChatEntity.LASTTIME)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String id = dataSnapshot.getKey();
                        String chatId = (String) dataSnapshot.child(Chat.ChatEntity.CHAT_ID).getValue();
                        String lastMessage = (String) dataSnapshot.child(Chat.ChatEntity.LASTMESSAGE).getValue();
                        long lastTime = (long) dataSnapshot.child(Chat.ChatEntity.LASTTIME).getValue();
                        boolean seen = (boolean) dataSnapshot.child(Chat.ChatEntity.SEEN).getValue();

                        Chat chat = new Chat.Builder().setId(id).setChatId(chatId)
                                .setLastMessage(lastMessage)
                                .setLastTime(convertTime(lastTime))
                                .setSeen(seen).build();
                        ((MessageViewModel) mViewModel).getChats().add(0, chat);
                        ((MessageViewModel) mViewModel).getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        LoadAll();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void Load() {
        mReference.child(Chat.ChatEntity.CLASS).child(mUserLoginId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ((MessageViewModel) mViewModel).getChats().clear();
                        ((MessageViewModel) mViewModel).getAdapter().notifyDataSetChanged();
                        LoadAll();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    public String convertTime(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        return formatter.format(date);
    }
}
