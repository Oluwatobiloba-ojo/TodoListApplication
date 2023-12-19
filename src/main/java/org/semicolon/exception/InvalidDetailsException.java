package org.semicolon.exception;

public class InvalidDetailsException extends TodoListException{
    public InvalidDetailsException(String message){
        super(message);
    }
}
