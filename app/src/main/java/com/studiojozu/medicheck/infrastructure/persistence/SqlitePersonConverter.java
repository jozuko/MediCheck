package com.studiojozu.medicheck.infrastructure.persistence;

import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.person.PersonDisplayOrderType;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;
import com.studiojozu.medicheck.domain.model.person.PersonNameType;
import com.studiojozu.medicheck.domain.model.person.PersonPhotoType;

import java.util.Map;

/**
 * DBのレコードから{@link com.studiojozu.medicheck.domain.model.person.Person}を生成する
 */
class SqlitePersonConverter extends ASqliteConverter<Person> {

    SqlitePersonConverter(@Nullable Map<ColumnBase, ADbType> databaseRecord) {
        super(databaseRecord);
    }

    @Override
    @Nullable
    Person createFromRecord() {
        if (mDatabaseRecord == null)
            return null;

        return new Person(
                getPersonIdType(),
                getPersonNameType(),
                getPersonPhotoType(),
                getPersonDisplayOrder()
        );
    }

    private PersonIdType getPersonIdType() {
        PersonIdType personIdType = (PersonIdType) getData(SqlitePersonRepository.COLUMN_ID);
        if (personIdType != null) return personIdType;
        return new PersonIdType();
    }

    private PersonNameType getPersonNameType() {
        PersonNameType personNameType = (PersonNameType) getData(SqlitePersonRepository.COLUMN_NAME);
        if (personNameType != null) return personNameType;
        return new PersonNameType();
    }

    private PersonPhotoType getPersonPhotoType() {
        PersonPhotoType personPhotoType = (PersonPhotoType) getData(SqlitePersonRepository.COLUMN_PHOTO);
        if (personPhotoType != null) return personPhotoType;
        return new PersonPhotoType();
    }

    private PersonDisplayOrderType getPersonDisplayOrder() {
        PersonDisplayOrderType personDisplayOrderType = (PersonDisplayOrderType) getData(SqlitePersonRepository.COLUMN_DISPLAY_ORDER);
        if (personDisplayOrderType != null) return personDisplayOrderType;
        return new PersonDisplayOrderType(-1);
    }
}
