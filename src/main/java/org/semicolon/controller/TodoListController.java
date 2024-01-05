package org.semicolon.controller;

import org.semicolon.data.model.Task;
import org.semicolon.dtos.request.*;
import org.semicolon.dtos.response.*;
import org.semicolon.exception.TodoListException;
import org.semicolon.service.TodoListService;
import org.semicolon.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class TodoListController {
    private final TodoListService todoListService;
    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            todoListService.register(registerRequest);
            registerResponse.setMessage("Account has been created");
            return new ResponseEntity<>(new ApiResponse(registerResponse, true), HttpStatus.ACCEPTED);
        }catch (TodoListException todoListException){
            registerResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(registerResponse, false), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();
        try{
            todoListService.login(loginRequest);
            loginResponse.setMessage("You don log in !!!!!!!!!!!");
            return new ResponseEntity<>(new ApiResponse(loginResponse, true),HttpStatus.ACCEPTED);
        }catch (TodoListException todoListException){
            loginResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(loginResponse, false), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody CreateRequest createRequest){
        CreateResponse createResponse = new CreateResponse();
        DataRequest dataRequest = new DataRequest();
        dataRequest.setMessage(createRequest.getMessage());
        if (createRequest.getDateCreated() != null) dataRequest.setDate(Mapper.mapDateToLocalDate(createRequest.getDateCreated()));
        dataRequest.setDueDateTime(Mapper.mapDueDateToLocalDateTime(createRequest.getDueDateTime()));
        try {
            todoListService.create(createRequest.getUsername(), dataRequest);
            createResponse.setMessage("We don create Task ooh");
            return new ResponseEntity<>(new ApiResponse(createResponse, true), HttpStatus.CREATED);
        }catch (TodoListException todoListException){
            createResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(createResponse, false), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateTask")
    public ResponseEntity<?>  updateTask(@RequestBody UpdateRequest updateRequest){
        UpdateResponse updateResponse = new UpdateResponse();
        try {
            todoListService.update(updateRequest.getUsername(), updateRequest.getDateCreated(),
                    updateRequest.getOldMessage(), updateRequest.getNewMessage());
            updateResponse.setMessage("We don update am ooh");
            return new ResponseEntity<>(new ApiResponse(updateResponse, true), HttpStatus.OK);
        }catch (TodoListException todoListException){
            updateResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(updateResponse,false), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewAllTodo")
    public ResponseEntity<?> viewAllTodo(@RequestBody ViewRequest viewRequest){
        ViewResponse viewResponse = new ViewResponse();
        try{
           List<Task> task = todoListService.findTaskBelongingTo(viewRequest.getUsername());
            viewResponse.setTask(task);
            return new ResponseEntity<>(new ApiResponse(viewResponse, true), HttpStatus.OK);
        }catch (TodoListException todoListException){
            viewResponse.setTask(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(viewResponse, false), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewADayTask")
    public ResponseEntity<?> viewADayTask(@RequestBody ViewADayRequest viewADayRequest){
        ViewADayResponse viewADayResponse = new ViewADayResponse();
        try{
            List<Task> tasks = todoListService.findTodoCreatedBy(viewADayRequest.getUsername(), viewADayRequest.getDate());
            viewADayResponse.setMessage(tasks);
            return new ResponseEntity<>(new ApiResponse(viewADayResponse, true), HttpStatus.OK);
        }catch (TodoListException todoListException){
            viewADayResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(viewADayResponse, false), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewATask")
    public ResponseEntity<?> viewATask(@RequestBody ViewATaskRequest viewATaskRequest){
        ViewATaskResponse viewATaskResponse = new ViewATaskResponse();
        try {
          Task task = todoListService.findTaskFor(viewATaskRequest.getUsername(),viewATaskRequest.getMessage(),
                  viewATaskRequest.getDateCreated());
          viewATaskResponse.setMessage(task);
          return new ResponseEntity<>(new ApiResponse(viewATaskResponse, true), HttpStatus.OK);
        }catch (TodoListException todoListException){
            viewATaskResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(viewATaskResponse, false), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteAll/{username}")
    public ResponseEntity<?> deleteAll(@PathVariable("username") String username){
        DeleteAllResponse deleteAllResponse = new DeleteAllResponse();
        try {
            todoListService.deleteAll(username);
            deleteAllResponse.setMessage("Deleted");
            return new ResponseEntity<>(new ApiResponse(deleteAllResponse, true), HttpStatus.OK);
        }catch (TodoListException todoListException){
            deleteAllResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(deleteAllResponse, false), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody DeleteATaskRequest deleteRequest){
      DeleteATaskResponse deleteATaskResponse = new DeleteATaskResponse();
      try {
          todoListService.delete(deleteRequest.getUsername(), deleteRequest.getDateCreated(), deleteRequest.getMessage());
          deleteATaskResponse.setMessage("Deleted "+ deleteRequest.getMessage() + " done !!!!");
          return new ResponseEntity<>(new ApiResponse(deleteATaskResponse, true), HttpStatus.OK);
      }catch (TodoListException todoListException){
          deleteATaskResponse.setMessage(todoListException.getMessage());
          return new ResponseEntity<>(new ApiResponse(deleteATaskResponse, false), HttpStatus.NOT_FOUND);
      }
    }
    @PutMapping("/updateDueDate")
    public ResponseEntity<?> updateDueDate(@RequestBody UpdateDueDateRequest updateDueDateRequest){
        UpdateDueDateResponse updateDueDateResponse = new UpdateDueDateResponse();
        try {
            todoListService.update(updateDueDateRequest.getUsername(), updateDueDateRequest.getDateCreated(),
                    updateDueDateRequest.getDescription(), updateDueDateRequest.getDueDate());
            updateDueDateResponse.setMessage("We don update ooh!!!!!");
            return new ResponseEntity<>(new ApiResponse(updateDueDateResponse, true), HttpStatus.OK);
        }catch (TodoListException todoListException){
            updateDueDateResponse.setMessage(todoListException.getMessage());
            return new ResponseEntity<>(new ApiResponse(updateDueDateResponse, false), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteAccount/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable("username") String username){
        try {
            todoListService.deleteAccount(username);
            return new ResponseEntity<>(new ApiResponse("Deleted......", true), HttpStatus.OK);
        }catch (TodoListException todoListException){
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

}
