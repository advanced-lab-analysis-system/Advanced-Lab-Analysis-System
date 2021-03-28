package org.alas.backend.dataobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQSubmission {

    private String finalAnswer;
    private Integer totalVisits;
    private List<VisitDetails> visits;

//    @Override
//    public String toString() {
//        return new JSONObject(this).toString();
//    }

}
