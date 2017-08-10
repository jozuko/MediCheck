package com.studiojozu.medicheck.database.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.database.helper.DbOpenHelper;
import com.studiojozu.medicheck.database.helper.WritableDatabase;

/**
 * Entity一覧を管理するクラス
 */
public class Entities {

    @NonNull
    private static final ABaseEntity[] _entities;

    static {
        _entities = new ABaseEntity[]{
                new MedicineEntity(),
                new MediTimeRelationEntity(),
                new ParsonEntity(),
                new ScheduleEntity(),
                new SettingEntity(),
                new TimetableEntity()
        };
    }

    public void createTables(@NonNull Context context, @Nullable WritableDatabase db) {
        upgradeTables(context, db, 1, DbOpenHelper.DB_VERSION);
    }

    public void upgradeTables(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            for (ABaseEntity entity : _entities) {
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
