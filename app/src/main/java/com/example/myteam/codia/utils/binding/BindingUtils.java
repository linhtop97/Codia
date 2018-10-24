package com.example.myteam.codia.utils.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myteam.codia.R;
import com.squareup.picasso.Picasso;

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

    @BindingAdapter("imgResource")
    public static void setImage(ImageView imageView, String urlImage) {
        if (urlImage == null) {
            imageView.setImageResource(R.drawable.ic_profile);
        } else {
            Picasso.get().load(urlImage)
                    .into(imageView);
        }
    }
}
