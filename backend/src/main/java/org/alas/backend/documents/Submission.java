package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dto.QuestionMCQ;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "exam_id_and_candidate_id", def = "{'exam_id':1,'candidate_id':1}")
public class Submission {

    private String exam_id;

    private String candidate_id;

    private List<QuestionMCQ> questionMCQList;
}
