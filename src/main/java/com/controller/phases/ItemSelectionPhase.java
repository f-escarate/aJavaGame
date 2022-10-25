package com.controller.phases;

import com.controller.GameController;

/**
 * Phase that is used when a main character is selecting an item to use
 * @author Felipe Escárate
 */
public class ItemSelectionPhase extends Phase {
    /**
     * Creates an ItemSelectionPhase
     * @param aController the controller of the phase
     */
    public ItemSelectionPhase(GameController aController) {
        super(aController);
        setMessage("Elija un item");
    }

    @Override
    public void toWaitActionPhase(){
        changePhase(new WaitActionPhase(super.controller));
    }

    @Override
    public void toMCSelectionPhase(String itemName) throws NullPointerException{
        changePhase(new MCSelectionPhase(super.controller, itemName));
    }
}
