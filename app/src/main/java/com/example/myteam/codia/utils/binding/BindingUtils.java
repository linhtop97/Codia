package com.example.myteam.codia.utils.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

public final class BindingUtils {

    private BindingUtils() {
    }

    /**
     * setAdapter For RecyclerView
     */
    @BindingAdapter({"recyclerAdapter"})
    public static void setAdapterForRecyclerView(RecyclerView recyclerView,
                                                 RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }
}
