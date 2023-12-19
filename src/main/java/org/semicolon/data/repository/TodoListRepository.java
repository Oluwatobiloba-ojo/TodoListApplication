package org.semicolon.data.repository;

import org.semicolon.data.model.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoListRepository extends MongoRepository<TodoList, String> {
    TodoList findByUsername(String username);
}
