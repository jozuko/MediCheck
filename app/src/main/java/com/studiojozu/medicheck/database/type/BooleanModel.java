package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * booleanの型クラス
 */
public class BooleanModel implements IDbType<Integer> {

    private final boolean _value;

    public BooleanModel(boolean value) {
        _value = value;
    }

    public BooleanModel(int value) {
        _value = (value == 1);
    }

    public boolean isTrue() {
        return _value;
    }

    public Integer getDbValue() {
        return (_value ? 1 : 0);
    }

    public String getDbWhereValue(){
        return String.valueOf(getDbValue());
    }

    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue) {
        contentValue.put(columnName, getDbValue());
    }
}
