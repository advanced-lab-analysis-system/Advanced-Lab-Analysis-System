package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Batch")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Batch {
    @Id
    private String id;
    private String batchName;
    private String batchDescription;
    private List<String> candidateList;
    private List<String> moduleList;
    private String status;

    public void addNewModule(String moduleId){this.moduleList.add(moduleId);}

    public void deleteModule(String moduleId){this.moduleList.remove(moduleId);}
}