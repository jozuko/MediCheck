package com.studiojozu.common.domain.model.general;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;

import org.jetbrains.annotations.NotNull;

/**
 * intの型クラス
 */
public abstract class TextType<C extends TextType<C>> extends ADbType<String, C> implements Comparable<TextType> {
    private static final long serialVersionUID = -4630676994700703038L;
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

    @NonNull
    @Override
    public String getDisplayValue() {
        return mValue;

    }
}
