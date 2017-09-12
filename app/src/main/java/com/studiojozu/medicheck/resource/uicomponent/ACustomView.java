package com.studiojozu.medicheck.resource.uicomponent;

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

public abstract class ACustomView<T> extends LinearLayout {

    protected final int RESOURCE_DEFAULT_INTEGER = Integer.MIN_VALUE;
    protected final String RESOURCE_DEFAULT_STRING = "";
    protected final int UNKNOWN_RESOURCE_ID = -1;

    @NonNull
    protected final Context mContext;
    @NonNull
    protected final T mCustomView;
    @NonNull
    private final LayoutInflater mLayoutInflater;
    @Nullable
    protected Activity mActivity = null;

    public ACustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mCustomView = getCurrentView();
    }

    @LayoutRes
    protected abstract int layoutResource();

    @Nullable
    @StyleableRes
    protected abstract int[] styleableResources();

    @SuppressWarnings("unchecked")
    @NonNull
    private T getCurrentView() {
        return (T) mLayoutInflater.inflate(layoutResource(), this);
    }

    @Contract("null -> null")
    @Nullable
    protected TypedArray getTypedArray(@Nullable AttributeSet attrs) {
        if (attrs == null) return null;

        int[] styleableResources = styleableResources();
        if (styleableResources == null) return null;

        return mContext.obtainStyledAttributes(attrs, styleableResources);
    }

    @NonNull
    @Contract("null, _ -> !null")
    protected String getAttributeString(@Nullable TypedArray typedArray, @StyleableRes int styleableId) {
        if (typedArray == null) return RESOURCE_DEFAULT_STRING;

        String data = typedArray.getString(styleableId);

        if (data == null) return RESOURCE_DEFAULT_STRING;
        return data;
    }

    protected int getAttributeInteger(@Nullable TypedArray typedArray, @StyleableRes int styleableId) {
        if (typedArray == null) return RESOURCE_DEFAULT_INTEGER;
        return typedArray.getInt(styleableId, RESOURCE_DEFAULT_INTEGER);
    }

    @DrawableRes
    protected int getAttributeDrawableResourceId(@Nullable TypedArray typedArray, @StyleableRes int styleableId) {
        if (typedArray == null) return UNKNOWN_RESOURCE_ID;
        return typedArray.getResourceId(styleableId, UNKNOWN_RESOURCE_ID);
    }

    public void setParentActivity(@NonNull Activity parentActivity) {
        mActivity = parentActivity;
    }
}
