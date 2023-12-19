package org.semicolon.dtos.request;

import lombok.Data;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;
@Data
public class UpdateDueDateRequest {
    private String username;
    private String description;
    private Date dateCreated;
    private DateTime dueDate;
}
