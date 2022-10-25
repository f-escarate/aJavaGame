package com.tomandmax.enemies;

/**
 *Interface that is used to call the enemies that are
 * immune to an attack type
 * @author Felipe Escárate
 */
public interface ImmuneEnemy extends Enemy{
    /**
     * Check if the enemy type is immune to jump attack
     * Is used by Salto class
     */
    boolean isImmuneToSalto();

    /**
     * Check if the enemy type is immune to hammer attack
     * Is used by Martillo class
     */
    boolean isImmuneToMartillo();

}
