package org.alas.backend.documents;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ping")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ping {
    String timestamp;
}
