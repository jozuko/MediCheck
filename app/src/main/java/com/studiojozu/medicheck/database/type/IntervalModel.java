package com.studiojozu.medicheck.database.type;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * 日付の間隔の型クラス.
 * 〇日おき、毎月〇日の〇を表す
 */
public class IntervalModel extends IntModel {
    public IntervalModel() {
        super(0);
    }

    public IntervalModel(int value) {
        super(value);
    }

}
