package com.controller;

import com.tomandmax.enemies.Ghost;
import com.tomandmax.enemies.Enemy;
import com.tomandmax.enemies.Slime;
import com.tomandmax.enemies.Thorny;

import java.util.Random;

/**
 * Class that generates enemies randomly
 * The enemies use an index to be differentiated of the other enemies of the same type
 * @author Felipe Escárate
 */
class RandomEnemyGenerator {
    private int lvl;
    private long lvlCoef;
    private int slimeIndex = 1;
    private int ghostIndex = 1;
    private int thornyIndex = 1;
    private final Random rand;

    /**
     * Creates random enemy generator
     */
    RandomEnemyGenerator(){
        this.rand = new Random();
    }

    /**
     * Sets up the random enemy generator
     * Sets the level of the enemies, the coefficient that scale the enemies stats
     * and the enemyTurnMaker
     * @param lvl               is the level that the enemies will have
     */
    void setUp(int lvl){
        this.lvl = lvl;
        this.lvlCoef = Math.round(Math.pow(1.15, lvl-1));
    }

    /**
     * Sets a seed to a random attribute
     * That is used by the tests
     * @param seed the seed that is set
     */
    void setSeed(int seed){
        rand.setSeed(seed);
    }

    /**
     * Method that creates an enemy randomly
     * Uses a random int to select the enemy type
     * @return the enemy
     */
    Enemy createRandomEnemy(){
        int randomInt = rand.nextInt(3);
        if(randomInt == 0){
            return newSlime(lvl, lvlCoef);
        }
        else if(randomInt ==1){
            return newGhost(lvl, lvlCoef);
        }
        else{
            return newThorny(lvl, lvlCoef);
        }
    }

    /**
     * Creates a Slime enemy with stats based in its level
     * @param lvl       the level of the slime enemy
     * @param lvlCoef   the coefficient that scales with the level and multiplies the stats
     * @return the Slime
     */
    private Slime newSlime(int lvl, double lvlCoef){
        int atk = (int) (20* lvlCoef);
        int def = (int) (5* lvlCoef);
        int hp = (int) (10* lvlCoef);
        Slime slime = new Slime(atk, def, hp, lvl);
        slime.setNameIndex(slimeIndex);
        slimeIndex +=1;
        return slime;
    }

    /**
     * Creates a Ghost enemy with stats based in its level
     * @param lvl       the level of the ghost enemy
     * @param lvlCoef   the coefficient that scales with the level and multiplies the stats
     * @return the Ghost
     */
    private Ghost newGhost(int lvl, double lvlCoef){
        int atk = (int) (25* lvlCoef);
        int def = (int) (3* lvlCoef);
        int hp = (int) (10* lvlCoef);
        Ghost ghost = new Ghost(atk, def, hp, lvl);
        ghost.setNameIndex(ghostIndex);
        ghostIndex +=1;
        return ghost;
    }

    /**
     * Creates a Thorny enemy with stats based in its level
     * @param lvl       the level of the thorny enemy
     * @param lvlCoef   the coefficient that scales with the level and multiplies the stats
     * @return the Thorny
     */
    private Thorny newThorny(int lvl, double lvlCoef){
        int atk = (int) (15* lvlCoef);
        int def = (int) (6* lvlCoef);
        int hp = (int) (12* lvlCoef);
        Thorny thorny = new Thorny(atk, def, hp, lvl);
        thorny.setNameIndex(thornyIndex);
        thornyIndex +=1;
        return thorny;
    }
}
