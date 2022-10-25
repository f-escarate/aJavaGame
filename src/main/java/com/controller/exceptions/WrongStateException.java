package com.controller.exceptions;

/**
 * Exception that is used when we try to call a method in a phase that
 * isn't designed to support that method
 * @author Felipe Escárate
 */
public class WrongStateException extends Exception{
    /**
     * Creates a WrongStateException
     * @param message the message that is showed by the exception
     */
    public WrongStateException(final String message){
        super(message);
    }
}
