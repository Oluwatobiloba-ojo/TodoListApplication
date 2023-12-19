package org.semicolon.service;

import org.semicolon.data.model.Task;
import org.semicolon.dtos.request.DataRequest;
import org.semicolon.dtos.request.LoginRequest;
import org.semicolon.dtos.request.RegisterRequest;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;

import java.util.List;

public interface TodoListService  {
    void register(RegisterRequest registerRequest);
    void login(LoginRequest loginRequest);
    void create(String username, DataRequest dataRequest);
    List<Task> findTodoCreatedBy(String username, Date dateCreated);
    List<Task> findTaskBelongingTo(String username);
    Task findTaskFor(String username, String message, Date dateCreated);
    void update(String username, Date dateCreated, String oldMessage, String newMessage);
    void update(String username, Date dateCreated, String oldMessage, DateTime newDueDate);
    void deleteAll(String username);
    void delete(String username, Date date, String message);
    void deleteAccount(String username);
}
