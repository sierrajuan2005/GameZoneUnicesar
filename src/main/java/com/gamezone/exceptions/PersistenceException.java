package com.gamezone.exceptions;

/**
 * Exception thrown when a persistence operation fails.
 */
public class PersistenceException extends GameZoneException {

    /**
     * Creates an exception with a message and the original cause.
     *
     * @param message description of the persistence error
     * @param cause original exception that caused the failure
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, "PERSISTENCE_ERROR");
        initCause(cause);
    }
}