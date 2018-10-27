package com.example.myteam.codia.screen.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Message;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsImpl;
import com.example.myteam.codia.data.source.local.sharedprf.SharedPrefsKey;
import com.example.myteam.codia.screen.base.adapter.ListAdapter;
import com.example.myteam.codia.screen.search.SearchAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khanhjm on 22-10-2018.
 */
public class ChatAdapter extends ListAdapter<Message> {

    private LayoutInflater mLayoutInflater;
    private String mUserLoginId;
    private final int VIEW_TYPE_1 = 1; // receive
    private final int VIEW_TYPE_2 = 2; // send

    public ChatAdapter(Context context, List<Message> data) {
        super(context, data);
        mUserLoginId = new SharedPrefsImpl(mContext).get(SharedPrefsKey.PREF_USER_ID, String.class);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        switch (viewType) {
            case VIEW_TYPE_1:
                view = mLayoutInflater.inflate(R.layout.item_message_receive, parent, false);
                return new ViewHolder(view);
            case VIEW_TYPE_2:
                view = mLayoutInflater.inflate(R.layout.item_message_send, parent, false);
                return new ViewHolderSend(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getFrom().equals(mUserLoginId)) {
            return VIEW_TYPE_2;
        } else {
            return VIEW_TYPE_1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData == null) return;
        if (position > 0) {
            if (mData.get(position).getFrom().equals(mData.get(position - 1).getFrom())) {
                mData.get(position - 1).ShowTime = false;
                mData.get(position).ShowAvatar = false;
            } else {
                mData.get(position - 1).ShowTime = true;
                mData.get(position).ShowAvatar = true;
            }
        }
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_1:
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.bindData(mData.get(position));
                break;
            case VIEW_TYPE_2:
                ViewHolderSend viewHolderSend = (ViewHolderSend) holder;
                viewHolderSend.bindData(mData.get(position));
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageText;
        private TextView time;
        private CircleImageView avatar;
        private Message mMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message);
            time = itemView.findViewById(R.id.text_time);
            avatar = itemView.findViewById(R.id.avatar);
        }

        public void bindData(Message element) {
            if (element == null) return;
            mMessage = element;
            messageText.setText(mMessage.getMessage());
            time.setText(mMessage.getTime());
            setAvatar(mMessage.getFrom());
            if (mMessage.ShowAvatar) avatar.setVisibility(View.VISIBLE);
            else avatar.setVisibility(View.INVISIBLE);
            if (mMessage.ShowTime) time.setVisibility(View.VISIBLE);
            else time.setVisibility(View.GONE);

        }

        private void setAvatar(String userId) {
            final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
            mReference.child(User.UserEntity.USERS).child(userId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String link = (String) dataSnapshot.child(User.UserEntity.AVATAR).getValue();
                            Picasso.get().load(link).into(avatar);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    }

    class ViewHolderSend extends RecyclerView.ViewHolder {
        private TextView messageText;
        private TextView time;
        private Message mMessage;

        public ViewHolderSend(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message);
            time = itemView.findViewById(R.id.text_time);
        }

        public void bindData(Message element) {
            if (element == null) return;
            mMessage = element;
            messageText.setText(mMessage.getMessage());
            time.setText(mMessage.getTime());
            if (mMessage.ShowTime) time.setVisibility(View.VISIBLE);
            else time.setVisibility(View.GONE);
        }
    }
}
