package org.alas.backend.evaluators;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.dto.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MCQEvaluator {

    List<Question> questionList;

    public List<String> evaluate(Map<String, MCQSubmission> mcqSubmissionMap){
        List<String> questionsAnsweredCorrectly = new ArrayList<>();
        questionList.forEach(question -> {
            if(mcqSubmissionMap.get(question.getQuestionId()).getFinalAnswer().equals(question.getAnswer()))
                questionsAnsweredCorrectly.add(question.getQuestionId());
        });
        return questionsAnsweredCorrectly;
    }
}
