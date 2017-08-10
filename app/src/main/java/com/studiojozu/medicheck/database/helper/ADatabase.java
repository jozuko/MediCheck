package com.studiojozu.medicheck.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * データベースクラスの基底クラス
 */
public abstract class ADatabase {

    @NonNull
    protected final SQLiteDatabase _database;

    public ADatabase(@NonNull SQLiteDatabase database) {
        _database = database;
    }

    protected static DbOpenHelper getDbOpenHelper(Context context) {
        return DbOpenHelper.getInstance(context.getApplicationContext());
    }

    @NonNull
    public Cursor rawQuery(@NonNull String sql, @Nullable String[] args) {
        return _database.rawQuery(sql, args);
    }
}
