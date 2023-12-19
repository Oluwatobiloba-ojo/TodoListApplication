package org.semicolon.dtos.request;

import lombok.Data;
import org.semicolon.util.Date;

@Data
public class UpdateRequest {
   private String username;
   private Date dateCreated;
   private String oldMessage;
   private String newMessage;
}
