package org.alas.backend.dataobjects.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.documents.Exam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamCandidate {

    private String id;
    private String examName;
    private int noOfQuestions;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examStartTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime examEndTime;
    private String authorId;
    private List<Object> questionList;
    private String status;
    private long timeRemaining;

    public ExamCandidate(Exam exam) {
        this.id = exam.getId();
        this.examName = exam.getExamName();
        this.noOfQuestions = exam.getNoOfQuestions();
        this.examStartTime = exam.getExamStartTime();
        this.examEndTime = exam.getExamEndTime();
        this.authorId = exam.getAuthorId();
        this.questionList = exam.getQuestionList();

        LocalDateTime currentDateTime = LocalDateTime.now();

        if (currentDateTime.isAfter(this.examEndTime)) this.status = "ended";
        else if (currentDateTime.isBefore(this.examStartTime)) this.status = "upcoming";
        else this.status = "running";

        if (this.status.equals("running"))
            this.timeRemaining = currentDateTime.until(this.examEndTime, ChronoUnit.SECONDS);
        else if (this.status.equals("upcoming"))
            this.timeRemaining = currentDateTime.until(this.examStartTime, ChronoUnit.SECONDS);
        else this.timeRemaining = -1;
    }
}
