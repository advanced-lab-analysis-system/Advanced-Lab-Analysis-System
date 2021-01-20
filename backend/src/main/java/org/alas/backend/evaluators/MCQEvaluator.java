package org.alas.backend.evaluators;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.dto.Question;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MCQEvaluator {

    List<MCQQuestion> questionList;

    public List<String> evaluate(Map<String, Object> allSubmissions){
        List<String> questionsAnsweredCorrectly = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        questionList.forEach(question -> {
            MCQSubmission mcqSubmission = new MCQSubmission();
            try {
                String submissionString = objectMapper.writeValueAsString(allSubmissions.get(question.getQuestionId()));
                mcqSubmission = objectMapper.readValue(submissionString,MCQSubmission.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(mcqSubmission.getFinalAnswer().equals(question.getAnswer()))
                questionsAnsweredCorrectly.add(question.getQuestionId());
        });
        return questionsAnsweredCorrectly;
    }
}
