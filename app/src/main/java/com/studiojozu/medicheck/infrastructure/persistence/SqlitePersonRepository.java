package com.studiojozu.medicheck.infrastructure.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.studiojozu.common.domain.model.ADbType;
import com.studiojozu.medicheck.R;
import com.studiojozu.medicheck.domain.model.person.Person;
import com.studiojozu.medicheck.domain.model.person.PersonIdType;
import com.studiojozu.medicheck.domain.model.person.PersonRepository;
import com.studiojozu.medicheck.infrastructure.preferences.PreferenceRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 薬を飲む人テーブル
 * <ol>
 * <li>name 名前</li>
 * <li>photo 写真</li>
 * </ol>
 */
public class SqlitePersonRepository extends ABaseRepository implements PersonRepository {
    /** 飲む人ID */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_ID = new ColumnBase("person_id", ColumnPattern.PARSON_ID, PrimaryPattern.Primary);
    /** 名前 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_NAME = new ColumnBase("person_name", ColumnPattern.PARSON_NAME);
    /** 写真 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_PHOTO = new ColumnBase("person_photo", ColumnPattern.PARSON_PHOTO);
    /** 写真 */
    @SuppressWarnings("WeakerAccess")
    public static final ColumnBase COLUMN_DISPLAY_ORDER = new ColumnBase("person_display_order", ColumnPattern.PARSON_DISPLAY_ORDER);
    static final String TABLE_NAME = "person";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_PHOTO);
        columns.add(COLUMN_DISPLAY_ORDER);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase db) {
        if (db == null) return;
        Map<ColumnBase, ADbType> insertData = new HashMap<>();

        insertData.put(COLUMN_ID, COLUMN_ID.mColumnType.createNewInstance(null));
        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.person_self)));
        insertData.put(COLUMN_PHOTO, COLUMN_PHOTO.mColumnType.createNewInstance(""));
        insertData.put(COLUMN_DISPLAY_ORDER, COLUMN_DISPLAY_ORDER.mColumnType.createNewInstance(1L));

        insert(db, insertData);
    }

    @Override
    protected String getUpgradeSQL(int oldVersion, int newVersion) {
        return null;
    }

    @Override
    protected void updateUpgradeData(@NonNull Context context, @Nullable SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing.
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Columns getColumns() {
        return COLUMNS;
    }

    @Override
    public Person getDefaultPerson(@NonNull Context context) {
        PreferenceRepository preferenceRepository = new PreferenceRepository(context);
        PersonIdType personIdType = preferenceRepository.getDefaultPersonId();

        if (personIdType != null) {
            Person person = findPersonById(context, personIdType);
            if (person != null) return person;
        }

        Person person = getFirstPerson(context);
        if (person == null)
            person = createDefaultPerson(context);

        preferenceRepository.setDefaultPersonId(person.getPersonId());
        return person;
    }

    @Nullable
    private Person getFirstPerson(@NonNull Context context) {
        List<Person> personList = findAll(context);
        if (personList == null || personList.size() == 0) return null;
        return personList.get(0);
    }

    private Person createDefaultPerson(@NonNull Context context) {
        Person person = new Person(context.getResources().getString(R.string.person_self), null);
        add(context, person);
        return person;
    }

    @Override
    public Person findPersonById(@NonNull Context context, @NonNull PersonIdType personIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);

        List<Map<ColumnBase, ADbType>> dataList = find(context, COLUMN_ID.getEqualsCondition(), whereList, null);
        if (dataList.size() == 0)
            return null;

        return new SqlitePersonConverter(dataList.get(0)).createFromRecord();
    }

    @Override
    @Nullable
    public List<Person> findAll(@NonNull Context context) {
        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, null, null, COLUMN_DISPLAY_ORDER.mColumnName);
        if (databaseRecords.size() == 0)
            return null;

        List<Person> personSet = new ArrayList<>();
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            Person person = new SqlitePersonConverter(record).createFromRecord();
            personSet.add(person);
        }

        return personSet;
    }

    @Override
    public boolean existPersonById(@NonNull Context context, @NonNull PersonIdType personIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);

        List<Map<ColumnBase, ADbType>> dataList = find(context, COLUMN_ID.getEqualsCondition(), whereList, null);
        return (dataList.size() != 0);
    }

    @Override
    public void add(@NonNull Context context, @NonNull Person person) {
        if (!existPersonById(context, person.getPersonId())) {
            int personCount = size(context);
            person.setDisplayOrder(personCount + 1);
            insert(context, person);
            return;
        }

        update(context, person);
    }

    @Override
    public int size(@NonNull Context context) {
        List<Person> personList = findAll(context);

        if (personList == null) return 0;
        return personList.size();
    }

    @Override
    public void remove(@NonNull Context context, @NonNull PersonIdType personIdType) {
        if (!existPersonById(context, personIdType)) return;

        String whereClause = COLUMN_ID.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);

        delete(context, whereClause, whereList);
    }

    private void insert(@NonNull Context context, @NonNull Person person) {
        Map<ColumnBase, ADbType> insertData = new HashMap<>();
        insertData.put(COLUMN_ID, person.getPersonId());
        insertData.put(COLUMN_NAME, person.getPersonName());
        insertData.put(COLUMN_PHOTO, person.getPersonPhoto());
        insertData.put(COLUMN_DISPLAY_ORDER, person.getPersonDisplayOrder());
        insert(context, insertData);
    }

    private void update(@NonNull Context context, @NonNull Person person) {
        Map<ColumnBase, ADbType> values = new HashMap<>();
        values.put(COLUMN_NAME, person.getPersonName());
        values.put(COLUMN_PHOTO, person.getPersonPhoto());
        values.put(COLUMN_DISPLAY_ORDER, person.getPersonDisplayOrder());

        String whereClause = COLUMN_ID.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(person.getPersonId());

        update(context, values, whereClause, whereList);
    }
}
