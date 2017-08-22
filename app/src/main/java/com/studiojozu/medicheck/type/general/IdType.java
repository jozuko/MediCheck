package com.studiojozu.medicheck.type.general;

/**
 * IDを管理するクラス
 */
public class IdType extends LongType {

    /** 未定義ID まだデータベースに登録されていない場合に使用する */
    public final static int UNDEFINED_ID = -1;

    public IdType() {
        super(UNDEFINED_ID);
    }

    public IdType(Object value) {
        super(value);
    }

}
