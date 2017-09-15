package com.studiojozu.common.domain.model.general;

import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * IDを管理するクラス
 */
public abstract class IdType<C extends IdType<C>> extends TextType<C> {

    private static final long serialVersionUID = 2407858114489390816L;

    protected IdType() {
        super(newId());
    }

    protected IdType(Object value) {
        super((value == null ? newId() : value));
    }

    @NonNull
    private static String newId() {
        return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
    }
}
