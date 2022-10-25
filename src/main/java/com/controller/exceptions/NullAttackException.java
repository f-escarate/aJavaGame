package com.controller.exceptions;

/**
 * Exception that is used when a main character can't select an attack
 * and the selectedAttack field in the AbstractMainCharacter Class is null
 * @author Felipe Escárate
 */
public class NullAttackException extends Exception {
    /**
     * Creates a NullAttackException
     * @param msg the message that is showed by the exception
     */
    public NullAttackException(final String msg){
        super(msg);
    }
}
