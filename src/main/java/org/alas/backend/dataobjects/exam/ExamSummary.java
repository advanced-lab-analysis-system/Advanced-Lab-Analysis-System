package org.alas.backend.dataobjects.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.documents.Exam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamSummary {

    private String Id;
    private String examName;
    private int noOfQuestions;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examStartTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examEndTime;
    private String authorId;

    public ExamSummary(Exam exam) {
        this.Id = exam.getId();
        this.examName = exam.getExamName();
        this.noOfQuestions = exam.getNoOfQuestions();
        this.examStartTime = exam.getExamStartTime();
        this.examEndTime = exam.getExamEndTime();
        this.authorId = exam.getAuthorId();
    }
}
