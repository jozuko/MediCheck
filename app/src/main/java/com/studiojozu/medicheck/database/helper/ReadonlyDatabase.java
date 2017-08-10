package com.studiojozu.medicheck.database.helper;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ReadOnlyのデータベースを管理するクラス
 */
public class ReadonlyDatabase extends ADatabase {

    public ReadonlyDatabase(@NonNull Context context) {
        super(ADatabase.getDbOpenHelper(context).getReadableDatabase());
    }
}
