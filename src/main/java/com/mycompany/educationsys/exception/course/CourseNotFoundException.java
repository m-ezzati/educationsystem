package com.mycompany.educationsys.exception.course;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Object id) {
        super("Course with id " + id + " not found!");
    }}
