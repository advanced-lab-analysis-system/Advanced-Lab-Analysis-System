package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Batch")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Batch {
    @Indexed(unique = true)
    private String batchId;
    private String batchName;
    private String batchDescription;
    private List<String> candidateList;
    private List<String> moduleList;
    private String status;
}