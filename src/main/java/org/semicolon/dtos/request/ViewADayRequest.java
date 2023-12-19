package org.semicolon.dtos.request;

import lombok.Data;
import org.semicolon.util.Date;
@Data
public class ViewADayRequest {
    private String username;
    private Date date;
}
