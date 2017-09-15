package com.studiojozu.medicheck.infrastructure.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * データベースクラスの基底クラス
 */
abstract class ADatabase {

    @NonNull
    final SQLiteDatabase mSQLiteDatabase;

    ADatabase(@NonNull SQLiteDatabase sqLiteDatabase) {
        mSQLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    public Cursor rawQuery(@NonNull String sql, @Nullable String[] args) {
        return mSQLiteDatabase.rawQuery(sql, args);
    }

    boolean isClosed() {
        return !mSQLiteDatabase.isOpen();
    }
}
