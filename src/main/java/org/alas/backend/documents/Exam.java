package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dataobjects.Question;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "Exams")
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examStartTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examEndTime;
    private String author;
    private String status;
    private List<Question> questionList;

    //Here String(Key) is candidateId and Object is his submission for this exam
    private Map<String, Map<String, Object>> submissions;
}


