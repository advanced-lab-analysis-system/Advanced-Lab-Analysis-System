package org.alas.backend.handlers;

import org.alas.backend.documents.Submission;
import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.dto.VisitDTO;
import org.alas.backend.dto.VisitDetails;
import org.alas.backend.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static scala.None.map;

@Service
public class SubmissionHandler {

    @Autowired
    private SubmissionRepository submissionRepository;

    public Mono<?> addVisit(String examId, String candidateId, VisitDTO visit) {
//        return submissionRepository.findByExamIdAndCandidateId(examId, candidateId)
//                .map(submission -> submission.updateVisit(visit))
//                .flatMap(submissionRepository::save);
        return submissionRepository.updateByExamIdAndCandidateIdAndQuestionId(examId, candidateId, visit);
    }
}
