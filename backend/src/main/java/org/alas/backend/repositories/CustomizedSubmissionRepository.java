package org.alas.backend.repositories;

import org.alas.backend.dto.VisitDTO;
import reactor.core.publisher.Mono;


public interface CustomizedSubmissionRepository {
    Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, VisitDTO visit);
}
