package org.alas.backend.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alas.backend.dataobjects.CodeSubmission;
import org.alas.backend.dataobjects.GetSubmissionResponse;
import org.alas.backend.dataobjects.dto.VisitDTO;
import org.alas.backend.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SubmissionHandler {

    @Autowired
    private SubmissionRepository submissionRepository;

    public Mono<?> addVisit(String examId, String candidateId, VisitDTO visit) {
        return submissionRepository.updateByExamIdAndCandidateIdAndQuestionId(examId, candidateId, visit);
    }

    public Mono<?> addCodeSubmission(String examId, String candidateId, String questionId, GetSubmissionResponse getSubmissionResponse) {
        System.out.println("In addCodeSubmission()");
        ObjectMapper objectMapper = new ObjectMapper();
        if(getSubmissionResponse.getStatus().getDescription().equals("Accepted"))
            getSubmissionResponse.setScore(10.0);
        return submissionRepository.findByExamIdAndCandidateId(examId, candidateId)
                .flatMap(submission -> {
                    try {
                        String codeSubmissionString = objectMapper.writeValueAsString(submission.getAllSubmissions().get(questionId));
                        CodeSubmission codeSubmission1 = objectMapper.readValue(codeSubmissionString,CodeSubmission.class);
                        System.out.println("codeSubmissionString is : "+codeSubmissionString);
                        System.out.println(codeSubmission1.toString());
                        return submissionRepository.updateByExamIdAndCandidateIdAndQuestionId(examId, candidateId, questionId, codeSubmission1, getSubmissionResponse);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Out of try block");
                    return submissionRepository
                            .updateByExamIdAndCandidateIdAndQuestionId(examId, candidateId, questionId,
                                    new CodeSubmission(0, -1, 0.0, null)
                                    , getSubmissionResponse);
                });
    }

    public void submitExam(String examId, String candidateId) {
        submissionRepository.updateByExamIdAndCandidateId(examId,candidateId,"submitted").subscribe();
    }
}
