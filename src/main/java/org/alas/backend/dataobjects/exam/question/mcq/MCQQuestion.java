package org.alas.backend.dataobjects.exam.question.mcq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.alas.backend.dataobjects.exam.question.Question;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MCQQuestion extends Question {
    private List<String> options;

    public MCQQuestion(String questionId, String questionType, String statement, List<String> options) {
        super(questionId, questionType, statement);
        this.options = options;
    }
}
