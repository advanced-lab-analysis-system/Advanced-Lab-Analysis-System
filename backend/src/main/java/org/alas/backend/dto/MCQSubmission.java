package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQSubmission {

    private String finalAnswer;
    private int totalVisits = 0;
    private List<VisitDetails> visits = new ArrayList<>();

    public void addVisit(VisitDetails visitDetails) {
        System.out.println("In addVisit");
        visits.add(visitDetails);
    }
}
