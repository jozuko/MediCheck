package com.studiojozu.common.domain.model;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * データベースモデル
 */
public abstract class ADbType<T> {
    /**
     * データベースで保持する値を返却する
     *
     * @return データベース値
     */
    public abstract T getDbValue();

    /**
     * Where句で使用するための、{@link #getDbValue()}のString値
     *
     * @return {@link #getDbValue()}のString値
     */
    public String getDbWhereValue() {
        return String.valueOf(getDbValue());
    }

    /**
     * insertで使用するContentValueに型名と値設定する
     *
     * @param columnName   型名称
     * @param contentValue 値
     */
    public abstract void setContentValue(@NonNull String columnName, @NonNull ContentValues contentValue);

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (!(obj instanceof ADbType))) return false;
        return getDbValue().equals(((ADbType) obj).getDbValue());
    }

    @Override
    public int hashCode() {
        return getDbValue().hashCode();
    }
}
