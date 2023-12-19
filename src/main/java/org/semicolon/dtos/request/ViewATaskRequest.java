package org.semicolon.dtos.request;

import lombok.Data;
import org.semicolon.util.Date;

@Data
public class ViewATaskRequest {
    private String username;
    private Date dateCreated;
    private String message;
}
