package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.NonNull;

/**
 * ReadOnlyのデータベースを管理するクラス
 */
class ReadonlyDatabase extends ADatabase {

    ReadonlyDatabase(@NonNull DbOpenHelper dbOpenHelper) {
        super(dbOpenHelper.getReadableDatabase());
    }
}
