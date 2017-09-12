package com.studiojozu.medicheck.domain.model.person;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Set;

public interface PersonRepository {
    Person findPersonById(@NonNull Context context, @NonNull PersonIdType personIdType);

    Set<Person> findAll(@NonNull Context context);

    int existPersonById(@NonNull Context context, @NonNull PersonIdType personIdType);

    void add(@NonNull Context context, @NonNull Person person);

    void remove(@NonNull Context context, @NonNull PersonIdType personIdType);
}
