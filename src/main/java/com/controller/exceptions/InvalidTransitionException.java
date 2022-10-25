package com.controller.exceptions;

/**
 * Exception that is used when we can´t move from one phase to another
 * @author Felipe Escárate
 */
public class InvalidTransitionException extends Exception {
    /**
     * Creates a InvalidTransitionException
     * @param message the message that is showed by the exception
     */
    public InvalidTransitionException(final String message) {
        super(message);
    }
}
