package com.mycompany.educationsys.exception;

public class ForbiddenActionException extends RuntimeException {
    public ForbiddenActionException(String message) {
        super(message);
    }
}
