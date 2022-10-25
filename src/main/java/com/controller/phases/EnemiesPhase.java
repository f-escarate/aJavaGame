package com.controller.phases;

import com.tomandmax.enemies.Enemy;
import com.tomandmax.mainCharacters.MainCharacter;
import com.controller.GameController;
import com.controller.exceptions.WrongStateException;

/**
 * Phase that represents when the enemies do their turn
 * @author Felipe Escárate
 */
public class EnemiesPhase extends Phase {
    /**
     * Creates an EnemiesPhase
     * @param aController the controller of the phase
     */
    public EnemiesPhase(GameController aController) {
        super(aController);
    }

    @Override
    public boolean isPlayerTurn(){
        return false;
    }

    @Override
    public void toNextTurnChoosingPhase() {
        changePhase(new NextTurnChoosingPhase(super.controller, isPlayerTurn()));
    }

    @Override
    public MainCharacter getCurrentMC() throws WrongStateException {
        throw new WrongStateException("No es el turno del jugador");
    }

    @Override
    public Enemy getCurrentEnemy() {
        return controller.getCurrentEnemy();
    }

    @Override
    public String getOwnerName(){
        return controller.getEnemyName(controller.getCurrentTurn());
    }

}
