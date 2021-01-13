package org.alas.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {

    private String examId;
    private String batchId;
    private String examName;
    private String subject;
    private int noOfQuestions;
    private String examDate;
    private String startTime;
    private String endTime;
    private String author;
    private String status;

}
