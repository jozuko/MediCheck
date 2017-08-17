package com.studiojozu.medicheck.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.table.ColumnBase;
import com.studiojozu.medicheck.database.table.MedicineTable;
import com.studiojozu.medicheck.database.table.ParsonMediRelationTable;
import com.studiojozu.medicheck.database.table.ParsonTable;
import com.studiojozu.medicheck.database.table.SchedulePlanDateTimeComparator;
import com.studiojozu.medicheck.database.table.ScheduleTable;
import com.studiojozu.medicheck.database.table.SettingTable;
import com.studiojozu.medicheck.database.table.TimetableTable;
import com.studiojozu.medicheck.database.type.ADbType;
import com.studiojozu.medicheck.database.type.DateType;
import com.studiojozu.medicheck.database.type.IntType;
import com.studiojozu.medicheck.database.type.TimeType;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * アラームクラス
 */
public class MedicineAlarm {

    @NonNull
    private static final int NOTIFICATION_MEDICINE = 1;

    @NonNull
    private final Context mContext;

    @NonNull
    private final TimetableTable mTimetableTable;

    @NonNull
    private final SettingTable mSettingTable;

    @NonNull
    private final ParsonMediRelationTable mParsonMediRelationTable;

    @NonNull
    private final MedicineTable mMedicineTable;

    @NonNull
    private final ParsonTable mParsonTable;

    public MedicineAlarm(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mTimetableTable = new TimetableTable();
        mSettingTable = new SettingTable();
        mParsonMediRelationTable = new ParsonMediRelationTable();
        mMedicineTable = new MedicineTable();
        mParsonTable = new ParsonTable();
    }

    /**
     * データベースに登録されているスケジュールから、アラームが必要なスケジュールを抽出し、スケジュール設定する。
     */
    void showNotification() {
        List<Map<ColumnBase, ADbType>> needAlarmSchedules = getNeedAlarmSchedules();

        TreeSet<Map<ColumnBase, ADbType>> targetSchedules = new TreeSet<>(new SchedulePlanDateTimeComparator(mContext));
        for (Map<ColumnBase, ADbType> scheduleRecord : needAlarmSchedules) {
            if (isNeedAlarm(scheduleRecord)) targetSchedules.add(scheduleRecord);
        }
        if (targetSchedules.size() == 0) return;

        Notification notification = createNotification(targetSchedules);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        manager.notify(NOTIFICATION_MEDICINE, notification);
    }

    private List<Map<ColumnBase, ADbType>> getNeedAlarmSchedules() {
        ScheduleTable scheduleTable = new ScheduleTable();
        return scheduleTable.getNeedAlerts(mContext);
    }

    private boolean isNeedAlarm(Map<ColumnBase, ADbType> scheduleRecord) {

        // 現在日時を取得する
        Calendar now = Calendar.getInstance();

        // 年月日時分が一致しない場合は終了
        DateType alarmDate = (DateType) scheduleRecord.get(ScheduleTable.COLUMN_PLAN_DATE);
        if (!alarmDate.equals(now)) return false;

        // 時分が一致する場合はtrueを返却する
        TimeType alarmTime = getScheduleTime((Integer) scheduleRecord.get(ScheduleTable.COLUMN_TIMETABLE_ID).getDbValue());
        if (alarmTime.equals(now)) return true;

        // リマインダが不要ならAlertも不要
        if (!mSettingTable.isUseRemind(mContext)) return false;

        // リマインダタイムアウトならAlertは不要
        if (mSettingTable.isRemindTimeout(mContext, now, alarmDate, alarmTime)) return false;

        // リマインドタイミングならAlertする
        return (mSettingTable.isRemindTiming(mContext, now, alarmDate, alarmTime));
    }

    private TimeType getScheduleTime(int timetableId) {
        Map<ColumnBase, ADbType> timetable = mTimetableTable.findTimetable(mContext, timetableId);
        return (TimeType) timetable.get(TimetableTable.COLUMN_TIME);
    }

    @Nullable
    private Notification createNotification(TreeSet<Map<ColumnBase, ADbType>> targetSchedules) {

        String notificationMessage = getNotificationMessage(targetSchedules);
        if (notificationMessage.length() == 0) return null;

        Notification.Builder notificationBuilder = new Notification.Builder(mContext);

        notificationBuilder.setSmallIcon(R.mipmap.notification_icon);
        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.notification_action_icon);
        notificationBuilder.setLargeIcon(largeIcon);

        notificationBuilder.setTicker(mContext.getResources().getString(R.string.notification_medicine_title));
        notificationBuilder.setContentTitle(mContext.getResources().getString(R.string.notification_medicine_title));
        notificationBuilder.setContentText(notificationMessage);

        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        notificationBuilder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return notificationBuilder.getNotification();
        }
        return notificationBuilder.build();
    }

    @NonNull
    private String getNotificationMessage(TreeSet<Map<ColumnBase, ADbType>> targetSchedules) {

        StringBuilder builder = new StringBuilder();
        for (Map<ColumnBase, ADbType> targetSchedule : targetSchedules) {
            TreeSet<IntType> parsonIds = mParsonMediRelationTable.findParsonIdsByMedicineId(mContext, (IntType) targetSchedule.get(ScheduleTable.COLUMN_MEDICINE_ID));
            if (parsonIds == null) continue;

            Map<ColumnBase, ADbType> medicines = mMedicineTable.findById(mContext, (IntType) targetSchedule.get(ScheduleTable.COLUMN_MEDICINE_ID));
            if (medicines == null) continue;

            builder.append(createMedicineLine(parsonIds, medicines));
        }

        return builder.toString();
    }

    @NonNull
    private String createMedicineLine(@NonNull TreeSet<IntType> parsonIds, Map<ColumnBase, ADbType> medicines) {
        StringBuilder builder = new StringBuilder();

        for (IntType parsonId : parsonIds) {
            Map<ColumnBase, ADbType> parson = mParsonTable.findById(mContext, parsonId);
            if (parson == null) continue;

            builder.append(parson.get(ParsonTable.COLUMN_NAME).getDbValue());
            builder.append(" ");
            builder.append(medicines.get(MedicineTable.COLUMN_NAME).getDbValue());
            builder.append("\n");
        }

        return builder.toString();
    }
}
