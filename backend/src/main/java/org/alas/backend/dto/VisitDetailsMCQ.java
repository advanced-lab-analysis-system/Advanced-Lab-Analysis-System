package org.alas.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDetailsMCQ{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visit_start_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visit_end_time;
    private long time_spent;
    private String visits_answer;
}
