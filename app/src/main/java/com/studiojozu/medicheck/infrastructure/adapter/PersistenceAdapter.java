package com.studiojozu.medicheck.infrastructure.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.studiojozu.medicheck.domain.model.MediTimeRelationRepository;
import com.studiojozu.medicheck.domain.model.MediTimeViewRepository;
import com.studiojozu.medicheck.domain.model.PersonMediRelationRepository;
import com.studiojozu.medicheck.domain.model.PersonMediViewRepository;
import com.studiojozu.medicheck.domain.model.medicine.MedicineRepository;
import com.studiojozu.medicheck.domain.model.person.PersonRepository;
import com.studiojozu.medicheck.domain.model.schedule.ScheduleRepository;
import com.studiojozu.medicheck.domain.model.setting.SettingRepository;
import com.studiojozu.medicheck.domain.model.setting.TimetableRepository;
import com.studiojozu.medicheck.infrastructure.persistence.DbOpenHelper;
import com.studiojozu.medicheck.infrastructure.persistence.SqliteMediTimeRelationRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqliteMediTimeViewRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqliteMedicineRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqlitePersonMediRelationRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqlitePersonMediViewRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqlitePersonRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqliteScheduleRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqliteSettingRepository;
import com.studiojozu.medicheck.infrastructure.persistence.SqliteTimetableRepository;

/**
 *
 */
public class PersistenceAdapter {

    @NonNull
    public static IPersistenceTransaction getPersistenceTransaction(@NonNull Context context) {
        return DbOpenHelper.getInstance(context);
    }

    @NonNull
    public static MedicineRepository getMedicineRepository() {
        return new SqliteMedicineRepository();
    }

    @NonNull
    public static MediTimeViewRepository getMediTimeViewRepository() {
        return new SqliteMediTimeViewRepository();
    }

    @NonNull
    public static PersonMediViewRepository getPersonMediViewRepository() {
        return new SqlitePersonMediViewRepository();
    }

    @NonNull
    public static PersonMediRelationRepository getPersonMediRelationRepository() {
        return new SqlitePersonMediRelationRepository();
    }

    @NonNull
    public static MediTimeRelationRepository getMediTimeRelationRepository() {
        return new SqliteMediTimeRelationRepository();
    }

    @NonNull
    public static ScheduleRepository getScheduleRepository() {
        return new SqliteScheduleRepository();
    }

    @NonNull
    public static TimetableRepository getTimetableRepository() {
        return new SqliteTimetableRepository();
    }

    @NonNull
    public static SettingRepository getSettingRepository() {
        return new SqliteSettingRepository();
    }

    @NonNull
    public static PersonRepository getPersonRepository() {
        return new SqlitePersonRepository();
    }
}