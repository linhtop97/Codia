package com.example.myteam.codia.screen.post;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myteam.codia.R;
import com.example.myteam.codia.data.model.Post;
import com.example.myteam.codia.data.model.User;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRemoteDataSource;
import com.example.myteam.codia.data.source.remote.auth.AuthenicationRepository;
import com.example.myteam.codia.data.source.remote.auth.DataCallback;
import com.example.myteam.codia.screen.base.adapter.ListAdapter;
import com.example.myteam.codia.screen.base.adapter.OnLoadMoreListener;
import com.example.myteam.codia.utils.Constant;
import com.example.myteam.codia.utils.DateTimeUtils;
import com.example.myteam.codia.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends ListAdapter<Post> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;

    public PostAdapter(Context context, RecyclerView recyclerView, List<Post> data) {
        super(context, data);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder) {
            PostViewHolder postViewHolder = (PostViewHolder) holder;
            postViewHolder.bind(mData.get(position));
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        private LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder implements DataCallback<User> {

        private CircleImageView mImageAvatar;
        private ImageView mImagePost;
        private TextView mDisplayName;
        private TextView mDateCreated;
        private TextView mContent;
        private ImageView mImagePrivacy;

        public PostViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.img_avartar);
            mImagePost = itemView.findViewById(R.id.img_post);
            mDisplayName = itemView.findViewById(R.id.text_display_name);
            mDateCreated = itemView.findViewById(R.id.text_date_created);
            mContent = itemView.findViewById(R.id.text_content);
            mImagePrivacy = itemView.findViewById(R.id.img_privacy);
        }

        public void bind(Post post) {
            new AuthenicationRepository(new AuthenicationRemoteDataSource()).getUserCodia(post.getUidUser(), this);
            mDateCreated.setText(DateTimeUtils.dateTimeToString(DateTimeUtils.convertTimeMillisToDate(Long.parseLong(post.getDateCreated()))));
            mContent.setText(post.getContent());
            String imgPrivacy = post.getPrivacy();
            mImagePrivacy.setVisibility(View.VISIBLE);
            if (imgPrivacy.equals(Constant.PUBLIC)) {
                mImagePrivacy.setBackgroundResource(R.drawable.ic_public);
            } else if (imgPrivacy.equals(Constant.FRIEND)) {
                mImagePrivacy.setBackgroundResource(R.drawable.ic_friend_black);
            } else {
                mImagePrivacy.setBackgroundResource(R.drawable.ic_only_me);
            }

            String imagePost = post.getImage();
            if (imagePost != null) {
                Bitmap bm = ImageUtils.base64toImage(imagePost);
                if (bm != null) {
                    mImagePost.setVisibility(View.VISIBLE);
                    mImagePost.setImageBitmap(bm);
                }
            } else {
                mImagePost.setVisibility(View.GONE);
            }
        }

        @Override
        public void onGetDataSuccess(User data) {
            mDisplayName.setText(data.getDisplayName());
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
}
