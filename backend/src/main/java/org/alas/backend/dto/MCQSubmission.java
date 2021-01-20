package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

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
