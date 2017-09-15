package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.resource.uicomponent.BitmapViewComponent;

/**
 * リストViewに表示するためのDTO
 */
abstract class ASelectItem<T> {
    final static int NO_USE_ICON = -1;
    @Nullable
    private final Uri mImageFileUri;
    @NonNull
    private final String mMainText;
    @Nullable
    private final String mSubText;
    @Nullable
    private final T mTag;
    @DrawableRes
    private final int mImageResourceId;
    @Nullable
    private BitmapViewComponent mBitmapViewComponent = null;

    ASelectItem(@NonNull String mainText, @Nullable String subText, @Nullable T tag) {
        mMainText = mainText;
        mSubText = subText;
        mImageFileUri = null;
        mImageResourceId = NO_USE_ICON;
        mTag = tag;
    }

    ASelectItem(@NonNull String mainText, @Nullable String subText, @Nullable Uri imageFilePath, @Nullable T tag) {
        mMainText = mainText;
        mSubText = subText;
        mImageFileUri = imageFilePath;
        mImageResourceId = NO_USE_ICON;
        mTag = tag;
    }

    ASelectItem(@NonNull String mainText, @Nullable String subText, @DrawableRes int imageResourceId, @Nullable T tag) {
        mMainText = mainText;
        mSubText = subText;
        mImageFileUri = null;
        mImageResourceId = imageResourceId;
        mTag = tag;
    }

    @Nullable
    Uri getImageFileUri() {
        return mImageFileUri;
    }

    @DrawableRes
    int getImageResourceId() {
        return mImageResourceId;
    }

    @NonNull
    String getMainText() {
        return mMainText;
    }

    @Nullable
    String getSubText() {
        return mSubText;
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

    abstract static class Builder<B extends Builder<B, T>, T> {
        @NonNull
        final String mBuilderMainText;
        @Nullable
        Uri mBuilderImageFileUri = null;
        @Nullable
        String mBuilderSubText = null;
        @Nullable
        T mBuilderTag = null;
        @DrawableRes
        int mBuilderImageResourceId = NO_USE_ICON;

        Builder(@NonNull String mainText) {
            mBuilderMainText = mainText;
        }

        @SuppressWarnings("unchecked")
        public B setSubText(@NonNull String subText) {
            mBuilderSubText = subText;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B setImageFileUri(@Nullable Uri builderImageFileUri) {
            mBuilderImageFileUri = builderImageFileUri;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B setTag(@Nullable T tag) {
            mBuilderTag = tag;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B setImageResourceId(int builderImageResourceId) {
            mBuilderImageResourceId = builderImageResourceId;
            return (B) this;
        }

        public abstract ASelectItem<T> build();
    }
}