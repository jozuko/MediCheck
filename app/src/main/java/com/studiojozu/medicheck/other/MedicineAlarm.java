package com.studiojozu.medicheck.other;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.database.entity.ColumnBase;
import com.studiojozu.medicheck.database.entity.MedicineEntity;
import com.studiojozu.medicheck.database.entity.ParsonEntity;
import com.studiojozu.medicheck.database.entity.ParsonMediRelationEntity;
import com.studiojozu.medicheck.database.entity.ScheduleEntity;
import com.studiojozu.medicheck.database.entity.SchedulePlanDateTimeComparator;
import com.studiojozu.medicheck.database.entity.SettingEntity;
import com.studiojozu.medicheck.database.entity.TimetableEntity;
import com.studiojozu.medicheck.database.type.DateModel;
import com.studiojozu.medicheck.database.type.IDbType;
import com.studiojozu.medicheck.database.type.IntModel;
import com.studiojozu.medicheck.database.type.TimeModel;

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
    private final Context _context;

    @NonNull
    private final TimetableEntity _timetableEntity;

    @NonNull
    private final SettingEntity _settingEntity;

    @NonNull
    private final  ParsonMediRelationEntity _parsonMediRelationEntity;

    @NonNull
    private final MedicineEntity _medicineEntity;

    @NonNull
    private final ParsonEntity _parsonEntity;

    public MedicineAlarm(@NonNull Context context) {
        _context = context.getApplicationContext();
        _timetableEntity = new TimetableEntity();
        _settingEntity = new SettingEntity();
        _parsonMediRelationEntity = new ParsonMediRelationEntity();
        _medicineEntity = new MedicineEntity();
        _parsonEntity = new ParsonEntity();
    }

    /**
     * データベースに登録されているスケジュールから、アラームが必要なスケジュールを抽出し、スケジュール設定する。
     */
    void setNotification() {
        List<Map<ColumnBase, IDbType>> needAlarmSchedules = getNeedAlarmSchedules();

        TreeSet<Map<ColumnBase, IDbType>> targetSchedules = new TreeSet<>(new SchedulePlanDateTimeComparator(_context));
        for (Map<ColumnBase, IDbType> scheduleRecord : needAlarmSchedules) {
            if (isNeedAlarm(scheduleRecord)) targetSchedules.add(scheduleRecord);
        }
        if (targetSchedules.size() == 0) return;

        Notification notification = createNotification(targetSchedules);
        NotificationManager manager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        manager.notify(NOTIFICATION_MEDICINE, notification);
    }

    private List<Map<ColumnBase, IDbType>> getNeedAlarmSchedules() {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        return scheduleEntity.getNeedAlerts(_context);
    }

    private boolean isNeedAlarm(Map<ColumnBase, IDbType> scheduleRecord) {

        // 現在日時を取得する
        Calendar now = Calendar.getInstance();

        // 年月日時分が一致しない場合は終了
        DateModel alarmDate = (DateModel) scheduleRecord.get(ScheduleEntity.COLUMN_PLAN_DATE);
        if (!alarmDate.equals(now)) return false;

        // 時分が一致する場合はtrueを返却する
        TimeModel alarmTime = getScheduleTime((Integer) scheduleRecord.get(ScheduleEntity.COLUMN_TIMETABLE_ID).getDbValue());
        if (alarmTime.equals(now)) return true;

        // リマインダが不要ならAlertも不要
        if (!_settingEntity.isUseRemind(_context)) return false;

        // リマインダタイムアウトならAlertは不要
        if (_settingEntity.isRemindTimeout(_context, now, alarmDate, alarmTime)) return false;

        // リマインドタイミングならAlertする
        return (_settingEntity.isRemindTiming(_context, now, alarmDate, alarmTime));
    }

    private TimeModel getScheduleTime(int timetableId) {
        Map<ColumnBase, IDbType> timetable = _timetableEntity.findTimetable(_context, timetableId);
        return (TimeModel) timetable.get(TimetableEntity.COLUMN_TIME);
    }

    @Nullable
    private Notification createNotification(TreeSet<Map<ColumnBase, IDbType>> targetSchedules) {

        String notificationMessage = getNotificationMessage(targetSchedules);
        if(notificationMessage.length() == 0) return null;

        Notification.Builder notificationBuilder = new Notification.Builder(_context);

        notificationBuilder.setSmallIcon(R.mipmap.notification_icon);
        Bitmap largeIcon = BitmapFactory.decodeResource(_context.getResources(), R.mipmap.notification_action_icon);
        notificationBuilder.setLargeIcon(largeIcon);

        notificationBuilder.setTicker(_context.getResources().getString(R.string.notification_medicine_title));
        notificationBuilder.setContentTitle(_context.getResources().getString(R.string.notification_medicine_title));
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
    private String getNotificationMessage(TreeSet<Map<ColumnBase, IDbType>> targetSchedules) {

        StringBuilder builder = new StringBuilder();
        for(Map<ColumnBase, IDbType> targetSchedule : targetSchedules) {
            TreeSet<IntModel> parsonIds = _parsonMediRelationEntity.findParsonIdsByMedicineId(_context, (IntModel)targetSchedule.get(ScheduleEntity.COLUMN_MEDICINE_ID));
            if(parsonIds == null) continue;

            Map<ColumnBase, IDbType> medicines = _medicineEntity.findById(_context, (IntModel)targetSchedule.get(ScheduleEntity.COLUMN_MEDICINE_ID));
            if(medicines == null) continue;

            builder.append(createMedicineLine(parsonIds, medicines));
        }

        return builder.toString();
    }

    @NonNull
    private String createMedicineLine(@NonNull TreeSet<IntModel> parsonIds, Map<ColumnBase, IDbType> medicines) {
        StringBuilder builder = new StringBuilder();

        for(IntModel parsonId : parsonIds) {
            Map<ColumnBase, IDbType> parson = _parsonEntity.findById(_context, parsonId);
            if(parson == null) continue;

            builder.append(parson.get(ParsonEntity.COLUMN_NAME).getDbValue());
            builder.append(" ");
            builder.append(medicines.get(MedicineEntity.COLUMN_NAME).getDbValue());
            builder.append("\n");
        }

        return builder.toString();
    }
}
