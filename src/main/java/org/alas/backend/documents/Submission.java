package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "Submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "examIdAndCandidateId", def = "{'examId':1,'candidateId':1}")
public class Submission {

    private String examId;
    private String candidateId;
    private String sessionStatus;
    private Map<String, Object> allSubmissions;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
