package org.alas.backend.dataobjects.exam.question.coding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodingQuestionWithTestCases extends CodingQuestion {
    private List<TestCase> testCases;
}
