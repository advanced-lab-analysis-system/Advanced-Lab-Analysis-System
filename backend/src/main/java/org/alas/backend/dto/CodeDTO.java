package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeDTO {

    private String questionId;
    private String language;
    private String code;
    private String customInput;
    private Boolean submit;
}
