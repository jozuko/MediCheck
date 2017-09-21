package com.studiojozu.medicheck.domain.model.person;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public interface PersonRepository {
    @NonNull
    Person getDefaultPerson(@NonNull Context context);

    Person findPersonById(@NonNull Context context, @NonNull PersonIdType personIdType);

    List<Person> findAll(@NonNull Context context);

    boolean existPersonById(@NonNull Context context, @NonNull PersonIdType personIdType);

    int size(@NonNull Context context);

    void add(@NonNull Context context, @NonNull Person person);

    void remove(@NonNull Context context, @NonNull PersonIdType personIdType);
}
