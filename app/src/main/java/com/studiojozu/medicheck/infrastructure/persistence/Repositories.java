package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * テーブル一覧を管理するクラス
 */
class Repositories {

    @NonNull
    private static final ABaseRepository[] TABLES;

    static {
        TABLES = new ABaseRepository[]{
                new MedicineRepository(),
                new MediTimeRelationRepository(),
                new ParsonRepository(),
                new ParsonMediRelationRepository(),
                new ScheduleRepository(),
                new SettingRepository(),
                new TimetableRepository()
        };
    }

    public void createTables(@NonNull Context context, @Nullable WritableDatabase db) {
        upgradeTables(context, db, 1, DbOpenHelper.DB_VERSION);
    }

    public void upgradeTables(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        if (db == null) return;

        db.beginTransaction();
        try {
            for (ABaseRepository entity : TABLES) {
                entity.createTable(context, db);
                entity.upgradeTable(context, db, oldVersion, newVersion);
            }

            db.commitTransaction();
        } catch (Exception e) {
            db.rollbackTransaction();
            throw e;
        }
    }
}
