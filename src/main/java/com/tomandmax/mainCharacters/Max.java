package com.tomandmax.mainCharacters;

import com.tomandmax.enemies.Enemy;

/**
 * This class represents Max
 * It uses the singleton pattern
 * @author Felipe Escárate
 */
public class Max extends AbstractMainCharacter{
    private static Max uniqueInstance = null;
    /**
     * Creates Max
     * @param ATK  Max's attack points
     * @param DEF  Max's defense points
     * @param HP   Max's health points
     * @param FP   Max's fight points
     * @param LVL  Max's level
     */
    private Max(int ATK, int DEF, int HP, int FP, int LVL) {
        super(ATK, DEF, HP, FP, LVL);
        super.setName("Max");
    }

    /**
     * Creates a Max if there isn't other Max created
     * If there was a Max created, replaces it by a new Max
     * @param ATK  Max's attack points
     * @param DEF  Max's defense points
     * @param HP   Max's health points
     * @param FP   Max's fight points
     * @param LVL  Max's level
     * @return Max
     */
    public static Max create(int ATK, int DEF, int HP, int FP, int LVL){
        uniqueInstance = new Max(ATK, DEF, HP, FP, LVL);
        return uniqueInstance;
    }

    @Override
    public boolean canAttack(Enemy enemy) {
        return enemy.canBeAttackedByMax();
    }

    @Override
    public boolean canBeAttackedBy(Enemy enemy) {
        return enemy.canAttackMax();
    }

}
