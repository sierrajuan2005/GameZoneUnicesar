
package com.gamezone.exceptions;

/**
 * Base class for application-specific exceptions in GameZone.
 */
public abstract class GameZoneException extends RuntimeException {

    private final String errorCode;

    /**
     * Creates an exception with a message and no error code.
     *
     * @param message description of the error
     */
    public GameZoneException(String message) {
        super(message);
        this.errorCode = null;
    }

    /**
     * Creates an exception with a message and an error code.
     *
     * @param message description of the error
     * @param errorCode unique code identifying the error
     */
    public GameZoneException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Returns the error code associated with this exception.
     *
     * @return the error code, or null if none was provided
     */
    public String getErrorCode() {
        return errorCode;
    }
}
