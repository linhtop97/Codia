package com.example.myteam.codia.utils.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.myteam.codia.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public final class BindingUtils {

    private BindingUtils() {
    }

    /**
     * setAdapter For RecyclerView
     */
    @BindingAdapter({"recyclerAdapter"})
    public static void setAdapterForRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"bind:imgAvatar"})
    public static void setImageAvartar(CircleImageView imageView, String urlImage) {
        if (urlImage == null) {
            imageView.setImageResource(R.drawable.default_avatar);
        } else {
            Picasso.get().load(urlImage).placeholder(R.drawable.default_avatar)
                    .into(imageView);
        }
    }

    @BindingAdapter({"bind:imgCover"})
    public static void setImageCover(ImageView imageView, String urlImage) {
        if (urlImage != null) {
            Picasso.get().load(urlImage)
                    .into(imageView);
        }
    }
}
