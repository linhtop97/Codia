package com.example.myteam.codia.screen.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.screen.base.adapter.ListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by khanhjm on 21-10-2018.
 */
public class SearchAdapter extends ListAdapter<User> {

    public void setListener(SearchContract.SearchListener mListener) {
        this.mListener = mListener;
    }

    private SearchContract.SearchListener mListener;
    private LayoutInflater mLayoutInflater;
    public static final String TAG = SearchAdapter.class.toString();

    public SearchAdapter(Context context, List<User> data) {
        super(context, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_search_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData == null) return;
        ((ViewHolder) holder).bindData(mData.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView displayname;
        private TextView email;
        private CircleImageView avatar;
        private User user;

        public ViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar);
            displayname = itemView.findViewById(R.id.text_display_name);
            email = itemView.findViewById(R.id.text_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) return;
                    mListener.onSelect(user);
                }
            });
        }

        public void bindData(User element) {
            if (element == null) return;
            user = element;
            displayname.setText(user.getDisplayName());
            email.setText(user.getEmail());
            if (user.getAvatar() == null || user.getAvatar().isEmpty())
                Picasso.get().load(R.drawable.default_avatar).into(avatar);
            else
                Picasso.get().load(user.getAvatar()).into(avatar);
        }
    }
}
