package com.studiojozu.medicheck.database.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.BooleanType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Schedule
 * <ol>
 * <li>medicine_id 服用薬ID</li>
 * <li>plan_date 服用予定日付</li>
 * <li>timetable_id 服用予定タイムテーブルID</li>
 * <li>need_alert Alertいる？</li>
 * <li>is_take 服用した？</li>
 * <li>alert_datetime Alertする日時(リマインダ時間ずつ増加する)</li>
 * <li>take_datetime 服用した日時</li>
 * </ol>
 */
public class ScheduleRepository extends ABaseRepository {
    /** 薬ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnPattern.ID, PrimaryPattern.Primary);
    /** 服用予定日付 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PLAN_DATE = new ColumnBase("plan_date", ColumnPattern.DATE, PrimaryPattern.Primary);
    /** 服用予定タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase("timetable_id", ColumnPattern.ID, PrimaryPattern.Primary);
    /** Alertいる？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NEED_ALERT = new ColumnBase("need_alert", ColumnPattern.BOOL);
    /** 服用した？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_IS_TAKE = new ColumnBase("is_take", ColumnPattern.BOOL);
    /** 服用した日時 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TAKE_DATETIME = new ColumnBase("take_datetime", ColumnPattern.DATETIME);

    static {
        TABLE_NAME = "schedule";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_MEDICINE_ID);
        columns.add(COLUMN_PLAN_DATE);
        columns.add(COLUMN_TIMETABLE_ID);
        columns.add(COLUMN_NEED_ALERT);
        columns.add(COLUMN_IS_TAKE);
        columns.add(COLUMN_TAKE_DATETIME);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable WritableDatabase db) {
        // do nothing.
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable WritableDatabase db, int oldVersion, int newVersion) {
        // do nothing.
    }

    /**
     * need_alert=true, is_take=falseの条件を満たすレコードを返却する
     *
     * @param context アプリケーションコンテキスト
     * @return need_alert=true, is_take=falseの条件を満たすレコード一覧
     */
    @NonNull
    public List<Map<ColumnBase, ADbType>> getNeedAlerts(@NonNull Context context) {
        ReadonlyDatabase readonlyDatabase = new ReadonlyDatabase(context);
        try {
            ArrayList<ADbType> whereList = new ArrayList<>();
            whereList.add(new BooleanType(true));
            whereList.add(new BooleanType(false));

            return find(readonlyDatabase, COLUMN_NEED_ALERT.getEqualsCondition() + " and " + COLUMN_IS_TAKE.getEqualsCondition(), whereList);
        } finally {
            readonlyDatabase.close();
        }
    }
}
