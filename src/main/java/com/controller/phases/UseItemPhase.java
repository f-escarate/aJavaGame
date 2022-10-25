package com.controller.phases;

import com.controller.GameController;

/**
 * Phase that is used when a main character uses an item
 * @author Felipe Escárate
 */
public class UseItemPhase extends Phase {
    /**
     * Creates an UseItemPhase
     * @param aController the controller of the phase
     */
    public UseItemPhase(GameController aController) {
        super(aController);
    }

    @Override
    public void toNextTurnChoosingPhase(){
        changePhase(new NextTurnChoosingPhase(super.controller, isPlayerTurn()));
    }

}
