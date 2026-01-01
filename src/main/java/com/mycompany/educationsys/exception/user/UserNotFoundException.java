package com.mycompany.educationsys.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Object id) {
        super("User with id " + id + " not found!");
    }}
