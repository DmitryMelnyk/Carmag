package com.carpedia.carmagazine.util;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by d264 on 12/26/17.
 */

public class BindingAdapters {

    @BindingAdapter("visibleShow")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView image, String url) {
        Glide.with(image)
                .load(url)
                .into(image);
    }

    @BindingAdapter("loadCircleImage")
    public static void loadCircleImage(ImageView image, String url) {
        Glide.with(image)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(image);
    }
}