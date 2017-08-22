package com.studiojozu.medicheck.database.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.repository.Repositories;

/**
 * SQLiteOpenHelper
 * singletonで動作する
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    private static final String DB_FILENAME = "medicheck.db";

    @SuppressLint("StaticFieldLeak")
    @Nullable
    private static DbOpenHelper sDbOpenHelper = null;

    @NonNull
    private final Context mContext;

    private DbOpenHelper(Context context) {
        super(context, DB_FILENAME, null, DB_VERSION);
        mContext = context.getApplicationContext();
    }

    synchronized static DbOpenHelper getInstance(@NonNull Context context) {
        if (sDbOpenHelper == null) sDbOpenHelper = new DbOpenHelper(context);
        return sDbOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (WritableDatabase.isWritableDatabase(db)) return;

        WritableDatabase writableDatabase = new WritableDatabase(db);
        new Repositories().createTables(mContext, writableDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (WritableDatabase.isWritableDatabase(db)) return;

        WritableDatabase writableDatabase = new WritableDatabase(db);
        new Repositories().upgradeTables(mContext, writableDatabase, oldVersion, newVersion);
    }
}
