package com.controller.exceptions;

/**
 * Exception that is used when a main character can't select an enemy
 * and the selectedEnemy field in the AbstractMainCharacter Class is null
 * @author Felipe Escárate
 */
public class NullEnemyException extends Exception {
    /**
     * Creates a NullEnemyException
     * @param msg the message that is showed by the exception
     */
    public NullEnemyException(final String msg) {
        super(msg);
    }
}
