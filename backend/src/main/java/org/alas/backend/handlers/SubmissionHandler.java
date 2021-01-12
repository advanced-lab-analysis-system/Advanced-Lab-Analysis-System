package org.alas.backend.handlers;

import org.alas.backend.documents.Submission;
import org.alas.backend.dto.QuestionMCQ;
import org.alas.backend.dto.VisitDetailsMCQ;
import org.alas.backend.repositories.SubmissionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionHandler {

    final
    SubmissionRepository submissionRepository;

    public SubmissionHandler(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public Mono<?> addSubmissionV2(String exam_id,String candidate_id,QuestionMCQ questionMCQ){
            return submissionRepository.findAndUpdateSubmissionByExam_idAndCandidate_id(exam_id, candidate_id, questionMCQ);
    }

    /*public Mono<?> addSubmission(String exam_id,String candidate_id,QuestionMCQ questionMCQ){
        return submissionRepository.findSubmissionByExam_idAndCandidate_id(exam_id, candidate_id)
                .flatMap(submission -> {
                     List<QuestionMCQ> questionMCQList =  submission.getQuestionMCQList();
                     List<QuestionMCQ> questionMCQList1 = questionMCQList.stream().filter(questionMCQ1 -> questionMCQ1.getQuestion_id().equals(questionMCQ.getQuestion_id())).collect(Collectors.toList());
                     if(!questionMCQList1.isEmpty()){
                         QuestionMCQ questionMCQ1 = questionMCQList1.get(0);
                         List<VisitDetailsMCQ> visitDetailsMCQList = questionMCQ1.getAll_visits();
                         visitDetailsMCQList.add(questionMCQ.getAll_visits().get(0));
                         questionMCQ1.setAll_visits(visitDetailsMCQList);
                         questionMCQ1.setFinal_answer(questionMCQ.getFinal_answer());
                         questionMCQ1.setTotal_no_of_visits(questionMCQ1.getTotal_no_of_visits()+1);
                     }
                     else{
                         questionMCQList.add(questionMCQ);
                     }
                     submission.setQuestionMCQList(questionMCQList);
                     return submissionRepository.save(submission);
                     });
    }*/
}
