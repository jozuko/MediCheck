package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * データベースモデル
 */
public interface IDbType<T> {
    public T getDbValue();
    public String getDbWhereValue();
    public void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue);
}
