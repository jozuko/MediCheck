package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * データベースモデル
 */
public abstract class ADbType<T> {
    public abstract T getDbValue();

    public String getDbWhereValue(){
        return String.valueOf(getDbValue());
    }

    public abstract void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue);
}
