package org.alas.backend.dataobjects.exam.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private String questionId;
    private String questionType;
    private String statement;
}
