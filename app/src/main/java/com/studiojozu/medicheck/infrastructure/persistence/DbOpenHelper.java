package com.studiojozu.medicheck.infrastructure.persistence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.infrastructure.adapter.IPersistenceTransaction;

import org.jetbrains.annotations.Contract;

/**
 * SQLiteOpenHelper
 * singletonで動作する
 */
public class DbOpenHelper extends SQLiteOpenHelper implements IPersistenceTransaction {

    static final int DB_VERSION = 1;
    private static final String DB_FILENAME = "medicheck.db";
    @SuppressLint("StaticFieldLeak")
    @Nullable
    private static DbOpenHelper sDbOpenHelper = null;
    @NonNull
    private final Context mContext;
    @Nullable
    private WritableDatabase mWritableDatabase = null;
    @Nullable
    private ReadonlyDatabase mReadonlyDatabase = null;

    private DbOpenHelper(Context context) {
        super(context, DB_FILENAME, null, DB_VERSION);
        mContext = context.getApplicationContext();
    }

    public synchronized static DbOpenHelper getInstance(@NonNull Context context) {
        if (sDbOpenHelper == null) sDbOpenHelper = new DbOpenHelper(context);
        return sDbOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (isWritableDatabase(db))
            new SqliteRepositories().createTables(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (isWritableDatabase(db))
            new SqliteRepositories().upgradeTables(mContext, db, oldVersion, newVersion);
    }

    @Contract("null -> false")
    private boolean isWritableDatabase(@Nullable SQLiteDatabase db) {
        return db != null && db.isOpen() && !db.isReadOnly();
    }

    ReadonlyDatabase openReadonlyDatabase() {
        synchronized (this) {
            if (mReadonlyDatabase == null || mReadonlyDatabase.isClosed())
                mReadonlyDatabase = new ReadonlyDatabase(this);

            return mReadonlyDatabase;
        }
    }

    WritableDatabase openWritableDatabase() {
        synchronized (this) {
            if (mWritableDatabase == null || mWritableDatabase.isClosed())
                mWritableDatabase = new WritableDatabase(this);

            return mWritableDatabase;
        }
    }

    void closeDatabase() {
        synchronized (this) {
            if (mWritableDatabase != null && mWritableDatabase.inTransaction())
                return;

            closeDatabaseForce();
        }
    }

    private void closeDatabaseForce() {
        synchronized (this) {
            this.close();
            mReadonlyDatabase = null;
            mWritableDatabase = null;
        }
    }

    @Override
    public void beginTransaction() {
        openWritableDatabase();
        if (mWritableDatabase != null)
            mWritableDatabase.beginTransaction();
    }

    @Override
    public void commit() {
        openWritableDatabase();
        if (mWritableDatabase != null)
            mWritableDatabase.commitTransaction();
        closeDatabaseForce();
    }

    @Override
    public void rollback() {
        openWritableDatabase();
        if (mWritableDatabase != null)
            mWritableDatabase.rollbackTransaction();
        closeDatabaseForce();
    }
}
