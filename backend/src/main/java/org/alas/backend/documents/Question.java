package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String questionId;
    private List<String> options;
    private String answer;

    public String getQuestionId(){
        return questionId;
    }
}
