package com.studiojozu.medicheck.database.repository;

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
    final SQLiteDatabase mSQLiteDatabase;

    ADatabase(@NonNull SQLiteDatabase sqLiteDatabase) {
        mSQLiteDatabase = sqLiteDatabase;
    }

    static DbOpenHelper getDbOpenHelper(@NonNull Context context) {
        return DbOpenHelper.getInstance(context.getApplicationContext());
    }

    @NonNull
    public Cursor rawQuery(@NonNull String sql, @Nullable String[] args) {
        return mSQLiteDatabase.rawQuery(sql, args);
    }

    public void close() {
        if (mSQLiteDatabase.isOpen()) mSQLiteDatabase.close();
    }
}
