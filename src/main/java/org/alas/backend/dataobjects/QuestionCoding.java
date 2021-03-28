package org.alas.backend.dataobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCoding {

    private List<LanguageAccepted> languagesAccepted;
    private Long timeLimit;
    private Long memoryLimit;
    private String statement;
    private List<TestCase> testCases;
}
