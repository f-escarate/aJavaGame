package com.tomandmax.enemies;

import com.tomandmax.attacks.Attack;
import com.tomandmax.mainCharacters.MainCharacter;

/**
 * Abstract class that is used to differentiate the enemies that are
 * immune to an attack type (this class represents those enemies).
 * @author Felipe Escárate
 */
public abstract class AbstractImmuneEnemy extends AbstractEnemy implements ImmuneEnemy{
    /**
     * Creates an immune enemy
     * @param ATK the enemy's attack points
     * @param DEF the enemy's defense points
     * @param HP  the enemy's health points
     * @param LVL the enemy's level
     */
    protected AbstractImmuneEnemy(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL);
    }

    /**
     * If a main character does an attack, and the enemy is immune that attack type,
     * the enemy gives a negative effect to the main character
     * (it depends on the attack received by the enemy)
     * @param mainCharacter is the main character who attacked the enemy
     * @return a message that explains the negative effect applied to the main character
     */
    abstract String negativeEffect(MainCharacter mainCharacter);

    @Override
    public String receiveMainCharacterAttack(MainCharacter character, int mcAtk, int mcLvl, Attack attack){
        if(attack.isUselessTo(this)){
            return this.negativeEffect(character);
        }
        return super.receiveMainCharacterAttack(character, mcAtk, mcLvl, attack);
    }
}
