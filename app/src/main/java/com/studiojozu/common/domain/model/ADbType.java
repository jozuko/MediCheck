package com.studiojozu.common.domain.model;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * データベースモデル
 */
public abstract class ADbType<T, C extends ADbType> implements Serializable, Cloneable {

    private static final long serialVersionUID = -7199799314817003653L;

    /**
     * データベースで保持する値を返却する
     *
     * @return データベース値
     */
    protected abstract T getDbValue();

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

    public abstract String getDisplayValue();

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (!(obj instanceof ADbType))) return false;
        return getDbValue().equals(((ADbType) obj).getDbValue());
    }

    @Override
    public int hashCode() {
        return getDbValue().hashCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public C clone() {
        try {
            return (C) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

}
