package com.mycompany.educationsys.exception;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException(Long id) {
        super("Exam with id " + id + " not found!");
    }}
