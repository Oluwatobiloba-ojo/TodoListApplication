package org.semicolon.service;

import org.semicolon.data.model.Task;
import org.semicolon.data.model.TodoList;
import org.semicolon.data.repository.TodoListRepository;
import org.semicolon.dtos.request.DataRequest;
import org.semicolon.dtos.request.LoginRequest;
import org.semicolon.dtos.request.RegisterRequest;
import org.semicolon.exception.ClientExistException;
import org.semicolon.exception.InvalidDetailsException;
import org.semicolon.exception.InvalidLogInDetails;
import org.semicolon.exception.TaskExistException;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;
import org.semicolon.util.Mapper;
import org.semicolon.util.PasswordEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoListServiceImpl implements TodoListService {
    @Autowired
    TodoListRepository todoListRepository;
    @Autowired
    TaskService taskService;
    @Override
    public void register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getUsername())) throw new ClientExistException("Client already exist");
        TodoList todoList =  Mapper.mapToTodolist(registerRequest);
        todoListRepository.save(todoList);
    }
    @Override
    public void login(LoginRequest loginRequest) {
        if (!userExist(loginRequest.getUsername())) throw new InvalidDetailsException("invalid details");
        TodoList todoList = todoListRepository.findByUsername(loginRequest.getUsername());
        boolean isEqual = PasswordEncode.verifyPassword(loginRequest.getPassword(), todoList.getPassword());
        if (!isEqual) throw new InvalidDetailsException("Invalid details");
        todoList.setLogOut(false);
        todoListRepository.save(todoList);
    }
    @Override
    public void create(String username, DataRequest dataRequest) {
        if (!userExist(username)) throw new ClientExistException("Client does not exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        accountLocked(username, todoList);
        Date dateCreated = Mapper.mapLocalDateToDate(dataRequest.getDate());
        if (taskCreated(dataRequest.getMessage(), todoList, dateCreated))throw new TaskExistException("Task has been created already");
        taskService.create(dataRequest, todoList.getId());
    }
    private boolean taskCreated(String message, TodoList todoList, Date dateCreated) {
        if (taskService.findTaskFor(message, todoList.getId(), dateCreated) != null){
            return true;
        }
        return false;
    }
    private static void accountLocked(String username, TodoList todoList) {
        if (todoList.isLogOut()) throw new InvalidLogInDetails(username + " account is locked");
    }
    @Override
    public List<Task> findTodoCreatedBy(String username, Date dateCreated) {
        if(!userExist(username))throw new ClientExistException("Client does not exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        accountLocked(username, todoList);
        List<Task> tasks = taskService.findParticularTaskInADay(todoList.getId(), dateCreated);
        taskEmpty(tasks);
        return tasks;
    }
    @Override
    public List<Task> findTaskBelongingTo(String username) {
        if (!userExist(username)) throw new ClientExistException("Client does not exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        accountLocked(username, todoList);
        List<Task> tasks = taskService.findTaskBelongingTo(todoList.getId());
        taskEmpty(tasks);
        return tasks;
    }
    private static void taskEmpty(List<Task> tasks) {
        if (tasks.isEmpty()) throw new TaskExistException("No task created");
    }
    @Override
    public Task findTaskFor(String username, String message, Date dateCreated) {
        if (!userExist(username)) throw new ClientExistException("Client does not exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        accountLocked(username, todoList);
        Task task = taskService.findTaskFor(message, todoList.getId(), dateCreated);
        if (task == null) throw new InvalidDetailsException("Invalid details to find");
        return task;
    }
    @Override
    public void update(String username, Date dateCreated, String oldMessage, String newMessage) {
        if (!userExist(username)) throw new ClientExistException("Invalid user");
        TodoList todoList = todoListRepository.findByUsername(username);
        if (taskCreated(newMessage, todoList, dateCreated))throw new TaskExistException("Task has been created already");
        taskService.update(todoList.getId(), dateCreated, oldMessage, newMessage);
    }
    @Override
    public void update(String username, Date dateCreated, String oldMessage, DateTime newDueDate) {
        if (!userExist(username)) throw new ClientExistException("Invalid user");
        TodoList todoList = todoListRepository.findByUsername(username);
        taskService.update(todoList.getId(), dateCreated, oldMessage, newDueDate);
    }
    @Override
    public void deleteAll(String username) {
        if(!userExist(username)) throw new ClientExistException("Client does not exist");
       TodoList todoList = todoListRepository.findByUsername(username);
       taskService.deleteAllTaskFor(todoList.getId());
    }
    @Override
    public void delete(String username, Date date, String message) {
        if (!userExist(username)) throw new ClientExistException("Invalid user");
        TodoList todoList = todoListRepository.findByUsername(username);
        taskService.delete(todoList.getId(), message, date);
    }
    @Override
    public void deleteAccount(String username) {
     if (!userExist(username)) throw new ClientExistException("Client does not exist");
     TodoList todoList = todoListRepository.findByUsername(username);
     todoListRepository.delete(todoList);
    }
    private boolean userExist(String username) {
        TodoList todoList = todoListRepository.findByUsername(username);
        return todoList != null;}
}
