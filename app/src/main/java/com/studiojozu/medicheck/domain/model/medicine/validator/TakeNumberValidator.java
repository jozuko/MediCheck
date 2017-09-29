package com.studiojozu.medicheck.domain.model.medicine.validator;

import com.studiojozu.common.domain.model.ANumericDecimalValidator;

import java.math.BigDecimal;

public class TakeNumberValidator extends ANumericDecimalValidator {
    public TakeNumberValidator() {
        super(new BigDecimal(0), new BigDecimal(Integer.MAX_VALUE), /* allowMinValue */false, /* allowMaxValue */true);
    }
}
