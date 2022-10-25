package com.tomandmax.attacks;

import com.tomandmax.enemies.ImmuneEnemy;

/**
 * Interface that represents an attack that a main character can do
 * @author Felipe Escárate
 */
public interface Attack {
    /**
     * Check if an Enemy is immune to the attack
     * @param enemy (is the enemy to attack)
     * @return true if enemy is immune and false if not
     */
    boolean isUselessTo(ImmuneEnemy enemy);

    /**
     * Gets the cost of the attack in fighting points
     * @return  the cost in FP
     */
    int getCostFP();

    /**
     * Gets the probability of hit an attack
     * @return the probability
     */
    double getHitProbability();

    /**
     * Gets the constant K that is used to calculate the damage
     * @return K
     */
    double getK();
}
