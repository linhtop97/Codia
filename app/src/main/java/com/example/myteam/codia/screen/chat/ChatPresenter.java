package com.example.myteam.codia.screen.chat;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Message;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.utils.DateTimeUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by khanhjm on 23-10-2018.
 */
public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.ViewModel mViewModel;
    private DataCallback<User> mUserDataCallback;
    private User mUserChat;
    private String mUserChatId;
    private String mUserLoginId;
    private DatabaseReference mReference;
    private FirebaseStorage mStorage;

    private static final int TOTAL_ITEMS_TO_LOAD = 40;
    private int mCurrentPage = 1;
    private int itemPos = 0;
    private String lastKey = "";
    private String prevKey = "";

    public ChatPresenter(ChatContract.ViewModel viewModel, DataCallback<User> userDataCallback) {
        mViewModel = viewModel;
        mUserLoginId = ((ChatViewModel) mViewModel).getUserLoginId();
        mUserChatId = ((ChatViewModel) mViewModel).getUserChatId();
        mUserDataCallback = userDataCallback;
        mReference = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
    }

    @Override
    public void LoadMessage() {
        Query query = mReference.child(Message.MessageEntity.CLASS).child(mUserLoginId).child(mUserChatId)
                .limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);

//        mReference.child(Message.MessageEntity.CLASS).child(mUserLoginId).child(mUserChatId)
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.getKey();
                String messagetext = (String) dataSnapshot.child(Message.MessageEntity.MESSAGE).getValue();
                long time = (long) dataSnapshot.child(Message.MessageEntity.TIME).getValue();
                String type = (String) dataSnapshot.child(Message.MessageEntity.Type).getValue();
                boolean seen = (boolean) dataSnapshot.child(Message.MessageEntity.SEEN).getValue();
                long timeseen;
                if (seen) {
                    timeseen = (long) dataSnapshot.child(Message.MessageEntity.TIMESEEN).getValue();
                } else {
                    timeseen = 0;
                }
                String from = (String) dataSnapshot.child(Message.MessageEntity.FROM).getValue();
                Message message = new Message.Builder().setId(id)
                        .setMessage(messagetext)
                        .setTime(convertTime(time))
                        .setType(type)
                        .setSeen(seen)
                        .setTimeSeen(convertTime(timeseen))
                        .setFrom(from)
                        .build();

                itemPos++;
                if (itemPos == 1) {
                    prevKey = lastKey = id;
                }

                ((ChatViewModel) mViewModel).getMessageList().add(message);
                ((ChatViewModel) mViewModel).getAdapter().notifyDataSetChanged();

                mViewModel.ScrollRecycleView();
                mViewModel.StopRefresh();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
    public void LoadMore() {
        mCurrentPage++;
        itemPos = 0;

        Query query = mReference.child(Message.MessageEntity.CLASS).child(mUserLoginId).child(mUserChatId)
                .orderByKey().endAt(lastKey)
                .limitToLast(TOTAL_ITEMS_TO_LOAD);

//        mReference.child(Message.MessageEntity.CLASS).child(mUserLoginId).child(mUserChatId)
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.getKey();
                String messagetext = (String) dataSnapshot.child(Message.MessageEntity.MESSAGE).getValue();
                long time = (long) dataSnapshot.child(Message.MessageEntity.TIME).getValue();
                String type = (String) dataSnapshot.child(Message.MessageEntity.Type).getValue();
                boolean seen = (boolean) dataSnapshot.child(Message.MessageEntity.SEEN).getValue();
                long timeseen;
                if (seen) {
                    timeseen = (long) dataSnapshot.child(Message.MessageEntity.TIMESEEN).getValue();
                } else {
                    timeseen = 0;
                }
                String from = (String) dataSnapshot.child(Message.MessageEntity.FROM).getValue();
                Message message = new Message.Builder().setId(id)
                        .setMessage(messagetext)
                        .setTime(convertTime(time))
                        .setType(type)
                        .setSeen(seen)
                        .setTimeSeen(convertTime(timeseen))
                        .setFrom(from)
                        .build();

                if (prevKey.equals(id)) {
                    prevKey = lastKey;
                } else {
                    ((ChatViewModel) mViewModel).getMessageList().add(itemPos++, message);
                    ((ChatViewModel) mViewModel).getAdapter().notifyDataSetChanged();
                }
                mViewModel.StopRefresh();
                if (itemPos == 1) {
                    lastKey = id;
                }
                ((ChatViewModel) mViewModel).getLinearLayoutManager().scrollToPositionWithOffset(itemPos > 0 ? itemPos - 1 : itemPos, 0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
    public void sendMessage(String message) {
        DatabaseReference message_push = mReference.child(Message.MessageEntity.CLASS)
                .child(mUserLoginId).child(mUserChatId).push();
        String push_id = message_push.getKey();

        Map messageMap = new HashMap();
        messageMap.put(Message.MessageEntity.MESSAGE, message);
        messageMap.put(Message.MessageEntity.SEEN, false);
        messageMap.put(Message.MessageEntity.TIME, DateTimeUtils.getCurrentTime());
        messageMap.put(Message.MessageEntity.Type, Message.MessageEntity.TypeText);
        messageMap.put(Message.MessageEntity.FROM, mUserLoginId);

        Map messageUserMap = new HashMap();
        messageUserMap.put(Message.MessageEntity.CLASS + "/" + mUserLoginId + "/" + mUserChatId + "/" + push_id, messageMap);
        messageUserMap.put(Message.MessageEntity.CLASS + "/" + mUserChatId + "/" + mUserLoginId + "/" + push_id, messageMap);

        mReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d("SEND_MESSAGE_LOG", databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void upload(Uri uri) {
        final String push_id = mReference.child(Message.MessageEntity.CLASS)
                .child(mUserLoginId).child(mUserChatId).push().getKey();
        final StorageReference filepath = mStorage.getReference().child(Message.MessageEntity.STORAGE_IMAGE).child(push_id + ".jpg");
        UploadTask uploadTask = filepath.putFile(uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.d("UPLOAD_IMAGE", task.getException().toString());
                    return null;
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String download_url = downloadUri.toString();

                    Map messageMap = new HashMap();
                    messageMap.put(Message.MessageEntity.MESSAGE, download_url);
                    messageMap.put(Message.MessageEntity.SEEN, false);
                    messageMap.put(Message.MessageEntity.TIME, DateTimeUtils.getCurrentTime());
                    messageMap.put(Message.MessageEntity.Type, Message.MessageEntity.TypeImage);
                    messageMap.put(Message.MessageEntity.FROM, mUserLoginId);

                    Map messageUserMap = new HashMap();
                    messageUserMap.put(Message.MessageEntity.CLASS + "/" + mUserLoginId + "/" + mUserChatId + "/" + push_id, messageMap);
                    messageUserMap.put(Message.MessageEntity.CLASS + "/" + mUserChatId + "/" + mUserLoginId + "/" + push_id, messageMap);

                    mReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Log.d("SEND_MESSAGE_IMAGE_LOG", databaseError.getMessage());
                            }
                        }
                    });

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    @Override
    public void onStart() {
        LoadInfoChatUser();
    }

    public String convertLastLogin(boolean isOnline, long lastLogin) {
        if (isOnline) {
            return "Recently online";
        } else {
            String result = "Last seen ";
            long now = DateTimeUtils.getCurrentTime();
            long space = now - lastLogin;
            long oneDay = 24 * 60 * 60 * 1000;
            long oneHour = 60 * 60 * 1000;
            long oneMinute = 60 * 1000;
            if (space > oneDay) {
                Date date = new Date(lastLogin);
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
                String dateFormatted = formatter.format(date);
                result += dateFormatted;
            } else if (space > oneHour) {
                long hourLong = space / oneHour;
                result += (int) hourLong + " hours ago";
            } else if (space > oneMinute) {
                long minuteLong = space / oneMinute;
                result += (int) minuteLong + " minutes ago";
            } else if (space > 0) {
                result += "1 minutes ago";
            } else {
                result = "Recently online";
            }
            return result;
        }
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        return formatter.format(date);
    }

    public void LoadInfoChatUser() {
        mReference.child(User.UserEntity.USERS)
                .child(((ChatViewModel) mViewModel).getUserChatId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id = dataSnapshot.getKey();
                        String avatar = (String) dataSnapshot.child(User.UserEntity.AVATAR).getValue();
                        String displayName = (String) dataSnapshot.child(User.UserEntity.DISPLAYNAME).getValue();
                        boolean isOnline = (boolean) dataSnapshot.child(User.UserEntity.ISONLINE).getValue();
                        long lastLogin = (long) dataSnapshot.child(User.UserEntity.LASTLOGIN).getValue();
                        mUserChat = new User.Builder().setId(id)
                                .setAvatar(avatar)
                                .setDisplayName(displayName)
                                .setIsOnline(isOnline)
                                .setLastLogin(convertLastLogin(isOnline, lastLogin))
                                .build();
                        mUserDataCallback.onGetDataSuccess(mUserChat);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mViewModel.getProfileError(R.string.get_message_error);
                    }
                });
    }

    @Override
    public void onStop() {

    }
}
