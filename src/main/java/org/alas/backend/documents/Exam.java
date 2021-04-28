package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dataobjects.exam.question.Question;
import org.springframework.data.annotation.Id;
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

    @Id
    private String Id;
    private String examName;
    private int noOfQuestions;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examStartTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examEndTime;
    private String authorId;
    private List<Question> questionList;

    /*
     * 1st Key -> candidateId
     * 2nd Key -> questionId
     * Object -> submission for questionId
     * */
    private Map<String, Map<String, Object>> submissions;
}
