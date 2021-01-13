package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dto.MCQSubmission;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "Exam")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exam {

    @Indexed(unique = true)
    private String examId;
    private String batchId;
    private String examName;
    private String subject;
    private int noOfQuestions;
    private String examDate;
    private String examStartTime;
    private String examEndTime;
    private String author;
    private String status;
    private List<Question> questions;
    private Map<String, MCQSubmission> submissions;
}
