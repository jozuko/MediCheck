package com.studiojozu.medicheck.repository;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ReadOnlyのデータベースを管理するクラス
 */
class ReadonlyDatabase extends ADatabase {

    ReadonlyDatabase(@NonNull Context context) {
        super(ADatabase.getDbOpenHelper(context).getReadableDatabase());
    }
}
