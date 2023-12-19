package org.semicolon.exception;

public class InvalidLogInDetails extends TodoListException{
    public InvalidLogInDetails(String message){
        super(message);
    }
}
