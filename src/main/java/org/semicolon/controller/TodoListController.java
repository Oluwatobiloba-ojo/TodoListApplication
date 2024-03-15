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
    @Autowired
    private TodoListService todoListService;

    @GetMapping("/")
    public String run() {
        return "Application is up and running";
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            todoListService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Account Has been created", true));
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            todoListService.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse("You don log in !!!",
                    true), HttpStatus.ACCEPTED);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(),
                    false), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody CreateRequest createRequest) {
        DataRequest dataRequest = getDataRequest(createRequest);
        try {
            todoListService.create(createRequest.getUsername(), dataRequest);
            return new ResponseEntity<>(new ApiResponse("We don create Task", true), HttpStatus.CREATED);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    private static DataRequest getDataRequest(CreateRequest createRequest) {
        DataRequest dataRequest = new DataRequest();
        dataRequest.setMessage(createRequest.getMessage());
        if (createRequest.getDateCreated() != null)
            dataRequest.setDate(Mapper.mapDateToLocalDate(createRequest.getDateCreated()));
        dataRequest.setDueDateTime(Mapper.mapDueDateToLocalDateTime(createRequest.getDueDateTime()));
        return dataRequest;
    }

    @PutMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody UpdateRequest updateRequest) {
        try {
            todoListService.update(updateRequest.getUsername(), updateRequest.getDateCreated(),
                    updateRequest.getOldMessage(), updateRequest.getNewMessage());
            return ResponseEntity.ok(new ApiResponse("We don update am ooh", true));
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(),
                    false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewAllTodo")
    public ResponseEntity<?> viewAllTodo(@RequestBody ViewRequest viewRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(todoListService.
                    findTaskBelongingTo(viewRequest.getUsername()), true), HttpStatus.OK);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewADayTask")
    public ResponseEntity<?> viewADayTask(@RequestBody ViewADayRequest viewADayRequest) {
        try {
            List<Task> tasks = todoListService.findTodoCreatedBy(viewADayRequest.getUsername(), viewADayRequest.getDate());
            return new ResponseEntity<>(new ApiResponse(tasks, true), HttpStatus.OK);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(),
                    false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewATask")
    public ResponseEntity<?> viewATask(@RequestBody ViewATaskRequest viewATaskRequest) {
        try {
            Task task = todoListService.findTaskFor(viewATaskRequest.getUsername(), viewATaskRequest.getMessage(),
                    viewATaskRequest.getDateCreated());
            return new ResponseEntity<>(new ApiResponse(task, true), HttpStatus.OK);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAll/{username}")
    public ResponseEntity<?> deleteAll(@PathVariable("username") String username) {
        try {
            todoListService.deleteAll(username);
            return new ResponseEntity<>(new ApiResponse("Deleted", true), HttpStatus.OK);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse("Deleted", false), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody DeleteATaskRequest deleteRequest) {
        try {
            todoListService.delete(deleteRequest.getUsername(), deleteRequest.getDateCreated(), deleteRequest.getMessage());
            return new ResponseEntity<>(new ApiResponse("Deleted " + deleteRequest.getMessage() + " done !!!!",
                    true), HttpStatus.OK);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateDueDate")
    public ResponseEntity<?> updateDueDate(@RequestBody UpdateDueDateRequest updateDueDateRequest) {
        try {
            todoListService.update(updateDueDateRequest.getUsername(), updateDueDateRequest.getDateCreated(),
                    updateDueDateRequest.getDescription(), updateDueDateRequest.getDueDate());
            return new ResponseEntity<>(new ApiResponse("We don update am ooh", true), HttpStatus.OK);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAccount/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable("username") String username) {
        try {
            todoListService.deleteAccount(username);
            return new ResponseEntity<>(new ApiResponse("Deleted......", true), HttpStatus.OK);
        } catch (TodoListException todoListException) {
            return new ResponseEntity<>(new ApiResponse(todoListException.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

}
