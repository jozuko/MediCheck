package com.studiojozu.medicheck.database.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

/**
 * Writableのデータベースを管理するクラス
 */
public class WritableDatabase extends ADatabase {

    WritableDatabase(@NonNull SQLiteDatabase db) {
        super(db);
        if (!isWritableDatabase(db)) throw new IllegalArgumentException("db is not writable.");
    }

    @Contract("null -> false")
    static boolean isWritableDatabase(@Nullable SQLiteDatabase db) {
        return db != null && db.isOpen() && !db.isReadOnly();
    }

    public void beginTransaction() {
        mSQLiteDatabase.beginTransaction();
    }

    public void rollbackTransaction() {
        if (!inTransaction()) return;
        mSQLiteDatabase.endTransaction();
    }

    public void commitTransaction() {
        if (!inTransaction()) return;
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    private boolean inTransaction() {
        return mSQLiteDatabase.inTransaction();
    }

    public void execSQL(@NonNull String sql) {
        mSQLiteDatabase.execSQL(sql);
    }

    public long save(@NonNull String tableName, @NonNull ContentValues insertData) {
        return mSQLiteDatabase.insert(tableName, null, insertData);
    }
}
