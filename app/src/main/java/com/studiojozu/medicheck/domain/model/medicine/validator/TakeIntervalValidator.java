package com.studiojozu.medicheck.domain.model.medicine.validator;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ANumericValidator;
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType;

public class TakeIntervalValidator extends ANumericValidator {

    public TakeIntervalValidator(@NonNull TakeIntervalModeType.DateIntervalPattern intervalPattern) {
        super((intervalPattern == TakeIntervalModeType.DateIntervalPattern.DAYS ? 0 : 1),
                (intervalPattern == TakeIntervalModeType.DateIntervalPattern.DAYS ? 365 : 31),
                /* allowMinValue */true,
                /* allowMaxValue */true);
    }
}
