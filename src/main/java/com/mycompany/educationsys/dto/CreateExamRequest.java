package com.mycompany.educationsys.dto;

public class CreateExamRequest {
    private String title;
    private String description;
    private Integer duration;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return duration;
    }
}
