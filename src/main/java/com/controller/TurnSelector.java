package com.controller;

/**
 * Interface that is used to call the TurnSelectors
 */
public interface TurnSelector {

    /**
     * Method that makes the controller to choose the phase which it will go
     * If is a MCSelector, the controller will go to the WaitActionPhase
     * and if is a EnemySelector, the controller will go to the EnemiesPhase
     */
    void select();
}
