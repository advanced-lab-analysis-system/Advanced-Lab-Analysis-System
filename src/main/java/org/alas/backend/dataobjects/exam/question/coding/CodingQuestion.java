package org.alas.backend.dataobjects.exam.question.coding;


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
public class CodingQuestion extends Question {

    private List<Language> languagesAccepted;
    private Long timeLimit;
    private Long memoryLimit;
}
