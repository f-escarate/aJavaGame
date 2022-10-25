package com.tomandmax.mainCharacters;

import com.tomandmax.Character;
import com.tomandmax.attacks.Attack;
import com.tomandmax.enemies.Enemy;

/**
 * Interface that represents a main character
 * i.e. Tom and Max
 * @author Felipe Escárate
 */
public interface MainCharacter extends Character {
    /**
     * Check if the main character can attack an enemy
     * it depends on the enemy's type
     * @param enemy (the enemy that we need to know if the main character can attack)
     * Returns true if the main character can attack to the enemy; false if not
     */
    boolean canAttack(Enemy enemy);

    /**
     * Check if the main character can be attacked by an enemy
     * @param enemy the enemy that attacks the main character
     * Returns true if the main character can be attacked by the enemy and false if not
     */
    boolean canBeAttackedBy(Enemy enemy);

    /**
     * Selects the attack that the main character will use
     * It happens only if the main character has enough fight points
     * @param atk the selected attack
     * @return true if the attack was successfully selected and false if it couldn't be selected
     */
    boolean selectAnAttack(Attack atk);

    /**
     * Selects an enemy to be attacked by the main character
     * @param enemy the enemy that will be attacked
     * @return true if the enemy was successfully selected and false if it couldn't be selected
     */
    boolean selectAnEnemy(Enemy enemy);

    /**
     * Do an attack from the main character to the selected enemy
     * The main character will make damage only if he hit the hit (considering probability)
     *  (it depends on the type attack probability)
     * @param randDouble    number compared with the probability of the attack to check if the attack was hit
     * @return a string that explains if the attack was successful, if the main character fails the attack due the
     *          probability or if the main character cannot attack the selected enemy
     */
    String attackSelectedEnemy(double randDouble);

    /**
     * Receives an enemy attack
     * i.e. calculates the damage and receives it
     * @param atk the enemy attack points
     * @param lvl the enemy level
     */
    void receiveEnemyAttack(int atk, int lvl);

    /**
     * Heals main character 10% of his max HP
     * It happens when the main character consumes a Healing Potion
     */
    void healingEffect();

    /**
     * Gives 3 FP to the main character
     * It happens when the main character consumes a Fighting Potion
     */
    void fightingEffect();

    /**
     * Gets the main character's fight points
     * (is public because is used in test)
     * @return the fp
     */
    int getFP();

    /**
     * Gets the maximum quantity of fight points of the main character
     * @return the maxFP
     */
    int getMaxFP();

    /**
     * Levels up the main character, i.e. upgrades the main character stats in 15%
     */
    void lvlUp();

    /**
     * Recovers the Health Points and the Fight Points of the main character
     */
    void recoverHPAndFP();
}
