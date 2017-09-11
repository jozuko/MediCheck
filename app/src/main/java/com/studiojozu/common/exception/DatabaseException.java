package com.studiojozu.common.exception;

/**
 * DBアクセスでエラーが発生した場合の例外
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
