package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * テーブル一覧を管理するクラス
 */
class SqliteRepositories {

    @NonNull
    private static final ABaseRepository[] TABLES;

    static {
        TABLES = new ABaseRepository[]{
                new SqliteMedicineViewRepository(),
                new SqliteMediTimeRelationRepository(),
                new SqlitePersonRepository(),
                new SqlitePersonMediRelationRepository(),
                new SqliteScheduleRepository(),
                new SqliteSettingRepository(),
                new SqliteTimetableRepository(),
                new SqliteMediTimeViewRepository(),
                new SqlitePersonMediViewRepository()
        };
    }

    public void createTables(@NonNull Context context, @Nullable SQLiteDatabase db) {
        upgradeTables(context, db, 1, DbOpenHelper.DB_VERSION);
    }

    public void upgradeTables(@NonNull Context context, @Nullable SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db == null) return;

        db.beginTransaction();
        try {
            for (ABaseRepository entity : TABLES) {
                entity.createTable(context, db);
                entity.upgradeTable(context, db, oldVersion, newVersion);
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            db.endTransaction();
            throw e;
        }
    }
}
