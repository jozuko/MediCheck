package com.studiojozu.medicheck.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.entity.Entities;

/**
 * SQLiteOpenHelper
 * singletonで動作する
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_FILENAME = "medicheck.db";
    public static final int DB_VERSION = 1;

    @Nullable
    private static DbOpenHelper _helper = null;
    @NonNull
    private final Context _context;

    synchronized static DbOpenHelper getInstance(@NonNull Context context) {
        if (_helper == null) _helper = new DbOpenHelper(context);
        return _helper;
    }

    private DbOpenHelper(Context context) {
        super(context, DB_FILENAME, null, DB_VERSION);
        _context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (WritableDatabase.isWritableDatabase(db)) return;

        WritableDatabase writableDatabase = new WritableDatabase(this, db);
        new Entities().createTables(_context, writableDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (WritableDatabase.isWritableDatabase(db)) return;

        WritableDatabase writableDatabase = new WritableDatabase(this, db);
        new Entities().upgradeTables(_context, writableDatabase, oldVersion, newVersion);
    }
}
