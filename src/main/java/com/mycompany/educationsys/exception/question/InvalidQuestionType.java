package com.mycompany.educationsys.exception.question;

public class InvalidQuestionType extends RuntimeException{
    public InvalidQuestionType(){
        super("Question type is not valid!");
    }
}
