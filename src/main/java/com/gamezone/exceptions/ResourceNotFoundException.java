package com.gamezone.exceptions;

/**
 * Exception thrown when a requested resource cannot be found.
 */
public class ResourceNotFoundException extends GameZoneException {

    /**
     * Creates an exception for a resource that could not be found.
     *
     * @param resourceType type of resource that was not found
     * @param identifier identifier of the missing resource
     */
    public ResourceNotFoundException(String resourceType, String identifier) {
        super("El recurso " + resourceType
                + " con identificador " + identifier
                + " no fue encontrado.", "RESOURCE_NOT_FOUND");
    }
}