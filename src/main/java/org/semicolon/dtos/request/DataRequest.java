package org.semicolon.dtos.request;
import lombok.Data;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DataRequest {
    private String message;
    private LocalDate date = LocalDate.now();
    private LocalDateTime dueDateTime;
    private String title;
    public DataRequest(String message, DateTime dateTime) {
        this.message = message;
        date = LocalDate.now();
        this.dueDateTime = LocalDateTime.of(dateTime.getDate().getYear(),
                dateTime.getDate().getMonth(), dateTime.getDate().getDay(),
                dateTime.getHour(), dateTime.getMinute());
    }
    public DataRequest(){}
    public DataRequest(String message, Date date, DateTime dateTime){
        this.message = message;
        this.date = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        this.dueDateTime = LocalDateTime.of(dateTime.getDate().getYear(), dateTime.getDate().getMonth(),
                dateTime.getDate().getDay(), dateTime.getHour(), dateTime.getMinute());
    }

}
