package com.studiojozu.medicheck.domain.model.person;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.LongType;

/**
 *
 */
public class PersonDisplayOrderType extends LongType implements Cloneable {
    public PersonDisplayOrderType(@NonNull Object value) {
        super(value);
    }

    @Override
    public PersonDisplayOrderType clone() {
        try {
            return (PersonDisplayOrderType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

}
