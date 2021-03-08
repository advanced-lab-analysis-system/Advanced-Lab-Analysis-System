package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeSubmission {

    private Integer totalNoOfSubmissions;
    private Integer bestSubmission;
    private Double bestSubmissionScore;
    private List<GetSubmissionResponse> allCodeSubmissions;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
