package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.resource.uicomponent.BitmapViewComponent;

/**
 * リストViewに表示するための人DTO
 */
public class ImageSingleSelectItem<T> {
    @Nullable
    private final Uri mImageFileUri;
    @NonNull
    private final String mText;
    @Nullable
    private final T mTag;
    @DrawableRes
    private final int mImageResourceId;
    @Nullable
    private BitmapViewComponent mBitmapViewComponent = null;

    public ImageSingleSelectItem(@NonNull String text, @Nullable Uri imageFilePath, @Nullable T tag) {
        mText = text;
        mImageFileUri = imageFilePath;
        mImageResourceId = 0;
        mTag = tag;
    }

    public ImageSingleSelectItem(@NonNull String text, @DrawableRes int imageResourceId, @Nullable T tag) {
        mText = text;
        mImageFileUri = null;
        mImageResourceId = imageResourceId;
        mTag = tag;
    }

    @Nullable
    public Uri getImageFileUri() {
        return mImageFileUri;
    }

    @DrawableRes
    public int getImageResourceId() {
        return mImageResourceId;
    }

    @NonNull
    public String getText() {
        return mText;
    }

    @Nullable
    public T getTag() {
        return mTag;
    }

    void setBitmapViewComponent(@NonNull BitmapViewComponent bitmapViewComponent) {
        mBitmapViewComponent = bitmapViewComponent;
    }

    void recycleBitmapViewComponent() {
        if (mBitmapViewComponent == null)
            return;

        mBitmapViewComponent.recycle();
    }
}