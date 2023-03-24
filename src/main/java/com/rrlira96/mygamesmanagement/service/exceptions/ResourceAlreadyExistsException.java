package com.rrlira96.mygamesmanagement.service.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String name) {
        super("Game " + name + " already exists in the database");
    }
}
