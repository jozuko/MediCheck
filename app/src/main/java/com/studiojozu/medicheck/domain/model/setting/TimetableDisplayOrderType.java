package com.studiojozu.medicheck.domain.model.setting;

import com.studiojozu.common.domain.model.general.LongType;

/**
 *
 */
public class TimetableDisplayOrderType extends LongType implements Cloneable {

    public TimetableDisplayOrderType() {
        super();
    }

    public TimetableDisplayOrderType(Object value) {
        super(value);
    }

    @Override
    public TimetableDisplayOrderType clone() {
        try {
            return (TimetableDisplayOrderType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }
}
