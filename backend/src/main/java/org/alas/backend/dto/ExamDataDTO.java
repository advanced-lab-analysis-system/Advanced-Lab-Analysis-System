package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDataDTO {

    private String examId;
    private String batchId;
    private String examName;
    private String subject;
    private int noOfQuestions;
    private String examDate;
    private String startTime;
    private String examEndTime;
    private String author;
    private String status;
    private List<QuestionDTO> questions;
}
