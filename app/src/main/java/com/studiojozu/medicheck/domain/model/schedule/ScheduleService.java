package com.studiojozu.medicheck.domain.model.schedule;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.medicine.Medicine;

/**
 * 薬に関する情報を操作するサービス
 */
public class ScheduleService {
    /**
     * 薬情報を元にスケジュールを生成する
     *
     * @return 生成したスケジュール一覧インスタンス
     */
    public ScheduleList createScheduleList(@NonNull Medicine medicine) {
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.createScheduleList(medicine);

        return scheduleList;
    }
}
