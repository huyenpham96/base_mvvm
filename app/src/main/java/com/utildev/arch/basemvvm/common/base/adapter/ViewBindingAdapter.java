package com.utildev.arch.basemvvm.common.base.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.utildev.arch.basemvvm.R;

public class ViewBindingAdapter {
    private static RequestOptions requestOptions = new RequestOptions().transforms(new CircleCrop());

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"imageUrl", "progressBar"})
    public static void loadImage(ImageView imageView, String imageUrl, ProgressBar progressBar) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    @BindingAdapter("show")
    public static void setAnimation(ProgressBar progressBar, boolean show) {
        progressBar.startAnimation(AnimationUtils.loadAnimation(progressBar.getContext(),
                show ? R.anim.slide_enter_from_bottom : R.anim.slide_exit_to_bottom));
    }
}
