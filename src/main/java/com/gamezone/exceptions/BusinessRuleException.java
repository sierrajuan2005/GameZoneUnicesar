package com.gamezone.exceptions;

/**
 * Exception thrown when a business rule is violated.
 */
public class BusinessRuleException extends GameZoneException {

    /**
     * Creates an exception for a violated business rule.
     *
     * @param message description of the violated business rule
     */
    public BusinessRuleException(String message) {
        super(message, "BUSINESS_RULE_VIOLATION");
    }
}