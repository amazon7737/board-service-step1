package org.example.application.Common.Exception;

public class EntityNotFoundException extends BoardException {
    public EntityNotFoundException(String entity, Object id) {
        super(String.format("%s with id %s not found", entity, id));
    }
}
