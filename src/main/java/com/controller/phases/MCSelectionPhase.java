package com.controller.phases;

import com.controller.GameController;

/**
 * Phase that is used when a main character is selecting a main character to use an item in him
 * @author Felipe Escárate
 */
public class MCSelectionPhase extends Phase {
    private final String itemName;
    /**
     * Creates an MCSelectionPhase
     * @param aController the controller of the phase
     * @param itemName is the name that the main character has selected in the previous phase
     */
    public MCSelectionPhase(GameController aController, String itemName) {
        super(aController);
        this.itemName = itemName;
        setMessage("Elige un personaje en el cual usar el item");
    }

    @Override
    public boolean isSelectingMC() {
        return true;
    }

    @Override
    public void toItemSelectionPhase(){
        controller.addItem(itemName); //in this case, we give back the item that we took out from the chest to it
        changePhase(new ItemSelectionPhase(super.controller));
    }

    @Override
    public void toUseItemPhase(){
        changePhase(new UseItemPhase(super.controller));
    }
}
