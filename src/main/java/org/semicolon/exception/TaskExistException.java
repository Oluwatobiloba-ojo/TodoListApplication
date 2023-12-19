package org.semicolon.exception;

public class TaskExistException extends TodoListException{
    public TaskExistException(String message) {
        super(message);
    }
}
