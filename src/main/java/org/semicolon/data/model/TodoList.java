package org.semicolon.data.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Data
public class TodoList {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean isLogOut = true;
}
