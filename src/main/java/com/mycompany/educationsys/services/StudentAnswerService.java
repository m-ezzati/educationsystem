package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.StudentAnswerRegister;

public interface StudentAnswerService {
    void studentAnswer(StudentAnswerRegister answer, Long studentId);
}
