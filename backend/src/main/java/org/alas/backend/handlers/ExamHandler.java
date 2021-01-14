package org.alas.backend.handlers;


import org.alas.backend.evaluators.MCQEvaluator;
import org.alas.backend.documents.Submission;
import org.alas.backend.dto.*;
import org.alas.backend.documents.Exam;
import org.alas.backend.repositories.ExamRepository;
import org.alas.backend.repositories.UserRepository;
import org.alas.backend.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamHandler {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    public void createExam(Exam exam) {
        populateSubmissionCollection(exam);
        Map<String, Map<String, MCQSubmission>> submissions = new HashMap<>();
        userRepository.findAllByRolesContaining("ROLE_CANDIDATE")
                .subscribe(user -> submissions.put(user.getUsername(),new HashMap<>()),
                        error -> System.err.println("Error: " + error),
                        () -> {
                            exam.setSubmissions(submissions);
                            examRepository.save(exam).subscribe();
                        });
    }

    private void populateSubmissionCollection(Exam exam) {

        submissionRepository.saveAll(userRepository.findAllByRolesContaining("ROLE_CANDIDATE")
                .map(candidate -> {
                    Submission submission = new Submission();
                    submission.setExamId(exam.getExamId());
                    submission.setCandidateId(candidate.getUsername());
                    Map<String, MCQSubmission> eachSubmissionMCQMap = new HashMap<>();
                    exam.getQuestions().
                            forEach(question -> eachSubmissionMCQMap.put(question.getQuestionId(), new MCQSubmission("", 0, new ArrayList<VisitDetails>() {
                            })));
                    submission.setAllSubmissions(eachSubmissionMCQMap);
                    return submission;
                })).subscribe();

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
                                new QuestionDTO(questionData.getQuestionId(), questionData.getOptions(), questionData.getStatement())).collect(Collectors.toList())));
    }

    public void endExamByExamId(String examId) {
        MCQEvaluator mcqEvaluator = new MCQEvaluator();
        examRepository.findByExamId(examId).subscribe(exam -> mcqEvaluator.setQuestionList(exam.getQuestions()));
        submissionRepository.findAllByExamId(examId)
                .subscribe(submission ->
                                examRepository.addSubmissionsByExamId(mcqEvaluator.evaluate(submission.getAllSubmissions()), examId, submission),
                        error -> System.err.println("Error: " + error),
                        () -> submissionRepository.deleteAllByExamId(examId).subscribe()
                );
    }
}
