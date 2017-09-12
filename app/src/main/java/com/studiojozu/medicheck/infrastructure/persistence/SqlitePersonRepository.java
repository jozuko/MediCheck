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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
    static final String TABLE_NAME = "person";
    private static final Columns COLUMNS;

    static {
        ArrayList<ColumnBase> columns = new ArrayList<>();
        columns.add(COLUMN_ID);
        columns.add(COLUMN_NAME);
        columns.add(COLUMN_PHOTO);
        COLUMNS = new Columns(columns);
    }

    @Override
    protected void updateDefaultData(@NonNull Context context, @Nullable SQLiteDatabase db) {
        if (db == null) return;
        Map<ColumnBase, ADbType> insertData = new HashMap<>();

        insertData.put(COLUMN_NAME, COLUMN_NAME.mColumnType.createNewInstance(context.getResources().getString(R.string.person_self)));
        insertData.put(COLUMN_PHOTO, COLUMN_PHOTO.mColumnType.createNewInstance(""));
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
    public Set<Person> findAll(@NonNull Context context) {
        List<Map<ColumnBase, ADbType>> databaseRecords = find(context, null, null, null);
        if (databaseRecords.size() == 0)
            return null;

        Set<Person> personSet = new TreeSet<>();
        for (Map<ColumnBase, ADbType> record : databaseRecords) {
            Person person = new SqlitePersonConverter(databaseRecords.get(0)).createFromRecord();
            personSet.add(person);
        }

        return personSet;
    }

    @Override
    public int existPersonById(@NonNull Context context, @NonNull PersonIdType personIdType) {
        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(personIdType);

        List<Map<ColumnBase, ADbType>> dataList = find(context, COLUMN_ID.getEqualsCondition(), whereList, null);
        return dataList.size();
    }

    @Override
    public void add(@NonNull Context context, @NonNull Person person) {
        int size = existPersonById(context, person.getPersonId());
        if (size <= 0) {
            insert(context, person);
            return;
        }

        update(context, person);
    }

    @Override
    public void remove(@NonNull Context context, @NonNull PersonIdType personIdType) {
        int size = existPersonById(context, personIdType);
        if (size <= 0) return;

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
        insert(context, insertData);
    }

    private void update(@NonNull Context context, @NonNull Person person) {
        Map<ColumnBase, ADbType> values = new HashMap<>();
        values.put(COLUMN_NAME, person.getPersonName());
        values.put(COLUMN_PHOTO, person.getPersonPhoto());

        String whereClause = COLUMN_ID.getEqualsCondition();

        ArrayList<ADbType> whereList = new ArrayList<>();
        whereList.add(person.getPersonId());

        update(context, values, whereClause, whereList);
    }
}
