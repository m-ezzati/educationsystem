package com.mycompany.educationsys.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Object id) {
        super("User with id " + id + " not found!");
    }}
