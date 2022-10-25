package com.tomandmax.attacks;

import com.tomandmax.enemies.ImmuneEnemy;

/**
 * This class represents the hammer attack type
 * @author Felipe Escárate
 */
public class Martillo extends AbstractAttack{

    /**
     * Creates a hammer
     */
    public Martillo() {
        super(2,1.5, 0.75);
    }

    @Override
    public boolean isUselessTo(ImmuneEnemy enemy) {
        return enemy.isImmuneToMartillo();
    }


}
