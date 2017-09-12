package com.studiojozu.medicheck.infrastructure.adapter;

/**
 * 永続化層のトランザクションを定義するI/F
 */
public interface IPersistenceTransaction {
    void beginTransaction();

    void commit();

    void rollback();
}
