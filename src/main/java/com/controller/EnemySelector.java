package com.controller;

/**
 * TurnSelector, that makes the next turn, be an enemy turn
 * @author Felipe Escárate
 */
public class EnemySelector implements TurnSelector{
    private final GameController controller;

    /**
     * Creates an EnemySelector
     * @param aController the controller that will be influenced by the TurnSelector
     */
    EnemySelector(GameController aController){
        controller = aController;
    }


    @Override
    public void select() {
        controller.tryToGoToEnemyTurn();
    }

}
