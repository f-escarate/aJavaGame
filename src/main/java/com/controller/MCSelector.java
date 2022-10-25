package com.controller;

/**
 * TurnSelector, that makes the next turn, be a main character turn
 * @author Felipe Escárate
 */
public class MCSelector implements TurnSelector{
    private final GameController controller;

    /**
     * Creates a MCSelector
     * @param aController the controller that will be influenced by the TurnSelector
     */
    MCSelector(GameController aController){
        controller = aController;
    }

    @Override
    public void select() {
        controller.tryToWaitForAnAction(System.out);
    }

}
