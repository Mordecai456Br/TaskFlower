package com.begodex.taskflow.exceptions.httpExceptions;


public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " with id " + id + " not found.");
    }

    public EntityNotFoundException(String entity, String details) {
        super(entity + " not found: " + details);
    }

}
