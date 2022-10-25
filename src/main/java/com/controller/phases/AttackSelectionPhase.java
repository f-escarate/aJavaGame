package com.controller.phases;

import com.tomandmax.attacks.Attack;
import com.tomandmax.mainCharacters.MainCharacter;
import com.controller.GameController;
import com.controller.exceptions.NullAttackException;

/**
 * Phase that is used when a main character is selecting an attack
 * @author Felipe Escárate
 */
public class AttackSelectionPhase extends Phase{

    /**
     * Creates an AttackSelectionPhase
     * @param aController the controller of the phase
     */
    public AttackSelectionPhase(GameController aController) {
        super(aController);
        setMessage("Elige un ataque");
    }

    @Override
    public boolean isWaitingForAttack() {
        return true;
    }

    @Override
    public void toWaitActionPhase(){
        changePhase(new WaitActionPhase(super.controller));
    }

    @Override
    public void toEnemySelectionPhase(){
        changePhase(new EnemySelectionPhase(super.controller));
    }

    @Override
    public void selectAttack(MainCharacter mc, Attack attack) throws NullAttackException {
        if(mc.selectAnAttack(attack)){
            toEnemySelectionPhase();
            return;
        }
        throw new NullAttackException("No tienes suficientes FP para seleccionar ese ataque");
    }

}
