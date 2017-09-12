package com.studiojozu.medicheck.resource.uicomponent.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.jetbrains.annotations.Contract;

abstract class ACustomView<T> extends LinearLayout {

    final int UNKNOWN_RESOURCE_ID = -1;
    final String STRING_RESOURCE_DEFAULT = "";

    @NonNull
    final Context mContext;
    @NonNull
    final LayoutInflater mLayoutInflater;
    @NonNull
    final T mCustomView;
    @Nullable
    Activity mActivity = null;

    public ACustomView(@NonNull Context context) {
        this(context, null);
    }

    public ACustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mCustomView = getCurrentView(context);
    }

    @LayoutRes
    abstract int layoutResource();

    @Nullable
    @StyleableRes
    abstract int[] styleableResources();

    @SuppressWarnings("unchecked")
    @NonNull
    private T getCurrentView(@NonNull Context context) {
        return (T) mLayoutInflater.inflate(layoutResource(), this);
    }

    @Contract("null -> null")
    @Nullable
    TypedArray getTypedArray(@Nullable AttributeSet attrs) {
        if (attrs == null) return null;

        int[] styleableResources = styleableResources();
        if(styleableResources == null) return null;

        return mContext.obtainStyledAttributes(attrs, styleableResources);
    }

    @NonNull
    @Contract("null, _ -> !null")
    String getAttributeString(@Nullable TypedArray typedArray, @StyleableRes int styleableId) {
        if (typedArray == null) return STRING_RESOURCE_DEFAULT;

        String data = typedArray.getString(styleableId);

        if(data == null) return STRING_RESOURCE_DEFAULT;
        return data;
    }

    @DrawableRes
    int getAttributeDrawableResourceId(@Nullable TypedArray typedArray, @StyleableRes int styleableId) {
        if (typedArray == null) return UNKNOWN_RESOURCE_ID;
        return typedArray.getResourceId(styleableId, UNKNOWN_RESOURCE_ID);
    }

    public void setParentActivity(@NonNull Activity parentActivity) {
        mActivity = parentActivity;
    }
}
