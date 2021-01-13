package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQSubmission {

    private String finalAnswer;
    private int totalVisits;
    private List<VisitDetails> visits;

}
