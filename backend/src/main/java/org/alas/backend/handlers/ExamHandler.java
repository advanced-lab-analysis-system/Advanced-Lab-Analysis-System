package org.alas.backend.handlers;


import org.alas.backend.documents.Question;
import org.alas.backend.documents.Submission;
import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.dto.ExamDTO;
import org.alas.backend.documents.Exam;
import org.alas.backend.dto.ExamDataDTO;
import org.alas.backend.dto.QuestionDTO;
import org.alas.backend.repositories.ExamRepository;
import org.alas.backend.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamHandler {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    private void populateSubmissionCollection(Exam exam){
        List<String> candidateIDs = Arrays.asList("j1","j2","j3","j4","j5");

        submissionRepository.saveAll(candidateIDs.stream().map(candidateID->{
            Submission submission = new Submission();
            submission.setExamId(exam.getExamId());
            submission.setCandidateId(candidateID);
            Map<String, MCQSubmission> eachSubmissionMCQMap = new HashMap<>();
            for(Question question : exam.getQuestions()){
                eachSubmissionMCQMap.put(question.getQuestionId(), new MCQSubmission());
            }
            submission.setAllSubmissions(eachSubmissionMCQMap);
            return submission;
        }).collect(Collectors.toList())).subscribe();
    }

    public Mono<Exam> createExam(Exam exam) {
        populateSubmissionCollection(exam);
        return examRepository.save(exam);
    }

    public Flux<ExamDTO> getAllExams() {
        return examRepository.findAll().map(exam ->
                new ExamDTO(exam.getExamId(),
                        exam.getBatchId(),
                        exam.getExamName(),
                        exam.getSubject(),
                        exam.getNoOfQuestions(),
                        exam.getExamDate(),
                        exam.getExamStartTime(),
                        exam.getExamEndTime(),
                        exam.getAuthor(),
                        exam.getStatus()
                ));
    }

    public Mono<Exam> getExamWithAnswersById(String exam_id) {
        return examRepository.findByExamId(exam_id);
    }

    public Mono<ExamDataDTO> getExamWithoutAnswersById(String exam_id) {
        return examRepository.findByExamId(exam_id).map(exam ->
                new ExamDataDTO(exam.getExamId(),
                        exam.getBatchId(),
                        exam.getExamName(),
                        exam.getSubject(),
                        exam.getNoOfQuestions(),
                        exam.getExamDate(),
                        exam.getExamStartTime(),
                        exam.getExamEndTime(),
                        exam.getAuthor(),
                        exam.getStatus(),
                        exam.getQuestions().stream().map(questionData ->
                                new QuestionDTO(questionData.getQuestionId(), questionData.getOptions())).collect(Collectors.toList())));
    }

    public void endExamByExamId(String examId) {
        submissionRepository.findAllByExamId(examId)
                .map(submission -> examRepository.addSubmissionByExamId(examId,submission).subscribe()).subscribe();
        submissionRepository.deleteAllByExamId(examId).subscribe();
    }
}
