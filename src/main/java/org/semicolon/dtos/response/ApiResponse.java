package org.semicolon.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Object data;
    private boolean isSuccessful;
}
