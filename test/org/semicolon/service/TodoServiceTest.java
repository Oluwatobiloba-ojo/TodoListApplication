package org.semicolon.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semicolon.data.repository.TaskRepository;
import org.semicolon.data.repository.TodoListRepository;
import org.semicolon.dtos.request.DataRequest;
import org.semicolon.dtos.request.LoginRequest;
import org.semicolon.dtos.request.RegisterRequest;
import org.semicolon.exception.ClientExistException;
import org.semicolon.exception.InvalidDetailsException;
import org.semicolon.exception.TaskExistException;
import org.semicolon.util.Date;
import org.semicolon.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {
    @Autowired
    TodoListRepository todoListRepository;
    @Autowired
    TodoListService todoListService;
    @Autowired
    TaskRepository taskRepository;
    RegisterRequest registerRequest = new RegisterRequest();
    LoginRequest loginRequest = new LoginRequest();
    @BeforeEach
    public void startAllTestWithThis(){
        todoListRepository.deleteAll();
        taskRepository.deleteAll();
        registerRequest.setUsername("ope");
        registerRequest.setPassword("Passwords45@");
        loginRequest.setUsername("ope");
        loginRequest.setPassword("Passwords45@");
    }
    @Test
    public void testThatWhenTheyRegisterWithSameUsernameTheyCanNotRegisterAnyMoreWithUsername(){
        todoListService.register(registerRequest);
        assertThrows(ClientExistException.class, ()-> todoListService.register(registerRequest));
    }
    @Test
    public void testThatWhenTheyRegisterWithSameUsernameAndLoginWithTheWrongUsername(){
        todoListService.register(registerRequest);
        loginRequest.setUsername("oluwa");
        assertThrows(InvalidDetailsException.class, ()-> todoListService.login(loginRequest));
    }
    @Test
    public void testThatLoginWithWrongPasswordThrowException(){
        todoListService.register(registerRequest);
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidDetailsException.class, ()-> todoListService.login(loginRequest));
    }
    @Test
    public void testThatWhenICreateATodoCountIncreaseByOne() {
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2001);
        date.setMonth(11);
        date.setDay(30);
        DateTime dueDateTime = new DateTime();
        dueDateTime.setDate(date);
        DataRequest dataRequest = new DataRequest("Today todolist", dueDateTime);
        todoListService.create("ope", dataRequest);
        assertEquals(1, taskRepository.count());
    }
    @Test
    public void testThatWhenICreateATaskForDifferentDateAndIWantToFindATaskCreatedInADay(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2004);
        date.setMonth(6);
        date.setDay(12);
        Date date1 = new Date();
        date1.setYear(2023);
        date1.setMonth(12);
        date1.setDay(18);
        DateTime dateTime = new DateTime();
        dateTime.setDate(date1);
        dateTime.setHour(12);
        dateTime.setMinute(30);
        DataRequest dataRequest = new DataRequest("Today todoList",date1, dateTime);
        todoListService.create("ope", dataRequest);
        DataRequest dataRequest1 = new DataRequest("Tomorrow todolist", date, dateTime);
        todoListService.create("ope", dataRequest1);
        assertEquals(2, taskRepository.count());
        assertEquals(1, todoListService.findTodoCreatedBy("ope", date1).size());
    }
    @Test
    public void testThatWhenWeCreateATodoForDifferentPersonCanFindTheTodoBelongingToAUser(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);

        registerRequest.setUsername("delighted");
        registerRequest.setPassword("Passwords45@");
        loginRequest.setUsername("delighted");
        loginRequest.setPassword("Passwords45@");
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2020);
        date.setMonth(8);
        date.setDay(17);
        DateTime dateTime = new DateTime();
        dateTime.setDate(date);
        dateTime.setHour(12);
        dateTime.setMinute(30);
        DataRequest dataRequest = new DataRequest("Shopping mall", dateTime);
        todoListService.create("ope", dataRequest);
        DataRequest dataRequest1 = new DataRequest("Shop market", dateTime);
        todoListService.create("delighted", dataRequest1);
        assertEquals(2, todoListRepository.count());
        assertEquals(2, taskRepository.count());
        assertEquals(1, todoListService.findTaskBelongingTo("ope").size());
        assertEquals(1, todoListService.findTaskBelongingTo("delighted").size());
    }

    @Test
    public void testThatWhenWeWantCreateATwoTodoCanFindAParticularOneWithWrongMessageThrowException(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2020);
        date.setMonth(8);
        date.setDay(17);
        DateTime dateTime = new DateTime();
        dateTime.setDate(date);
        dateTime.setHour(12);
        dateTime.setMinute(30);
        DataRequest dataRequest = new DataRequest("Shopping mall", dateTime);
        todoListService.create("ope", dataRequest);
        DataRequest dataRequest1 = new DataRequest("Shop market", dateTime);
        todoListService.create("ope", dataRequest1);
        Date dateCreated = new Date();
        dateCreated.setYear(2020);
        dateCreated.setMonth(9);
        dateCreated.setDay(17);
        assertThrows(InvalidDetailsException.class, ()-> todoListService.findTaskFor("ope", "Shop markets", dateCreated));
    }
    @Test
    public void testThatWhenICreateATodoWeCanUpdateTheTaskWithAMessageGivenThatUsername_DateCollected(){
       todoListService.register(registerRequest);
       todoListService.login(loginRequest);
       Date date = new Date();
       date.setYear(2020);
       date.setMonth(8);
       date.setDay(17);
       DateTime dateTime = new DateTime();
       dateTime.setDate(date);
       dateTime.setHour(12);
       dateTime.setMinute(30);
        Date dateCreated = new Date();
        dateCreated.setYear(2023);
        dateCreated.setMonth(12);
        dateCreated.setDay(18);
       DataRequest dataRequest1 = new DataRequest("Old message",dateCreated, dateTime);
       todoListService.create("ope", dataRequest1);
       todoListService.update("ope", dateCreated, "Old message", "new Message");
       assertNotNull(todoListService.findTaskFor("ope", "new Message", dateCreated).getMessage());
    }
    @Test
    public void testThatWhenICreateATodoWeCanUpdateTheWithTheDueDateGivenThatUsername_DateCreatedCollected(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2020);
        date.setMonth(8);
        date.setDay(17);
        DateTime dueDateTime = new DateTime();
        dueDateTime.setDate(date);
        dueDateTime.setHour(12);
        dueDateTime.setMinute(30);
        Date dateCreated = new Date();
        dateCreated.setYear(2023);
        dateCreated.setMonth(12);
        dateCreated.setDay(18);
        DataRequest dataRequest1 = new DataRequest("Old message",dateCreated, dueDateTime);
        todoListService.create("ope", dataRequest1);
        DateTime dateTime = new DateTime();
        dateTime.setDate(date);
        dateTime.setHour(10);
        dueDateTime.setMinute(30);
        LocalDateTime localDateTime = LocalDateTime.of(2020,8, 17,10,0);
        todoListService.update("ope", dateCreated, "Old message", dateTime);
        Assertions.assertEquals(localDateTime, todoListService.findTaskFor("ope", "Old message", dateCreated).getDueDateTime());
    }
    @Test
    public void testThatWhenWeCreateTaskOnTodoICanDeleteAllTodoTask(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2020);
        date.setMonth(8);
        date.setDay(17);
        DateTime dateTime = new DateTime();
        dateTime.setDate(date);
        dateTime.setHour(12);
        dateTime.setMinute(30);
        DataRequest dataRequest1 = new DataRequest("Old message", dateTime);
        todoListService.create("ope", dataRequest1);
        assertEquals(1, taskRepository.count());
        todoListService.deleteAll("ope");
        assertThrows(TaskExistException.class, () ->todoListService.findTaskBelongingTo("ope"));
    }
    @Test
    public void testThatWeCanCreateTwoTodo_ICanDeleteOneTodoTaskAndCountIsOne(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2020);
        date.setMonth(8);
        date.setDay(17);
        DateTime dueDateTime = new DateTime();
        dueDateTime.setDate(date);
        dueDateTime.setHour(12);
        dueDateTime.setMinute(30);
        Date dateCreated = new Date();
        dateCreated.setYear(2023);
        dateCreated.setMonth(12);
        dateCreated.setDay(18);
        DataRequest dataRequest1 = new DataRequest("Old message",dateCreated, dueDateTime);
        dueDateTime.setHour(10);
        DataRequest dataRequest = new DataRequest("Next message",dateCreated, dueDateTime);
        todoListService.create("ope", dataRequest1);
        todoListService.create("ope", dataRequest);
        assertEquals(2, taskRepository.count());
        todoListService.delete("ope", dateCreated, "Next message");
        dateCreated.setDay(17);
        assertThrows(InvalidDetailsException.class, ()-> todoListService.findTaskFor("ope", "Next message",
                dateCreated));
        assertEquals(1, todoListService.findTaskBelongingTo("ope").size());
    }
}