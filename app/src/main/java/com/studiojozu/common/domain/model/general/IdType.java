package com.studiojozu.common.domain.model.general;

/**
 * IDを管理するクラス
 */
public class IdType extends LongType {

    /** 未定義ID まだデータベースに登録されていない場合に使用する */
    protected final static int UNDEFINED_ID = -1;

    protected IdType() {
        super(UNDEFINED_ID);
    }

    protected IdType(Object value) {
        super(value);
    }

    public boolean isUndefined() {
        return (getDbValue() == UNDEFINED_ID);
    }
}
