package org.semicolon.service;

import org.semicolon.data.model.Task;
import org.semicolon.data.repository.TaskRepository;
import org.semicolon.dtos.request.DataRequest;
import org.semicolon.exception.TaskExistException;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.semicolon.util.Mapper.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Override
    public void create(DataRequest dataRequest, String id) {
        Task task = mapDataToTask(dataRequest, id);
        taskRepository.save(task);
    }
    @Override
    public List<Task> findTaskBelongingTo(String id) {
        List<Task> tasks = new ArrayList<>();
        for (Task task : taskRepository.findAll()){
            if (task.getTodoId().equals(id)) tasks.add(task);
        }
        return tasks;
    }
    @Override
    public List<Task> findParticularTaskInADay(String id, Date dateCreated) {
        List<Task> tasks = new ArrayList<>();
        LocalDate localDate = mapDateToLocalDate(dateCreated);
       for (Task task : findTaskBelongingTo(id)){
           LocalDate dateCreate = task.getLocalDate();
           if (dateCreate.getYear() == localDate.getYear() && dateCreate.getMonthValue() == localDate.getMonthValue()
           && dateCreate.getDayOfMonth() == localDate.getDayOfMonth()) tasks.add(task);
       }
       return tasks;
    }
    @Override
    public Task findTaskFor(String message, String todoId, Date dateCreated) {
        List<Task> taskCreatedInADay = findParticularTaskInADay(todoId, dateCreated);
        for (Task task : taskCreatedInADay){
            if (task.getMessage().equals(message)) return task;
        }
        return null;
    }
    @Override
    public void update(String todoId, Date dateCreated, String oldMessage, String newMessage) {
       Task task = findTaskFor(oldMessage, todoId, dateCreated);
       if (task == null) throw new TaskExistException("Task does not exist");
       task.setMessage(newMessage);
       taskRepository.save(task);
    }
    @Override
    public void update(String todoId, Date dateCreated, String oldMessage, DateTime newDueDate) {
        Task task = findTaskFor(oldMessage, todoId, dateCreated);
        if (task == null) throw new TaskExistException("Task does not exist");
        LocalDateTime newDueDateCreated = mapDueDateToLocalDateTime(newDueDate);
        task.setDueDateTime(newDueDateCreated);
        taskRepository.save(task);
    }
    @Override
    public void deleteAllTaskFor(String todoId) {
        List<Task> tasks = findTaskBelongingTo(todoId);
        if (tasks.isEmpty()) throw new TaskExistException("No task found");
        taskRepository.deleteAll(tasks);
    }

    @Override
    public void delete(String todoId, String message, Date date) {
       Task task = findTaskFor(message, todoId, date);
       if (task == null) throw new TaskExistException("No task found");
       taskRepository.delete(task);
    }
}
