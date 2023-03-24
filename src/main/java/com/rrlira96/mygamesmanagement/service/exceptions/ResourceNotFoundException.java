package com.rrlira96.mygamesmanagement.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String id) {
        super("Resource with id " + id + " was not found");
    }
}
