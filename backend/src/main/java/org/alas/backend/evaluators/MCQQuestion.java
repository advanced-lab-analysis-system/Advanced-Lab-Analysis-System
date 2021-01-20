package org.alas.backend.evaluators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQQuestion {

    private String questionId;
    private String answer;
}
