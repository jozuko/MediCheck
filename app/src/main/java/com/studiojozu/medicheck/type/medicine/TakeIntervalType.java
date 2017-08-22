package com.studiojozu.medicheck.type.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.medicheck.type.general.LongType;

/**
 * 日付の間隔の型クラス.
 * 〇日おき、毎月〇日の〇を表す
 */
public class TakeIntervalType extends LongType {
    public TakeIntervalType() {
        super(0);
    }

    public TakeIntervalType(@NonNull Object value) {
        super(value);
    }

}
