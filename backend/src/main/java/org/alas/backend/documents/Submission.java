package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dto.MCQSubmission;
import org.alas.backend.dto.VisitDTO;
import org.alas.backend.dto.VisitDetails;
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
    private Map<String, MCQSubmission> allSubmissions;

    public Submission updateVisit(VisitDTO visit) {
        allSubmissions.get(visit.getQuestionId())
                .addVisit(new VisitDetails(visit.getVisitStartTime(), visit.getVisitEndTime(), visit.getSelectedAnswer()));
        return this;
    }
}
