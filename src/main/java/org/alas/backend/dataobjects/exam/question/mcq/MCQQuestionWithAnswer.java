package org.alas.backend.dataobjects.exam.question.mcq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQQuestionWithAnswer extends MCQQuestion {
    private String answer;
}
