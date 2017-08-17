package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * intの型クラス
 */
public class TextType extends ADbType<String> implements Comparable<TextType> {
    private final String _value;

    public TextType() {
        _value = "";
    }

    public TextType(String value) {
        _value = value;
    }

    @Override
    public String getDbValue() {
        return _value;
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
