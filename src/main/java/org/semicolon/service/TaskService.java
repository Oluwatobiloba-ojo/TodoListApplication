package org.semicolon.service;

import org.semicolon.data.model.Task;
import org.semicolon.dtos.request.DataRequest;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TaskService {
    void create(DataRequest dataRequest, String id);
    List<Task> findTaskBelongingTo(String id);
    List<Task> findParticularTaskInADay(String todoId, Date dateCreated);
    Task findTaskFor(String message, String todoId, Date dateCreated);
    void update(String id, Date dateCreated, String oldMessage, String newMessage);
    void update(String todoId, Date dateCreated, String oldMessage, DateTime newDueDate);
    void deleteAllTaskFor(String todoId);
    void delete(String todoId, String message, Date date);
}
