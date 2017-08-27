package com.studiojozu.medicheck.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.entity.Schedule;
import com.studiojozu.medicheck.entity.ScheduleList;
import com.studiojozu.medicheck.type.ADbType;
import com.studiojozu.medicheck.type.medicine.MedicineIdType;
import com.studiojozu.medicheck.type.schedule.IsTakeType;
import com.studiojozu.medicheck.type.schedule.NeedAlarmType;
import com.studiojozu.medicheck.type.schedule.PlanDateType;
import com.studiojozu.medicheck.type.timetable.TimetableIdType;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static final ColumnBase COLUMN_MEDICINE_ID = new ColumnBase("medicine_id", ColumnPattern.MEDICINE_ID, PrimaryPattern.Primary);
    /** 服用予定日付 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PLAN_DATE = new ColumnBase("plan_date", ColumnPattern.SCHEDULE_PLAN_DATE, PrimaryPattern.Primary);
    /** 服用予定タイムテーブルID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TIMETABLE_ID = new ColumnBase("timetable_id", ColumnPattern.TIMETABLE_ID, PrimaryPattern.Primary);
    /** Alertいる？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NEED_ALERT = new ColumnBase("need_alert", ColumnPattern.SCHEDULE_NEED_ALARM);
    /** 服用した？ */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_IS_TAKE = new ColumnBase("is_take", ColumnPattern.SCHEDULE_IS_TAKE);
    /** 服用した日時 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_TOOK_DATETIME = new ColumnBase("took_datetime", ColumnPattern.SCHEDULE_TOOK_DATETIME);

    static {
        TABLE_NAME = "schedule";

        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_MEDICINE_ID);
        columns.add(COLUMN_PLAN_DATE);
        columns.add(COLUMN_TIMETABLE_ID);
        columns.add(COLUMN_NEED_ALERT);
        columns.add(COLUMN_IS_TAKE);
        columns.add(COLUMN_TOOK_DATETIME);
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
            whereList.add(new NeedAlarmType(true));
            whereList.add(new IsTakeType(false));

            return find(readonlyDatabase, COLUMN_NEED_ALERT.getEqualsCondition() + " and " + COLUMN_IS_TAKE.getEqualsCondition(), whereList);
        } finally {
            readonlyDatabase.close();
        }
    }

    /**
     * 薬-タイムテーブルに保存する。
     * 一度すべてを削除し、新規追加する。
     *
     * @param database     Writableなデータベースインスタンス
     * @param medicineId   薬ID
     * @param scheduleList スケジュール一覧
     */
    void save(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId, @NonNull ScheduleList scheduleList) {

        // データの削除
        deleteNotTookMedicineByMedicineId(database, medicineId);

        // スケジュールを保存する
        saveSchedule(database, scheduleList);
    }

    /**
     * 薬IDが一致し、服用されていない薬に関して、テーブルから削除する。
     *
     * @param database   Writableなデータベースインスタンス
     * @param medicineId 薬ID
     */
    private void deleteNotTookMedicineByMedicineId(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId) {

        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition() + " and " + COLUMN_IS_TAKE.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        whereList.add(new IsTakeType(true));

        delete(database, whereClause, whereList);
    }

    /**
     * 薬IDをキーとして、テーブルから該当レコードを削除する。
     *
     * @param database   Writableなデータベースインスタンス
     * @param medicineId 薬ID
     */
    void deleteIgnoreHistory(@NonNull WritableDatabase database, @NonNull MedicineIdType medicineId) {

        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);

        delete(database, whereClause, whereList);
    }

    /**
     * 薬ID, 服用予定日付, タイムテーブルIDを条件としてレコードを取得する
     *
     * @param database     データベースインスタンス
     * @param medicineId   薬ID
     * @param planDateType 服用予定日付
     * @param timetableId  タイムテーブルID
     * @return 条件が一致するレコード(1件 or null)
     */
    @Nullable
    private Map<ColumnBase, ADbType> findByPrimaryKey(@NonNull ADatabase database, @NonNull MedicineIdType medicineId, @NonNull PlanDateType planDateType, @NonNull TimetableIdType timetableId) {

        String whereClause = COLUMN_MEDICINE_ID.getEqualsCondition() + " and " + COLUMN_PLAN_DATE.getEqualsCondition() + " and " + COLUMN_TIMETABLE_ID.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(medicineId);
        whereList.add(planDateType);
        whereList.add(timetableId);

        List<Map<ColumnBase, ADbType>> recordList = find(database, whereClause, whereList);
        if (recordList.size() == 0) return null;

        return recordList.get(0);
    }

    /**
     * スケジュール一覧を登録する
     *
     * @param database     Writableなデータベースインスタンス
     * @param scheduleList スケジュール一覧
     */
    private void saveSchedule(@NonNull WritableDatabase database, @NonNull ScheduleList scheduleList) {

        for (Schedule schedule : scheduleList) {
            // PrimaryKeyが一致するレコードが登録済みの場合はスキップする
            Map<ColumnBase, ADbType> record = findByPrimaryKey(database, schedule.getMedicineId(), schedule.getPlanDate(), schedule.getTimetableId());
            if (record != null) continue;

            // 追加データの作成
            Map<ColumnBase, ADbType> insertData = new HashMap<>();
            insertData.put(COLUMN_MEDICINE_ID, schedule.getMedicineId());
            insertData.put(COLUMN_PLAN_DATE, schedule.getPlanDate());
            insertData.put(COLUMN_TIMETABLE_ID, schedule.getTimetableId());
            insertData.put(COLUMN_NEED_ALERT, schedule.getNeedAlarm());
            insertData.put(COLUMN_IS_TAKE, schedule.getIsTake());
            insertData.put(COLUMN_TOOK_DATETIME, schedule.getTookDatetime());

            // レコード追加
            insert(database, insertData);
        }
    }
}
