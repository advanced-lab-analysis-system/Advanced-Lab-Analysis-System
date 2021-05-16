package org.alas.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alas.backend.dataobjects.exam.submission.coding.CodingVisit;
import org.alas.backend.dataobjects.exam.submission.coding.judge0.CreationResponse;
import org.alas.backend.dataobjects.exam.submission.coding.judge0.GetSubmissionResponse;
import org.alas.backend.dataobjects.exam.submission.coding.judge0.JudgeRequest;
import org.alas.backend.dataobjects.exam.submission.mcq.MCQVisit;
import org.alas.backend.repositories.SubmissionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final JudgeService judgeService;

    public SubmissionService(SubmissionRepository submissionRepository, JudgeService judgeService) {
        this.submissionRepository = submissionRepository;
        this.judgeService = judgeService;
    }

    public ResponseEntity<?> newVisit(String questionType, String visit, String candidateId, String examId) throws JsonProcessingException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        switch (questionType) {
            case "mcq":
                MCQVisit mcqVisit = objectMapper.readValue(visit, MCQVisit.class);
                submissionRepository.updateByExamIdAndCandidateIdAndQuestionId(examId, candidateId, mcqVisit);
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            case "coding":
                CodingVisit codingVisit = objectMapper.readValue(visit, CodingVisit.class);
                JudgeRequest judgeRequest = new JudgeRequest(codingVisit.getLanguage_id(), codingVisit.getCode(), codingVisit.getStdin(), codingVisit.getExpectedOutput());
                CreationResponse creationResponse = judgeService.createSubmission(judgeRequest);
                TimeUnit.SECONDS.sleep(5);
                GetSubmissionResponse getSubmissionResponse = judgeService.getSubmission(creationResponse.getToken());

                if (codingVisit.getSubmit()) {
                    submitExam(examId, candidateId);
                }
                return new ResponseEntity<>(getSubmissionResponse, HttpStatus.CREATED);
            default:
                throw new IllegalStateException("Unexpected value: " + questionType);
        }
    }

    public void submitExam(String examId, String candidateId) {
        submissionRepository.updateByExamIdAndCandidateId(examId, candidateId, "submitted");
    }
}
