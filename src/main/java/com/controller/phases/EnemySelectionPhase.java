package com.controller.phases;

import com.tomandmax.enemies.Enemy;
import com.tomandmax.mainCharacters.MainCharacter;
import com.controller.GameController;
import com.controller.exceptions.NullEnemyException;

import java.util.List;

/**
 * Phase that represents when a main character is selecting an enemy to attack it
 * @author Felipe Escárate
 */
public class EnemySelectionPhase extends Phase {
    /**
     * Creates an EnemySelectionPhase
     * @param aController the controller of the phase
     */
    public EnemySelectionPhase(GameController aController) {
        super(aController);
        setMessage("Elige un enemigo a atacar");
    }

    @Override
    public boolean isSelectingEnemy(){
        return true;
    }

    @Override
    public void toAttackSelectionPhase(){
        changePhase(new AttackSelectionPhase(super.controller));
    }

    @Override
    public void toAttackPhase(){
        changePhase(new AttackPhase(super.controller));
    }

    @Override
    public void selectEnemy(MainCharacter mc, int enemyIndex, List<Enemy> enemiesList) throws NullEnemyException {
        if(mc.selectAnEnemy(enemiesList.get(enemyIndex))){
            toAttackPhase();
            return;
        }
        throw new NullEnemyException("No puedes seleccionar a ese enemigo");
    }

}
