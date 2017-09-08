package com.studiojozu.medicheck.activity.validator.medicine;

import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.activity.uicomponent.dialog.InputDialogView;

/**
 * 薬の名前に対するValidator
 */
public class MedicineNameValidator implements InputDialogView.IInputValidator {

    @Override
    public int validate(String data) {
        if (data == null) return R.string.validation_require;
        if (data.length() == 0) return R.string.validation_require;

        return -1;
    }
}
