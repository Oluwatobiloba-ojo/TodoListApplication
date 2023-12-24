package org.semicolon.data.model;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document
public class Task {
    @Id
    private String id;
    private String message;
    private LocalDate localDate;
    private LocalDateTime dueDateTime;
    private String todoId;
}


