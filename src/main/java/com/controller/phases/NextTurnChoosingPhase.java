package com.controller.phases;

import com.controller.GameController;

/**
 * Phase that allows to choose who will be the next turn owner
 * It checks if the player win or lose
 * Also, it uses a TurnSelector to decide who will be the next turn owner
 * @author Felipe Escárate
 */
public class NextTurnChoosingPhase extends Phase {
    boolean playerTurn;

    /**
     * Creates a NextTurnChoosingPhase
     * @param aController the controller of the phase
     * @param isPlayerTurn a boolean variable that checks if in the previous phase was the player turn or not
     *                     in this class, that variable can be changed
     */
    public NextTurnChoosingPhase(GameController aController, boolean isPlayerTurn) {
        super(aController);
        playerTurn = isPlayerTurn;
    }

    @Override
    public boolean isPlayerTurn(){
        return playerTurn;
    }

    @Override
    public boolean isChoosingNextTurn(){
        return true;
    }

    @Override
    public void toWaitActionPhase(){
        changePhase(new WaitActionPhase(super.controller));
    }

    @Override
    public void toEnemiesPhase(){
        changePhase(new EnemiesPhase(super.controller));
    }

    @Override
    public void chooseNextTurn(){
        if(controller.mcsDefeated()){
            changePhase(new LossPhase(controller));
            return;
        }
        if(controller.enemiesDefeated()){
            changePhase(new WinPhase(controller));
            return;
        }
        controller.endTurn();
        if(super.controller.getCurrentTurn() == 0){
            playerTurn = !playerTurn;
            controller.changeTurnSelector();
        }
        controller.runTurnSelector();
    }



}
