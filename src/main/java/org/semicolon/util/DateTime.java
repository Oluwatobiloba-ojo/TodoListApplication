package org.semicolon.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DateTime {
    private Date date;
    private int hour;
    private int minute;
}
