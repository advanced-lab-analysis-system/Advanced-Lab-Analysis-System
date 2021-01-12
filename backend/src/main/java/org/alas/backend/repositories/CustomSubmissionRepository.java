package org.alas.backend.repositories;

import org.alas.backend.dto.QuestionMCQ;
import reactor.core.publisher.Mono;


public interface CustomSubmissionRepository {
    Mono<?> findAndUpdateSubmissionByExam_idAndCandidate_id(String exam_id, String candidate_id, QuestionMCQ questionMCQ);
}
