package com.tomandmax.attacks;

import java.util.Hashtable;

/**
 * Class that represents all the attacks that the main character can do
 * These attacks are in a group, that can be accessed by methods
 * @author Felipe Escárate
 */
public class AttackGroup {
    public Hashtable<String, Attack> attacks;

    /**
     * Creates a group of attacks
     */
    public AttackGroup(){
        Salto salto = new Salto();
        Martillo martillo = new Martillo();
        attacks = new Hashtable<>();
        attacks.put("Salto", salto);
        attacks.put("Martillo", martillo);
    }

    /**
     * Gets the Attack by its name
     * @param key   is the Attack's name
     * @return      the Attack
     */
    public Attack get(String key){
        return attacks.get(key);
    }

    /**
     * Gives the hashtable with the attacks and their names
     * @return a hashtable with the attacks names as key and the attacks itself as values
     */
    public Hashtable<String, Attack> getAttacks(){
        return attacks;
    }


}
