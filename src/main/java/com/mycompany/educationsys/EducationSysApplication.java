package com.mycompany.educationsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EducationSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationSysApplication.class, args);
    }

}
