package com.tomandmax.items;

import com.tomandmax.mainCharacters.MainCharacter;

/**
 * Interface that represents the items that the main character can consume
 * Every item has an effect associated
 * @author Felipe Escárate
 */
public interface Item {
    /**
     * It applies the item effect to the main character
     * @param mainCharacter the main character to apply the effect
     *
     */
    void effect(MainCharacter mainCharacter);

}
