package com.studiojozu.medicheck.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.database.entity.Entities;

/**
 * SQLiteOpenHelper
 * singletonで動作する
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    /**
     * データベースファイル名
     */
    private static final String DB_FILENAME = "medicheck.db";

    /**
     * データベースバージョン
     */
    public static final int DB_VERSION = 1;

    /**
     * データベースインスタンス
     */
    private static DbOpenHelper _helper = null;

    synchronized static DbOpenHelper getInstance(@NonNull Context context) {
        if (_helper == null) _helper = new DbOpenHelper(context);
        return _helper;
    }

    private DbOpenHelper(Context context) {
        super(context, DB_FILENAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (WritableDatabase.isWritableDatabase(db)) return;

        WritableDatabase writableDatabase = new WritableDatabase(this, db);
        new Entities().createTables(writableDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (WritableDatabase.isWritableDatabase(db)) return;

        WritableDatabase writableDatabase = new WritableDatabase(this, db);
        new Entities().upgradeTables(writableDatabase, oldVersion, newVersion);
    }
}
