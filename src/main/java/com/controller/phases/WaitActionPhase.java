package com.controller.phases;

import com.controller.GameController;

/**
 * Phase used by to wait the user to select the next move.
 * The user can select: attack, use item or pass the turn
 * @author Felipe Escárate
 */
public class WaitActionPhase extends Phase {
    /**
     * Creates an WaitActionPhase
     * @param aController the controller of the phase
     */
    public WaitActionPhase(GameController aController) {
        super(aController);
        setMessage("Elija entre: atacar, usar un item o pasar el turno");
    }

    @Override
    public boolean isWaitingForAction() {
        return true;
    }

    @Override
    public void toAttackSelectionPhase(){
        changePhase(new AttackSelectionPhase(super.controller));
    }

    @Override
    public void toItemSelectionPhase(){
        changePhase(new ItemSelectionPhase(super.controller));
    }

    @Override
    public void toNextTurnChoosingPhase(){
        changePhase(new NextTurnChoosingPhase(super.controller, isPlayerTurn()));
    }

}
