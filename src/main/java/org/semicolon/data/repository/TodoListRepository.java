package org.semicolon.data.repository;

import org.semicolon.data.model.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends MongoRepository<TodoList, String> {
    TodoList findByUsername(String username);
}
