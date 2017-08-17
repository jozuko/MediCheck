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
    protected final SQLiteDatabase mSQLiteDatabase;

    public ADatabase(@NonNull SQLiteDatabase sqLiteDatabase) {
        mSQLiteDatabase = sqLiteDatabase;
    }

    protected static DbOpenHelper getDbOpenHelper(@NonNull Context context) {
        return DbOpenHelper.getInstance(context.getApplicationContext());
    }

    @NonNull
    public Cursor rawQuery(@NonNull String sql, @Nullable String[] args) {
        return mSQLiteDatabase.rawQuery(sql, args);
    }

    public void close() {
        if (mSQLiteDatabase == null) return;
        if (mSQLiteDatabase.isOpen()) mSQLiteDatabase.close();
    }
}
