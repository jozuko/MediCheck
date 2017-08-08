package com.studiojozu.medicheck.model;

/**
 * booleanの型クラス
 */
public class BooleanModel {
    private final boolean _value;

    public BooleanModel(boolean value) {
        _value = value;
    }

    public BooleanModel(int value) {
        _value = (value == 1);
    }

    public boolean isTrue() {
        return _value;
    }

    public int getDbValue() {
        return (_value ? 1 : 0);
    }
}
