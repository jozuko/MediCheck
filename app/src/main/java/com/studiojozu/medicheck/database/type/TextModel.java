package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * intの型クラス
 */
public class TextModel implements IDbType<String> {
    private final String _value;

    public TextModel() {
        _value = "";
    }

    public TextModel(String value) {
        _value = value;
    }

    public String getDbValue() {
        return _value;
    }

    public String getDbWhereValue() {
        return String.valueOf(getDbValue());
    }

    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }
}
