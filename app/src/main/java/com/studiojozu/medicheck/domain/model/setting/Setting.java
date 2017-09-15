package com.studiojozu.medicheck.domain.model.setting;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.DateType;
import com.studiojozu.common.domain.model.general.TimeType;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TreeMap;

/**
 * 設定を画面から受信し、DBに保存する。また、DBのデータを画面表示用に加工する。
 */
public class Setting implements Serializable {

    private static final long serialVersionUID = -8960841441881026848L;
    /** リマンダ機能を使用するか？ */
    @NonNull
    private UseReminderType mUseReminder = new UseReminderType();
    /** リマンダ機能のインターバル時間 */
    @NonNull
    private RemindIntervalType mRemindInterval = new RemindIntervalType();
    /** リマンダ機能のタイムアウト時間 */
    @NonNull
    private RemindTimeoutType mRemindTimeout = new RemindTimeoutType();

    public Setting(@NonNull UseReminderType useReminder, @NonNull RemindIntervalType remindInterval, @NonNull RemindTimeoutType remindTimeout) {
        mUseReminder = useReminder;
        mRemindInterval = remindInterval;
        mRemindTimeout = remindTimeout;
    }

    /**
     * リマンダ機能を使用するかを設定する
     *
     * @param useReminder リマンダ機能を使用する場合はtrueを設定する
     */
    public void setUseReminder(boolean useReminder) {
        mUseReminder = new UseReminderType(useReminder);
    }

    /**
     * リマンダ機能のタイムアウト時間を設定する
     *
     * @param timeoutMinute タイムアウト時間（分）.
     *                      選択できる時間は、{@link #getRemindTimeoutMap(Context)} で取得する
     */
    public void setRemindTimeout(int timeoutMinute) {
        mRemindTimeout = new RemindTimeoutType(timeoutMinute);
    }

    /**
     * リマンダ機能のインターバルを設定する
     *
     * @param intervalMinute リマンダ機能のインターバル(分).
     *                       選択できる時間は、{@link #getRemindIntervalMap(Context)} で取得する
     */
    public void setRemindInterval(int intervalMinute) {
        mRemindInterval = new RemindIntervalType(intervalMinute);
    }

    public boolean useReminder() {
        return mUseReminder.isTrue();
    }


    /**
     * パラメータnowに指名した時刻が、リマインド機能の限界時間を超えているか？
     *
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド機能の限界時間を超えている場合はtrueを返却する
     */
    public boolean isRemindTimeout(@NonNull Calendar now, @NonNull DateType scheduleDate, @NonNull TimeType scheduleTime) {
        ReminderDatetimeType reminderDatetimeType = new ReminderDatetimeType(now.getTimeInMillis());
        return mRemindTimeout.isTimeout(reminderDatetimeType, scheduleDate, scheduleTime);
    }

    /**
     * パラメータnowに指名した時刻が、リマインド時刻であるか？
     *
     * @param now          現在日時
     * @param scheduleDate アラーム予定日付
     * @param scheduleTime アラーム予定時刻
     * @return リマインド時刻である場合はtrueを返却する
     */
    public boolean isRemindTiming(@NonNull Calendar now, @NonNull DateType scheduleDate, @NonNull TimeType scheduleTime) {
        ReminderDatetimeType reminderDateTime = new ReminderDatetimeType(scheduleDate, scheduleTime);
        ReminderDatetimeType currentDateTime = new ReminderDatetimeType(now.getTimeInMillis());

        long diffMinutes = reminderDateTime.diffMinutes(currentDateTime);
        return (diffMinutes % mRemindInterval.getDbValue() == 0);
    }


    /**
     * リマインド機能のタイムアウト時間として、選択可能な時間(分)と対応する文言を返却する。
     *
     * @return リマインド機能のタイムアウト時間一覧
     */
    @NonNull
    public TreeMap<Integer, String> getRemindTimeoutMap(@NonNull Context context) {
        return RemindTimeoutType.getAllValues(context.getApplicationContext());
    }

    /**
     * リマインド機能のインターバルとして、選択可能な時間(分)と対応する文言を返却する。
     *
     * @return リマインド機能のインターバル一覧
     */
    public TreeMap<Integer, String> getRemindIntervalMap(@NonNull Context context) {
        return RemindIntervalType.getAllValues(context.getApplicationContext());
    }


}
