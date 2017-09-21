package com.studiojozu.medicheck.infrastructure.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.medicheck.domain.model.person.PersonIdType;

public class PreferenceRepository {
    private static final String KEY_DEFAULT_PERSON_ID = "default_person_id";

    @NonNull
    private SharedPreferences mSharedPreferences;

    public PreferenceRepository(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences("medicheck", Context.MODE_PRIVATE);
    }

    @Nullable
    public PersonIdType getDefaultPersonId() {
        String personId = mSharedPreferences.getString(KEY_DEFAULT_PERSON_ID, null);
        if (personId == null) return null;

        return new PersonIdType(personId);
    }

    public void setDefaultPersonId(PersonIdType personIdType) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_DEFAULT_PERSON_ID, personIdType.getDbValue());
        editor.apply();
    }
}
