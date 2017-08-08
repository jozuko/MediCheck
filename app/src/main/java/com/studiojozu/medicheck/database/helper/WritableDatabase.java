package com.studiojozu.medicheck.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.Contract;

/**
 * Writableのデータベースを管理するクラス
 */
public class WritableDatabase extends ADatabase {

    @NonNull
    private final DbOpenHelper _dbOpenHelper;

    @NonNull
    private final SQLiteDatabase _writableDatabase;

    @NonNull
    private boolean _startTransaction = false;

    WritableDatabase(@NonNull Context context) {
        _dbOpenHelper = DbOpenHelper.getInstance(context.getApplicationContext());
        _writableDatabase = _dbOpenHelper.getWritableDatabase();
        _startTransaction = false;
    }

    WritableDatabase(@NonNull DbOpenHelper dbOpenHelper, @NonNull SQLiteDatabase db) {
        if (!isWritableDatabase(db)) throw new IllegalArgumentException("db is not writable.");
        _dbOpenHelper = dbOpenHelper;
        _writableDatabase = db;
        _startTransaction = false;
    }

    @Contract("null -> false")
    static boolean isWritableDatabase(@Nullable SQLiteDatabase db) {
        if (db == null) return false;
        if (!db.isOpen()) return false;
        return !db.isReadOnly();
    }

    public void beginTransaction() {
        _writableDatabase.beginTransaction();
    }

    public void rollbackTransaction() {
        if(!inTransaction()) return;
        _writableDatabase.endTransaction();
    }

    public void commitTransaction() {
        if(!inTransaction()) return;
        _writableDatabase.setTransactionSuccessful();
        _writableDatabase.endTransaction();
    }

    private boolean inTransaction() {
        return _writableDatabase.inTransaction();
    }

    public void execSQL(@NonNull String sql) {
        _writableDatabase.execSQL(sql);
    }
}
