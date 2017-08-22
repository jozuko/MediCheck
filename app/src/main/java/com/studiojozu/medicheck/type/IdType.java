package com.studiojozu.medicheck.type;

/**
 *
 */
public class IdType extends IntType {

    /** 未定義ID まだデータベースに登録されていない場合に使用する */
    public final static int UNDEFINED_ID = -1;

    public IdType() {
        super(UNDEFINED_ID);
    }

    public IdType(int value) {
        super(value);
    }

}
