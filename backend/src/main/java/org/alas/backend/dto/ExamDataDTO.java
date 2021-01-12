package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDataDTO {

    private String exam_id;
    private String batch_id;
    private String exam_name;
    private String exam_type;
    private String subject;
    private int no_of_questions;
    private String exam_date;
    private String exam_start_time;
    private String exam_end_time;
    private String author;
    private String class_and_section;
    private boolean exam_completed;
    private List<QuestionDTO> questions;
}