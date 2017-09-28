package com.studiojozu.medicheck.domain.model.medicine;

import android.support.annotation.NonNull;

import com.studiojozu.common.domain.model.ANumericValidator;

public class TakeIntervalValidator extends ANumericValidator {

    public TakeIntervalValidator(@NonNull TakeIntervalModeType.DateIntervalPattern intervalPattern) {
        super((intervalPattern == TakeIntervalModeType.DateIntervalPattern.DAYS ? 0 : 1),
                (intervalPattern == TakeIntervalModeType.DateIntervalPattern.DAYS ? 365 : 31),
                /* allowMinValue */true,
                /* allowMaxValue */true);
    }
}
