package com.studiojozu.medicheck.domain.model.medicine.validator;

import com.studiojozu.common.domain.model.ANumericValidator;

public class DateNumberValidator extends ANumericValidator {
    public DateNumberValidator() {
        super(1, 365, /* allowMinValue */true, /* allowMaxValue */true);
    }
}
