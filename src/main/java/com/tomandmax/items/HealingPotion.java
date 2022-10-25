package com.tomandmax.items;

import com.tomandmax.mainCharacters.MainCharacter;

/**
 * Class that represents the Healing Potion item
 * When a main character uses this item, recovers 10% of his HP
 * @author Felipe Escárate
 */
public class HealingPotion implements Item{

    @Override
    public void effect(MainCharacter mainCharacter){
        mainCharacter.healingEffect();
    }

}
