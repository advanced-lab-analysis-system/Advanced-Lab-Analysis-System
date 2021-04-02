package org.alas.backend.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "Module")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Module {
    @Indexed(unique = true)
    private String moduleId;
    private String moduleName;
    private String moduleDescription;
    private String originalAuthor;
    private List<String> batchList;
//    TODO: change it so each author will have the rights associated with him/her
    private Map<String, String> authorList;
    private List<String> examList;
}