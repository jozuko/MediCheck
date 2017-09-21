package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.general.BooleanType;

public class DeleteFlagType extends BooleanType<DeleteFlagType> {
    private static final long serialVersionUID = -4357707301598095745L;

    public DeleteFlagType() {
        super(false);
    }

    public DeleteFlagType(boolean value) {
        super(value);
    }

    public DeleteFlagType(@NonNull Object value) {
        super(value);
    }
}
