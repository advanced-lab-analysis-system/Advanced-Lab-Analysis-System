package org.alas.backend.dataobjects.exam.submission.coding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodingVisit {
    private String questionId;
    private Integer language_id;
    private String code;
    private String stdin;
    private String expectedOutput;
    private Boolean submit;
}
