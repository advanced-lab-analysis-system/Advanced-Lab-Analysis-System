package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "PerformanceAnalysis")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PerformanceAnalysis {

    private String examId;
    private Map<String, String> confidenceLevelChanges;
    private Map<String, String> accuracy;
    private Map<String, String> confidenceLevelTimeSpent;
}
