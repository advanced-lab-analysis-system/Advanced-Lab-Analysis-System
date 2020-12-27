package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Exam")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exam {

    @Indexed(unique = true)
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
    private List<Question> questions;

}
