package com.GUI;

import com.tomandmax.enemies.Enemy;

/**
 * Class that shows the enemy info
 * @author Felipe Escárate
 */
public class EnemyInfo extends AbstractCharacterInfo{
    /**
     * Creates a EnemyInfo
     * @param enemy the enemy that will be showed
     * @param x the position of the group in the horizontal axis
     * @param y the position of the group in the vertical axis
     */
    EnemyInfo(Enemy enemy, int x, int y) {
        super(enemy, x, y);
        setSprite(enemy.getBaseName()+".png");
    }
}
