package com.studiojozu.common.domain.model;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

public interface IValidator {
    @StringRes
    int validate(@Nullable String data);
}
