package com.tomandmax;

/**
 * Interface that represents a Character in the game
 * i.e. (a MainCharacter or an Enemy)
 */
public interface Character {

    /**
     * Gets the health points
     * @return the health points
     */
    int getHP();

    /**
     * Gets the maximum health points of the main character
     * @return maxHP
     */
    int getMaxHP();

    /**
     * Check if the character hp is lower than zero
     * if that happens, the character was defeated, and we set isKO to true
     */
    void setKO();

    /**
     * Subtract the damage taken, from the character's health points
     * @param damage is the damage taken
     */
    void receiveDamage(int damage);

    /**
     * Check if the character is alive or not
     * @return true if character is K.O. and false if is alive
     */
    boolean getKO();

    /**
     * Gives the character's name
     * @return the character's name
     */
    String getName();

}
