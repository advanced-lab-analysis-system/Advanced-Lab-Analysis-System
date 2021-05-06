package org.alas.backend.dataobjects.exam.question.coding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    private String input;
    private String output;
}
