package com.mycompany.educationsys.scheduler;

import com.mycompany.educationsys.entity.exam.StudentExam;
import com.mycompany.educationsys.entity.exam.enums.ExamStatus;
import com.mycompany.educationsys.repository.StudentExamRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ExamScheduler {
    private final StudentExamRepository studentExamRepository;

    public ExamScheduler(StudentExamRepository studentExamRepository) {
        this.studentExamRepository = studentExamRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void closeExpiredExams(){
        List<StudentExam> exams =
                studentExamRepository.findByStatus(ExamStatus.IN_PROGRESS);

        LocalDateTime now = LocalDateTime.now();

        for(StudentExam se: exams){
            LocalDateTime endTime = se.getStartedAt().plusMinutes(se.getExam().getDuration());
            if(now.isAfter(endTime)){
                se.setStatus(ExamStatus.FINISHED);
                se.setFinishedAt(now);
                System.out.println("Exam " + se.getExam().getTitle() + " finished");
            }
        }
    }
}
