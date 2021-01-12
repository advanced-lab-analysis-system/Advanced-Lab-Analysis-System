package org.alas.backend.documents;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alas.backend.dto.QuestionMCQ;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "TestExam")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestExam {

    @Indexed(unique = true)
    private String testExam_id;
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
    private List<Question> questions;
    private long time_limit;
    private Map<String, QuestionMCQ> all_submissions;

}
