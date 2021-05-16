package org.alas.backend.dataobjects.exam.submission.coding.judge0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeRequest {

    private Integer language_id;
    private String source_code;
    private String stdin;
    private String expected_output;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}