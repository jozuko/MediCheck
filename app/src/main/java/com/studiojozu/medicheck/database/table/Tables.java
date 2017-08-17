package com.studiojozu.medicheck.database.table;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.DbOpenHelper;
import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * テーブル一覧を管理するクラス
 */
public class Tables {

    @NonNull
    private static final ABaseTable[] TABLES;

    static {
        TABLES = new ABaseTable[]{
                new MedicineTable(),
                new MediTimeRelationTable(),
                new ParsonTable(),
                new ParsonMediRelationTable(),
                new ScheduleTable(),
                new SettingTable(),
                new TimetableTable()
        };
    }

    public void createTables(@NonNull Context context, @Nullable WritableDatabase db) {
        upgradeTables(context, db, 1, DbOpenHelper.DB_VERSION);
    }

    public void upgradeTables(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            for (ABaseTable entity : TABLES) {
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
