package com.tomandmax.enemies;

import com.tomandmax.mainCharacters.MainCharacter;

/**
 * This class represents an enemy type called Ghost
 * @author Felipe Escárate
 */
public class Ghost extends AbstractImmuneEnemy {
    /**
     * Creates a Ghost
     *
     * @param ATK Ghost's attack points
     * @param DEF Ghost's defense points
     * @param HP  Ghost's health points
     * @param LVL Ghost's level
     */
    public Ghost(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL);
        //we add a random number to the name (to differentiate the enemies)
        super.setName("Ghost");
    }

    @Override
    public boolean isImmuneToSalto() {
        return false;
    }

    @Override
    public boolean isImmuneToMartillo() {
        return true;
    }

    @Override
    public String negativeEffect(MainCharacter mainCharacter) {
        return "Ghost esquivó el ataque";
    }

    @Override
    public boolean canBeAttackedByTom() {
        return true;
    }

    @Override
    public boolean canAttackTom() {
        return false;
    }

    @Override
    public boolean canBeAttackedByMax() {
        return false;
    }

    @Override
    public boolean canAttackMax() {
        return true;
    }

    @Override
    public String getBaseName() {
        return "Ghost";
    }

}
