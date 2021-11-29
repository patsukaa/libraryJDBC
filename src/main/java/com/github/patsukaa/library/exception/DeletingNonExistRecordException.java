package com.github.patsukaa.library.exception;

public class DeletingNonExistRecordException extends RuntimeException {

    public DeletingNonExistRecordException() {
    }

    public DeletingNonExistRecordException(String message) {
        super(message);
    }
}
