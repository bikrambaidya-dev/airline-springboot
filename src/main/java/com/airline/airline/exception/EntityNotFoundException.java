package com.airline.airline.exception;

/**
 * Custom exception for when a requested entity is not found.
 * Will be caught by GlobalExceptionHandler and converted to 404 response.
 */
public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method for common "entity not found" pattern
     */
    public static EntityNotFoundException forEntity(String entityName, String fieldName, Object fieldValue) {
        return new EntityNotFoundException(
            String.format("%s with %s '%s' not found", entityName, fieldName, fieldValue)
        );
    }

    /**
     * Factory method for entity not found by ID
     */
    public static EntityNotFoundException forId(String entityName, Long id) {
        return forEntity(entityName, "id", id);
    }
}
