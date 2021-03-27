package org.alas.backend.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alas.backend.documents.Exam;
import org.alas.backend.documents.Submission;
import org.alas.backend.dto.*;
import org.alas.backend.evaluators.MCQEvaluator;
import org.alas.backend.repositories.ExamRepository;
import org.alas.backend.repositories.SubmissionRepository;
import org.alas.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
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
        Map<String, Map<String, Object>> submissions = new HashMap<>();
        userRepository.findAllByRolesContaining("ROLE_CANDIDATE")
                .subscribe(user -> submissions.put(user.getUsername(), new HashMap<>()),
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
                    submission.setSessionStatus("upcoming");
                    Map<String, Object> eachSubmissionMap = new HashMap<>();
                    exam.getQuestionList().forEach(question -> {
                        if (question.getQuestionType().equals("mcq"))
                            eachSubmissionMap.put(question.getQuestionId(), new MCQSubmission("", 0, new ArrayList<>()));
                        else
                            eachSubmissionMap.put(question.getQuestionId(),
                                    new CodeSubmission(0, -1, 0.0
                                            , new ArrayList<>()));
                    });
                    submission.setAllSubmissions(eachSubmissionMap);
                    return submission;
                })).subscribe();
    }


    public Flux<ExamDTO> getAllExams(String candidateId) {
        Map<String, String> sessionStatusMap = new HashMap<>();
        submissionRepository.findAllByCandidateId(candidateId).toStream().forEach(submission -> {
            sessionStatusMap.put(submission.getExamId(), submission.getSessionStatus());
        });
        return examRepository.findAll().map(exam ->
                new ExamDTO(exam.getExamId(),
                        exam.getBatchId(),
                        exam.getExamName(),
                        exam.getSubject(),
                        exam.getNoOfQuestions(),
                        exam.getExamStartTime(),
                        exam.getExamEndTime(),
                        exam.getAuthor(),
                        exam.getStatus(),
                        sessionStatusMap.get(exam.getExamId())));
    }

    public Flux<ExamDTO> getAllAuthorExams(String authorId) {
        return examRepository.findAll().map(exam ->
                new ExamDTO(exam.getExamId(),
                        exam.getBatchId(),
                        exam.getExamName(),
                        exam.getSubject(),
                        exam.getNoOfQuestions(),
                        exam.getExamStartTime(),
                        exam.getExamEndTime(),
                        exam.getAuthor(),
                        exam.getStatus(),
                        ""));
    }

    public Mono<Exam> getExamWithAnswersById(String exam_id) {
        return examRepository.findByExamId(exam_id);
    }

    public Mono<?> getExamWithoutAnswersById(String examId, String candidateId) {
        ObjectMapper objectMapper = new ObjectMapper();
        submissionRepository.updateByExamIdAndCandidateId(examId, candidateId, "running").subscribe();
        return examRepository.findByExamId(examId)
                .map(exam ->
                        new ExamDataDTO(exam.getExamId(),
                                exam.getBatchId(),
                                exam.getExamName(),
                                exam.getSubject(),
                                exam.getNoOfQuestions(),
                                exam.getExamStartTime(),
                                exam.getExamEndTime(),
                                exam.getAuthor(),
                                exam.getStatus(),
                                exam.getQuestionList().stream().map(question -> {
                                    Question questionFiltered = new Question();
                                    if (question.getQuestionType().equals("mcq")) {
                                        questionFiltered.setQuestionId(question.getQuestionId());
                                        questionFiltered.setQuestionType(question.getQuestionType());
                                        try {
                                            String questionString = objectMapper.writeValueAsString(question.getQuestion());
                                            QuestionMCQ questionMCQ = objectMapper.readValue(questionString, QuestionMCQ.class);
                                            questionFiltered.setQuestion(new QuestionMCQDTO(questionMCQ.getStatement(), questionMCQ.getOptions()));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        questionFiltered = question;
                                    }
                                    return questionFiltered;
                                }).collect(Collectors.toList())));
    }

    public void endExamByExamId(String examId) {
        MCQEvaluator mcqEvaluator = new MCQEvaluator();
        ObjectMapper objectMapper = new ObjectMapper();
        examRepository.findByExamId(examId)
                .subscribe(exam -> mcqEvaluator.setQuestionList(exam.getQuestionList().stream()
                                .filter(question -> question.getQuestionType().equals("mcq"))
                                .map(question -> {
                                    QuestionMCQ questionMCQ = new QuestionMCQ();
                                    try {
                                        String questionString = objectMapper.writeValueAsString(question.getQuestion());
                                        //System.out.println("In endExamByExamId1"+questionString);
                                        questionMCQ = objectMapper.readValue(questionString, QuestionMCQ.class);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    return new MCQQuestion(question.getQuestionId(), questionMCQ.getAnswer());
                                })
                                .collect(Collectors.toList()))
                        , null
                        , () ->
                        {
                            submissionRepository.findAllByExamId(examId)
                                    .subscribe(submission ->
                                                    examRepository.addSubmissionsByExamId(mcqEvaluator.evaluate(submission.getAllSubmissions()), examId, submission),
                                            error -> System.err.println("Error: " + error),
                                            () -> submissionRepository.deleteAllByExamId(examId).subscribe()
                                    );
                            examRepository.updateExamStatus(examId);
                        }

                );
    }
}
