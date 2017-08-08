package com.studiojozu.medicheck.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * ReadOnlyのデータベースを管理するクラス
 */
public class ReadonlyDatabase extends ADatabase {

    @NonNull
    private final DbOpenHelper _dbOpenHelper;

    @NonNull
    private final Context _context;

    @NonNull private final SQLiteDatabase _readonlyDatabase;

    ReadonlyDatabase(@NonNull Context context) {
        _context = context.getApplicationContext();
        _dbOpenHelper = DbOpenHelper.getInstance(_context);
        _readonlyDatabase = _dbOpenHelper.getReadableDatabase();
    }

}
