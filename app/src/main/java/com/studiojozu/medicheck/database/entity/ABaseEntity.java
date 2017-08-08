package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.WritableDatabase;

import org.jetbrains.annotations.Contract;

/**
 * テーブル定義の基底クラス
 * <p>
 * Created by jozuko on 2017/04/25.
 */
public abstract class ABaseEntity {

    protected abstract String getCreateTableSQL();

    protected abstract void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db);

    protected abstract String getUpgradeSQL(int oldVersion, int newVersion);

    protected abstract void updateUpgradeData(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion);

    /**
     * CREATE TABLEを行う
     *
     * @param context    アプリケーションコンテキスト
     * @param db 書き込み可能データベース接続
     */
    void createTable(@NonNull Context context, @Nullable WritableDatabase db) {
        execSQL(db, getCreateTableSQL());
        updateDefaultData(context, db);
    }

    /**
     * バージョンが上がった際のUPDATE TABLEを行う
     *
     * @param context    アプリケーションコンテキスト
     * @param db          書き込み可能データベース接続
     * @param oldVersion 旧バージョンNo
     * @param newVersion 新バージョンNo
     */
    void upgradeTable(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        if (!isNewVersion(oldVersion, newVersion)) return;

        execSQL(db, getUpgradeSQL(oldVersion, newVersion));
        updateUpgradeData(context, db, oldVersion, newVersion);
    }

    @Contract(pure = true)
    private boolean isNewVersion(int oldVersion, int newVersion) {
        if (newVersion == 1) return false;
        return (oldVersion < newVersion);
    }

    private void execSQL(@Nullable WritableDatabase db, @Nullable String sql) {
        if (db == null) return;
        if (sql == null) return;
        if (sql.isEmpty()) return;

        db.execSQL(sql);
    }
}
