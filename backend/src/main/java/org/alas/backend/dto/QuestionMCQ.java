package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionMCQ {

    private String question_id;
    private String final_answer;
    private int total_no_of_visits = 0;
    private List<VisitDetailsMCQ> all_visits;
}
