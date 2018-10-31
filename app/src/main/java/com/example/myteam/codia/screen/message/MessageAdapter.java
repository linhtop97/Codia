package com.example.myteam.codia.screen.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Chat;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.screen.base.adapter.ListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khanhjm on 22-10-2018.
 */
public class MessageAdapter extends ListAdapter<Chat> {

    public void setListener(MessageContract.MessageListener listener) {
        mListener = listener;
    }

    private MessageContract.MessageListener mListener;
    private LayoutInflater mLayoutInflater;

    public MessageAdapter(Context context, List<Chat> data) {
        super(context, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData == null) return;
        ((ViewHolder) holder).bindData(mData.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView displayname;
        private TextView lastmessage;
        private TextView lasttime;
        private CircleImageView avatar;
        private CircleImageView online;
        private Chat mChat;

        public ViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            displayname = itemView.findViewById(R.id.text_display_name);
            lastmessage = itemView.findViewById(R.id.text_last_message);
            lasttime = itemView.findViewById(R.id.text_last_time);
            avatar = itemView.findViewById(R.id.avatar);
            online = itemView.findViewById(R.id.online);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) return;
                    mListener.onSelect(mChat);
                }
            });
        }

        public void bindData(Chat element) {
            if (element == null) return;
            mChat = element;
            lastmessage.setText(mChat.getLastMessage());
            lasttime.setText(mChat.getLastTime());
            setUser(mChat.getId());
        }

        private void setUser(String userId) {
            String userLoginId = new SharedPrefsImpl(mContext).get(SharedPrefsKey.PREF_USER_ID, String.class);
            final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();

            mReference.child(Chat.ChatEntity.CLASS).child(userLoginId).child(mChat.getId())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String textlastMessage = (String) dataSnapshot.child(Chat.ChatEntity.LASTMESSAGE).getValue();
                            long longlastTime = (long) dataSnapshot.child(Chat.ChatEntity.LASTTIME).getValue();
                            lastmessage.setText(textlastMessage);
                            lasttime.setText(convertTime(longlastTime));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            mReference.child(User.UserEntity.USERS).child(userId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = (String) dataSnapshot.child(User.UserEntity.DISPLAYNAME).getValue();
                            boolean isOnline = (boolean) dataSnapshot.child(User.UserEntity.ISONLINE).getValue();
                            String link = (String) dataSnapshot.child(User.UserEntity.AVATAR).getValue();
                            if (isOnline) online.setVisibility(View.VISIBLE);
                            else online.setVisibility(View.GONE);
                            displayname.setText(name);
                            if (link == null || link.isEmpty()) {
                                avatar.setImageResource(R.drawable.ic_profile);
                            } else {
                                Picasso.get().load(link).into(avatar);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("SEND_MESSAGE_LOG", databaseError.getMessage());
                            Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        return formatter.format(date);
    }
}
