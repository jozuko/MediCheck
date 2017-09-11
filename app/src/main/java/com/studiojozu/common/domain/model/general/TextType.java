package com.studiojozu.common.domain.model.general;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;

import org.jetbrains.annotations.NotNull;

/**
 * intの型クラス
 */
public class TextType extends ADbType<String> implements Comparable<TextType> {
    @NotNull
    private final String mValue;

    protected TextType() {
        mValue = "";
    }

    protected TextType(@Nullable Object value) {
        if (value == null)
            mValue = "";
        else
            mValue = (String) value;
    }

    @Override
    public String getDbValue() {
        return mValue;
    }

    @Override
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }

    @Override
    public int compareTo(@NonNull TextType target) {
        return getDbValue().compareTo(target.getDbValue());
    }
}
