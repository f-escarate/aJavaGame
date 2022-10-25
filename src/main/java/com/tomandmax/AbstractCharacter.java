package com.tomandmax;

import java.util.Objects;

/**
 * Class that has attributes and methods for the characters of the game:
 * i.e. Main Characters (Tom, Max) or Enemies
 * @author Felipe Escárate
 */
public abstract class AbstractCharacter implements Character {
    private int atk;
    private int def;
    private int hp;
    private int maxHP;
    protected int lvl;
    private boolean isKO = false;
    private String name;

    /**
     * Creates a new character
     * @param ATK   attack points
     * @param DEF   defense points
     * @param HP    health points
     * @param LVL   character's level
     *
     */
    protected AbstractCharacter(int ATK, int DEF, int HP, int LVL){
        atk=ATK;
        def=DEF;
        hp=HP;
        maxHP = HP;
        lvl=LVL;
    }

    public String getName() {
        return name;
    }

    /**
     * It receives a name when a character is created
     * That name identifies the character
     * @param aName the character tag
     */
    protected void setName(String aName){
        name = aName;
    }

    /**
     * Gets the atk
     * @return the atk
     */
    protected int getAtk(){
        return this.atk;
    }

    /**
     * Gets the def
     * @return the def
     */
    protected int getDef(){
        return this.def;
    }

    public int getHP(){
        return this.hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    /**
     * Change the max HP valor
     * @param newMaxHP the new valor of maximum HP
     */
    protected void setMaxHP(int newMaxHP){
        this.maxHP = newMaxHP;
    }

    /**
     * Gets the character's level
     * @return the character's level
     */
    protected int getLvl(){
        return this.lvl;
    }

    /**
     * Sets a new character attack
     * @param newAtk the new attack that is set
     */
    protected void setAtk(int newAtk){
        this.atk = newAtk;
    }

    /**
     * Sets a new character defense
     * (It may be used when a main character levels up or when
     * an enemy is under an effect of reduce defense (it may be implemented in the future))
     * @param newDef the new defense that is set
     */
    protected void setDef(int newDef){
        this.def = newDef;
    }

    /**
     * Sets a new hp
     * @param newHP the new health points of the character
     */
    protected void setHP(int newHP){
        this.hp = newHP;
    }


    public void setKO(){
        if (this.getHP()==0){
            setAtk(0);
            isKO = true;
        }
    }

    public boolean getKO(){
        return isKO;
    }


    public void receiveDamage(int damage) {
        setHP(Math.max(getHP()-damage, 0));
    }

    /**
     * Calculates the damage that the character receives
     * and returns it
     * @param k is the constant used to calculate the damage
     * @param atk are the attack points of the character who attacks
     * @param lvl is the level of the character who attacks
     * @return the damage
     */
    protected int calculateDamage(double k, int atk, int lvl){
        return (int)Math.round(k*(double)atk*(((double)lvl/(double)this.getDef())));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCharacter that = (AbstractCharacter) o;
        return atk == that.atk && def == that.def && hp == that.hp && maxHP == that.maxHP && lvl == that.lvl && isKO == that.isKO && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atk, def, hp, maxHP, lvl, isKO, name);
    }
}
