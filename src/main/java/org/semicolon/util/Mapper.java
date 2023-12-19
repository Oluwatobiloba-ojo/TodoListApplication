package org.semicolon.util;
import org.semicolon.data.model.Task;
import org.semicolon.data.model.TodoList;
import org.semicolon.dtos.request.DataRequest;
import org.semicolon.dtos.request.RegisterRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Mapper {

    public static TodoList mapToTodolist(RegisterRequest registerRequest){
        TodoList todoList = new TodoList();
        String securePassword = PasswordEncode.generateHashPassword(registerRequest.getPassword(), PasswordEncode.getSaltValue());
        todoList.setUsername(registerRequest.getUsername());
        todoList.setPassword(securePassword);
        return todoList;
    }
    public static Task mapDataToTask(DataRequest dataRequest, String id){
        Task task = new Task();
        task.setMessage(dataRequest.getMessage());
        task.setLocalDate(dataRequest.getDate());
        task.setDueDateTime(dataRequest.getDueDateTime());
        task.setTodoId(id);
        return task;
    }
    public static LocalDate mapDateToLocalDate(Date date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
    }
    public static LocalDateTime mapDueDateToLocalDateTime(DateTime newDueDate) {
        Date date = newDueDate.getDate();
        return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDay(), newDueDate.getHour(), newDueDate.getMinute());
    }

    public static Date mapLocalDateToDate(LocalDate date) {
        Date date1 = new Date();
        date1.setYear(date.getYear());
        date1.setMonth(date.getMonthValue());
        date1.setDay(date.getDayOfMonth());
        return date1;
    }
}
