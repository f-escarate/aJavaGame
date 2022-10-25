package com.tomandmax.items;

import com.tomandmax.mainCharacters.MainCharacter;

/**
 * Class that represents the Fighting Potion item
 * This item gives 3 Fight Points (FP) to a main character
 * @author Felipe Escárate
 */
public class FightingPotion implements Item{

    @Override
    public void effect(MainCharacter mainCharacter) {
        mainCharacter.fightingEffect();
    }

}
