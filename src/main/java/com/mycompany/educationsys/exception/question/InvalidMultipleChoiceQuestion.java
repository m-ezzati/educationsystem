package com.mycompany.educationsys.exception.question;

public class InvalidMultipleChoiceQuestion extends RuntimeException {
    public InvalidMultipleChoiceQuestion(){
        super("The Multiple choice question must have more than 2 choice! ");
    }
}
