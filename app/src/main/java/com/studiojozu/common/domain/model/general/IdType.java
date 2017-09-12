package com.studiojozu.common.domain.model.general;

import java.util.UUID;

/**
 * IDを管理するクラス
 */
public class IdType extends TextType {

    protected IdType() {
        super(UUID.randomUUID().toString().toUpperCase().substring(0, 8));
    }

    protected IdType(Object value) {
        super(value);
    }
}
