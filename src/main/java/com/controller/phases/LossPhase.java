package com.controller.phases;

import com.controller.GameController;

/**
 * Phase that represents when the player has lost a battle
 * @author Felipe Escárate
 */
public class LossPhase extends Phase {
    /**
     * Creates an LossPhase
     * @param controller the controller of the phase
     */
    public LossPhase(GameController controller) {
        super(controller);
        setMessage("Has perdido");
    }

    @Override
    public boolean lost(){
        return true;
    }

    @Override
    public void toNextTurnChoosingPhase(){
        controller.setUp();
    }

}
