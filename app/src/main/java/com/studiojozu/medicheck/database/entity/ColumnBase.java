package com.studiojozu.medicheck.database.entity;

import android.support.annotation.NonNull;

/**
 *
 */
public class ColumnBase {
    @NonNull
    final String _columnName;
    @NonNull
    final ABaseEntity.ColumnType _type;
    final ABaseEntity.NullType _nullType;
    final ABaseEntity.PrimayType _primayType;
    final ABaseEntity.AutoIncrementType _autoIncrementType;

    ColumnBase(@NonNull String columnName, @NonNull ABaseEntity.ColumnType type, ABaseEntity.AutoIncrementType autoIncrementType) {
        _columnName = columnName;
        _type = type;
        _nullType = ABaseEntity.NullType.NotNull;
        _primayType = ABaseEntity.PrimayType.Primary;
        _autoIncrementType = autoIncrementType;
    }

    ColumnBase(@NonNull String columnName, @NonNull ABaseEntity.ColumnType type, ABaseEntity.PrimayType primayType) {
        _columnName = columnName;
        _type = type;
        _nullType = ABaseEntity.NullType.NotNull;
        _primayType = primayType;
        _autoIncrementType = ABaseEntity.AutoIncrementType.NotAutoIncrement;
    }

    ColumnBase(@NonNull String columnName, @NonNull ABaseEntity.ColumnType type) {
        _columnName = columnName;
        _type = type;
        _nullType = ABaseEntity.NullType.NotNull;
        _primayType = ABaseEntity.PrimayType.NotPrimary;
        _autoIncrementType = ABaseEntity.AutoIncrementType.NotAutoIncrement;
    }

    String getEqualsCondition() {
        return _columnName + "=?";
    }
}
