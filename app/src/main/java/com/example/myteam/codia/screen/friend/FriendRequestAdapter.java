package com.example.myteam.codia.screen.friend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.base.adapter.ListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendRequestAdapter extends ListAdapter<User> {

    private OnFriendRequestClickListener mListener;

    public FriendRequestAdapter(Context context, List<User> data, OnFriendRequestClickListener listener) {
        super(context, data);
        mListener = listener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserViewHolder userViewHolder = (UserViewHolder) holder;
        userViewHolder.bind(mData.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_request, parent, false);
        return new UserViewHolder(view, mListener);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements DataCallback<User> {

        private ImageView mImageAvatar;
        private TextView mTextDisplayName;
        private Button mAcceptButton;
        private Button mDeclineButton;
        private OnFriendRequestClickListener mListener;

        public UserViewHolder(View itemView, OnFriendRequestClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageAvatar = itemView.findViewById(R.id.img_avartar);
            mTextDisplayName = itemView.findViewById(R.id.text_display_name);
            mAcceptButton = itemView.findViewById(R.id.accept_button);
            mDeclineButton = itemView.findViewById(R.id.decline_button);
            mAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onAccept(getAdapterPosition());
                }
            });
            mDeclineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDecline();
                }
            });
        }

        public void bind(User user) {
            if (user == null) {
                return;
            }
            new AuthenicationRepository(new AuthenicationRemoteDataSource()).getUserCodia(user.getId(), this);

        }

        @Override
        public void onGetDataSuccess(User data) {
            mTextDisplayName.setText(data.getDisplayName());
            String urlImage = data.getAvatar();
            if (urlImage == null) {
                mImageAvatar.setImageResource(R.drawable.ic_profile);
            } else {
                Picasso.get().load(urlImage).placeholder(R.drawable.default_avatar)
                        .into(mImageAvatar);
            }
        }

        @Override
        public void onGetDataFailed(String msg) {

        }
    }

    public interface OnFriendRequestClickListener {
        void onAccept(int position);

        void onDecline();
    }
}
