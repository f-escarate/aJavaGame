package com.tomandmax.enemies;
/**
 * This class represents an enemy type called Slime
 * @author Felipe Escárate
 */
public class Slime extends AbstractEnemy {
    /**
     * Creates a Slime
     *
     * @param ATK Slime's attack points
     * @param DEF Slime's defense points
     * @param HP  Slime's health points
     * @param LVL Slime's level
     */
    public Slime(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL);
        //we add a random number to the name (to differentiate the enemies)
        super.setName("Slime");
    }

    @Override
    public boolean canBeAttackedByTom() {
        return true;
    }

    @Override
    public boolean canAttackTom() {
        return true;
    }

    @Override
    public boolean canBeAttackedByMax() {
        return true;
    }

    @Override
    public boolean canAttackMax() {
        return true;
    }

    @Override
    public String getBaseName() {
        return "Slime";
    }

}
