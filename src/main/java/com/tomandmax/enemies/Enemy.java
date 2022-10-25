package com.tomandmax.enemies;

import com.tomandmax.attacks.Attack;
import com.tomandmax.mainCharacters.MainCharacter;
import com.tomandmax.Character;

/**
 * That interface represents an enemy
 * @author Felipe Escárate
 */
public interface Enemy extends Character {
    /**
     * Checks if the enemy can be attacked by Tom
     * Is used by Tom class
     * @return true if Tom can attack enemy and false if not
     */
    boolean canBeAttackedByTom();

    /**
     * Checks if the enemy can be attack Tom
     * Is used by Tom class
     * @return true if the enemy can attack Tom and false if not
     */
    boolean canAttackTom();

    /**
     * Checks if the enemy can be attacked by Max
     * Is used by Max class
     * @return true if Max can attack enemy and false if not
     */
    boolean canBeAttackedByMax();

    /**
     * Checks if the enemy can be attack Max
     * Is used by Max class
     * @return true if the enemy can attack Max and false if not
     */
    boolean canAttackMax();

    /**
     * Receives a main character attack
     * @param character the main character who attacks
     * @param mcAtk     the main character attack points
     * @param mcLvl     the main character level
     * @param attack the attack type that the main character did
     * @return a message that explains the MC attack
     */
    String receiveMainCharacterAttack(MainCharacter character, int mcAtk, int mcLvl, Attack attack);

    /**
     * Does an attack from the enemy to a main character
     * @param mainCharacter (is the main character to attack)
     */
    void attackMainCharacter(MainCharacter mainCharacter);

    /**
     * Sets the index that differentiates the enemies of the same enemy type
     * (This index is part of the enemy name)
     * @param index (is the enemy index)
     */
    void setNameIndex(int index);

    /**
     * Gives the name of the enemy kind
     * @return the kind of the enemy
     */
    String getBaseName();

}
