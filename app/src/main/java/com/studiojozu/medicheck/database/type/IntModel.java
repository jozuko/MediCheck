package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * intの型クラス
 */
public class IntModel implements IDbType<Integer> {
    private final int _value;

    public IntModel() {
        _value = 0;
    }

    public IntModel(int value) {
        _value = value;
    }

    public Integer getDbValue() {
        return _value;
    }

    public String getDbWhereValue(){
        return String.valueOf(getDbValue());
    }

    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }
}
