package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 単一選択リストViewに表示するためのDTO
 */
public class SingleSelectItem<T> extends ASelectItem<T> {

    private SingleSelectItem(@NonNull String text, @Nullable String subText, @Nullable T tag) {
        super(text, subText, tag);
    }

    private SingleSelectItem(@NonNull String text, @Nullable String subText, @Nullable Uri imageFilePath, @Nullable T tag) {
        super(text, subText, imageFilePath, tag);
    }

    private SingleSelectItem(@NonNull String text, @Nullable String subText, @DrawableRes int imageResourceId, @Nullable T tag) {
        super(text, subText, imageResourceId, tag);
    }

    public static class Builder<T> extends ASelectItem.Builder<Builder<T>, T> {
        public Builder(String mainText) {
            super(mainText);
        }

        @SuppressWarnings("unchecked")
        public SingleSelectItem<T> build() {
            if (mBuilderImageFileUri != null)
                return new SingleSelectItem<>(mBuilderMainText, mBuilderSubText, mBuilderImageFileUri, mBuilderTag);

            if (mBuilderImageResourceId != NO_USE_ICON)
                return new SingleSelectItem<>(mBuilderMainText, mBuilderSubText, mBuilderImageResourceId, mBuilderTag);

            return new SingleSelectItem<>(mBuilderMainText, mBuilderSubText, mBuilderTag);
        }
    }
}