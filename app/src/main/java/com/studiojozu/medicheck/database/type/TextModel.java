package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * intの型クラス
 */
public class TextModel extends ADbType<String> {
    private final String _value;

    public TextModel() {
        _value = "";
    }

    public TextModel(String value) {
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
}
