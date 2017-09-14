package com.studiojozu.medicheck.resource.uicomponent.listview;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 複数選択リストViewに表示するためのDTO
 */
public class MultiSelectItem<T> extends ASelectItem<T> {

    private boolean mChecked;

    private MultiSelectItem(@NonNull String text, @Nullable String subText, @Nullable T tag, boolean checked) {
        super(text, subText, tag);
        mChecked = checked;
    }

    private MultiSelectItem(@NonNull String text, @Nullable String subText, @Nullable Uri imageFilePath, @Nullable T tag, boolean checked) {
        super(text, subText, imageFilePath, tag);
        mChecked = checked;
    }

    private MultiSelectItem(@NonNull String text, @Nullable String subText, @DrawableRes int imageResourceId, @Nullable T tag, boolean checked) {
        super(text, subText, imageResourceId, tag);
        mChecked = checked;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public static class Builder<T> extends ASelectItem.Builder<Builder<T>, T> {

        private boolean mBuilderChecked;

        public Builder(String mainText) {
            super(mainText);
        }

        public Builder setChecked(boolean checked) {
            mBuilderChecked = checked;
            return this;
        }

        @SuppressWarnings("unchecked")
        public MultiSelectItem<T> build() {
            if (mBuilderImageFileUri != null)
                return new MultiSelectItem<>(mBuilderMainText, mBuilderSubText, mBuilderImageFileUri, mBuilderTag, mBuilderChecked);

            if (mBuilderImageResourceId != NO_USE_ICON)
                return new MultiSelectItem<>(mBuilderMainText, mBuilderSubText, mBuilderImageResourceId, mBuilderTag, mBuilderChecked);

            return new MultiSelectItem<>(mBuilderMainText, mBuilderSubText, mBuilderTag, mBuilderChecked);
        }
    }
}