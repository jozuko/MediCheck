package com.studiojozu.medicheck.database.type;

/**
 * 日付の間隔の型クラス.
 * 〇日おき、毎月〇日の〇を表す
 */
public class IntervalType extends IntType {
    public IntervalType() {
        super(0);
    }

    public IntervalType(int value) {
        super(value);
    }

}
