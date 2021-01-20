package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeRequestDTO {
    private Integer language_id;
    private String source_code;
    private String stdin;

    @Override
    public String toString() {
        return "{" +
                "language_id=" + language_id +
                ",source_code=\"" + source_code + "\"" +
                ", stdin=\"" + stdin + "\"" +
                '}';
    }
}
