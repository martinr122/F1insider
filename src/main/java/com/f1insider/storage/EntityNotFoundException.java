package com.f1insider.storage;

public class EntityNotFoundException extends Exception {
    private static final long serialVersionUID = 3881335702567833226L;

    public EntityNotFoundException(String message) {
        super(message);
    }

}
