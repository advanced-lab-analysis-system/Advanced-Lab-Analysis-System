package org.alas.backend.documents;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {

    private String exam_id;
    private String batch_id;
    private String exam_name;
    private String exam_type;
    private String subject;
    private String exam_date;
    private String exam_start_time;
    private String exam_end_time;
    private String conductedBy;
    private String class_and_section;
    private String exam_status;

}
