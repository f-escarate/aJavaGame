package com.tomandmax.mainCharacters;

import com.tomandmax.enemies.Enemy;

/**
 * Represents Tom in the game
 * It uses the singleton pattern
 * @author Felipe Escárate
 */
public class Tom extends AbstractMainCharacter{
    private static Tom uniqueInstance = null;

    /**
     * Creates Tom
     * @param ATK      Tom's attack points
     * @param DEF      Tom's defense points
     * @param HP       Tom's health points
     * @param FP       Tom's fight points
     * @param LVL      Tom's level
     */
    private Tom(int ATK, int DEF, int HP, int FP, int LVL) {
        super(ATK, DEF, HP, FP, LVL);
        super.setName("Tom");
    }

    /**
     * Creates Tom, if there isn't other Tom created
     * If there was a Tom created, replaces it by a new Tom
     * @param ATK      Tom's attack points
     * @param DEF      Tom's defense points
     * @param HP       Tom's health points
     * @param FP       Tom's fight points
     * @param LVL      Tom's level
     * @return Tom
     */
    public static Tom create(int ATK, int DEF, int HP, int FP, int LVL){
        uniqueInstance = new Tom(ATK, DEF, HP, FP, LVL);
        return uniqueInstance;
    }

    @Override
    public boolean canAttack(Enemy enemy) {
        return enemy.canBeAttackedByTom();
    }

    @Override
    public boolean canBeAttackedBy(Enemy enemy) {
        return enemy.canAttackTom();
    }

}
