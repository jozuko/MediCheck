package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

/**
 * Writableのデータベースを管理するクラス
 */
class WritableDatabase extends ADatabase {

    WritableDatabase(@NonNull Context context) {
        super(ADatabase.getDbOpenHelper(context).getWritableDatabase());
    }

    WritableDatabase(@NonNull SQLiteDatabase db) {
        super(db);
        if (!isWritableDatabase(db)) throw new IllegalArgumentException("db is not writable.");
    }

    @Contract("null -> false")
    static boolean isWritableDatabase(@Nullable SQLiteDatabase db) {
        return db != null && db.isOpen() && !db.isReadOnly();
    }

    void beginTransaction() {
        mSQLiteDatabase.beginTransaction();
    }

    void rollbackTransaction() {
        if (!inTransaction()) return;
        mSQLiteDatabase.endTransaction();
    }

    void commitTransaction() {
        if (!inTransaction()) return;
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    private boolean inTransaction() {
        return mSQLiteDatabase.inTransaction();
    }

    void execSQL(@NonNull String sql) {
        mSQLiteDatabase.execSQL(sql);
    }

    long insert(@NonNull String tableName, @NonNull ContentValues values) {
        return mSQLiteDatabase.insert(tableName, null, values);
    }

    long update(@NonNull String tableName, @NonNull ContentValues values, @Nullable String whereClause, @Nullable String[] whereArgs) {
        return mSQLiteDatabase.update(tableName, values, whereClause, whereArgs);
    }

    long delete(@NonNull String tableName, @Nullable String whereClause, @Nullable String[] whereArgs) {
        return mSQLiteDatabase.delete(tableName, whereClause, whereArgs);
    }
}
