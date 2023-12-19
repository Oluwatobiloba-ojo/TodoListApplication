package org.semicolon.dtos.request;

import lombok.Data;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;

@Data
public class CreateRequest {
    private String username;
    private String message;
    private Date dateCreated;
    private DateTime dueDateTime;
}
