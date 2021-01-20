package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSubmissionResponse {
    private String source_code;
    private Integer language_id;
    private String stdin;
    private String expected_output;
    private String stdout;
    private Integer status_id;
    private Double  time;
    private Long memory;
    private String stderr;
    private String token;
    private String compile_output;
    private Integer exit_code;
    private String message;

    private Status status;

    private SubmissionLanguage language;

    private Double score;
}
