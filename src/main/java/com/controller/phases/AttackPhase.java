package com.controller.phases;

import com.controller.GameController;

/**
 * Phase when the main character attacks an enemy
 * @author Felipe Escárate
 */
public class AttackPhase extends Phase {

    /**
     * Creates an attack phase
     * @param aController the controller of the phase
     */
    public AttackPhase(GameController aController) {
        super(aController);
    }

    @Override
    public void toNextTurnChoosingPhase() {
        changePhase(new NextTurnChoosingPhase(super.controller, isPlayerTurn()));
    }

}
