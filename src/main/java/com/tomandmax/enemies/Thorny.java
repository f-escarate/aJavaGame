package com.tomandmax.enemies;

import com.tomandmax.mainCharacters.MainCharacter;

/**
 * That class represent an enemy type called Thorny
 * @author Felipe Escárate
 */
public class Thorny extends AbstractImmuneEnemy {
    /**
     * Creates a Thorny
     *
     * @param ATK Thorny's attack points
     * @param DEF Thorny's defense points
     * @param HP  Thorny's health points
     * @param LVL Thorny's level
     */
    public Thorny(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL);
        //we add a random number to the name (to differentiate the enemies)
        super.setName("Thorny");
    }

    @Override
    public boolean isImmuneToSalto() {
        return true;
    }

    @Override
    public boolean isImmuneToMartillo() {
        return false;
    }

    @Override
    public String negativeEffect(MainCharacter mainCharacter) {
        mainCharacter.receiveDamage((int)(mainCharacter.getMaxHP()*0.05));
        mainCharacter.setKO();
        return "Las espinas de Thorny te quitan un 5% de tu vida";
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
        return "Thorny";
    }

}
