package org.alas.backend.services;


import org.alas.backend.documents.Exam;
import org.alas.backend.repositories.ExamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    @Autowired
    ExamsRepository examsRepository;

    public void createExam(Exam exam) {
        examsRepository.save(exam);
        System.out.println(exam.toString());
    }
}
