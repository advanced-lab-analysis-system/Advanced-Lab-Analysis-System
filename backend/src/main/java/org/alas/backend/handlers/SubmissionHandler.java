package org.alas.backend.handlers;

import org.alas.backend.dto.VisitDTO;
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
}
