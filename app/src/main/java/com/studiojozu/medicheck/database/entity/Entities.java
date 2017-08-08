package com.studiojozu.medicheck.database.entity;

import android.support.annotation.NonNull;

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

        };
    }

    public void createTables(WritableDatabase writableDatabase) {
        upgradeTables(writableDatabase, 1, DbOpenHelper.DB_VERSION);
    }

    public void upgradeTables(WritableDatabase writableDatabase, int oldVersion, int newVersion) {
        writableDatabase.beginTransaction();
        try {
            for (ABaseEntity model : _entities) {
                model.createTable(writableDatabase);
                model.upgradeTable(writableDatabase, oldVersion, newVersion);
            }

            writableDatabase.commitTransaction();
        } catch (Exception e) {
            writableDatabase.rollbackTransaction();
            throw e;
        }
    }
}
