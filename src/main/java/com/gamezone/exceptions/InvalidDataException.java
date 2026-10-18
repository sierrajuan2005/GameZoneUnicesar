package com.gamezone.exceptions;

/**
 * Exception thrown when provided data is invalid.
 */
public class InvalidDataException extends GameZoneException {

    /**
     * Creates an exception for a field containing invalid data.
     *
     * @param fieldName name of the invalid field
     * @param problem description of the validation problem
     */
    public InvalidDataException(String fieldName, String problem) {
        super("El campo " + fieldName + " no es valido: " + problem,
                "INVALID_DATA");
    }
}