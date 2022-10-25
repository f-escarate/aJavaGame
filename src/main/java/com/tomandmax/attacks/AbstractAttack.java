package com.tomandmax.attacks;

/**
 * Abstract class that represents an attack (type) that a main character can do
 * @author Felipe Escárate
 */
public abstract class AbstractAttack implements Attack{

    private final int costFP;
    private final double k;
    private final double hitProbability;

    /**
     * Creates an attack (type)
     * @param costoFP               is the attack type cost (to main character) in fight points
     * @param constante             is the constant K to calculate the damage taken
     * @param probabilidadAcierto   is the probability to hit the attack
     */
    protected AbstractAttack(int costoFP, double constante, double probabilidadAcierto) {
        this.costFP = costoFP;
        this.k = constante;
        this.hitProbability = probabilidadAcierto;
    }

    public int getCostFP(){
        return this.costFP;
    }

    public double getK(){
        return this.k;
    }

    public double getHitProbability(){
        return this.hitProbability;
    }


}
