package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MCQSubmission {

    private String finalAnswer;
    private int totalVisits;
    private List<VisitDetails> visits;

    public MCQSubmission() {
        this.finalAnswer = "";
        this.totalVisits = 0;
        this.visits = new ArrayList<>();
    }

    public void addVisit(VisitDetails visitDetails) {
        System.out.println("In addVisit");
        visits.add(visitDetails);
    }
}
