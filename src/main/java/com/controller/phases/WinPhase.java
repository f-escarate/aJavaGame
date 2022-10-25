package com.controller.phases;

import com.controller.GameController;
/**
 * Phase that represents when the player has won a battle
 * @author Felipe Escárate
 */
public class WinPhase extends Phase{
    /**
     * Creates an WinPhase
     * @param aController the controller of the phase
     */
    public WinPhase(GameController aController) {
        super(aController);
        setMessage("Has ganado");
    }
    @Override
    public boolean won(){
        return true;
    }

    @Override
    public void toNextTurnChoosingPhase(){
        if(controller.getLvl() == 6){
            controller.setUp(); // si llega a nivel 6, simplemente se empieza desde 0
            return;
        }
        controller.levelUP();
        changePhase(new NextTurnChoosingPhase(controller, true));
    }

}
