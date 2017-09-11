package com.studiojozu.medicheck.domain.model.medicine;

import com.studiojozu.common.domain.model.IValidator;
import com.studiojozu.medicheck.R;

/**
 * 薬の名前に対するValidator
 */
public class MedicineNameValidator implements IValidator {

    @Override
    public int validate(String data) {
        if (data == null) return R.string.validation_require;
        if (data.length() == 0) return R.string.validation_require;

        return -1;
    }
}
