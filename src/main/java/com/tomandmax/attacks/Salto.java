package com.tomandmax.attacks;

import com.tomandmax.enemies.ImmuneEnemy;

/**
 * This class represents the jump attack type
 * @author Felipe Escárate
 */
public class Salto extends AbstractAttack{

    /**
     * Creates a jump
     */
    public Salto(){
        super(1,1.0, 1.0);
    }

    @Override
    public boolean isUselessTo(ImmuneEnemy enemy) {
        return enemy.isImmuneToSalto();
    }


}
