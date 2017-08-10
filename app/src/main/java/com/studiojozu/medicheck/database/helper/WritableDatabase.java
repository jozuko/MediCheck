package com.studiojozu.medicheck.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

/**
 * Writableのデータベースを管理するクラス
 */
public class WritableDatabase extends ADatabase {

    WritableDatabase(@NonNull Context context) {
        super(ADatabase.getDbOpenHelper(context).getWritableDatabase());
    }

    WritableDatabase(@NonNull SQLiteDatabase db) {
        super(db);
        if (!isWritableDatabase(db)) throw new IllegalArgumentException("db is not writable.");
    }

    @Contract("null -> false")
    static boolean isWritableDatabase(@Nullable SQLiteDatabase db) {
        if (db == null) return false;
        if (!db.isOpen()) return false;
        return !db.isReadOnly();
    }

    public void beginTransaction() {
        _database.beginTransaction();
    }

    public void rollbackTransaction() {
        if (!inTransaction()) return;
        _database.endTransaction();
    }

    public void commitTransaction() {
        if (!inTransaction()) return;
        _database.setTransactionSuccessful();
        _database.endTransaction();
    }

    private boolean inTransaction() {
        return _database.inTransaction();
    }

    public void execSQL(@NonNull String sql) {
        _database.execSQL(sql);
    }

    public long save(@NonNull String tableName, @NonNull ContentValues insertData) {
        return _database.insert(tableName, null, insertData);
    }
}
