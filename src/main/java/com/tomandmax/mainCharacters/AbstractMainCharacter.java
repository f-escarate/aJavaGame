package com.tomandmax.mainCharacters;

import com.tomandmax.AbstractCharacter;
import com.tomandmax.attacks.Attack;
import com.tomandmax.enemies.Enemy;

/**
 * Class that represents a main character in the game
 * i.e. Tom or Max
 * @author Felipe Escárate
 */

public abstract class AbstractMainCharacter extends AbstractCharacter implements MainCharacter {
    private int fp;
    private int maxFP;
    private Attack selectedAttack;
    private Enemy selectedEnemy;

    /**
     * Creates a new main character
     * @param ATK           main character's attack points
     * @param DEF           main character's defense points
     * @param HP            main character's health points
     * @param FP            main character's fight points
     * @param LVL           main character's level
     */
    protected AbstractMainCharacter(int ATK, int DEF, int HP, int FP, int LVL){
        super(ATK, DEF, HP, LVL);
        fp = FP;
        maxFP = FP;
    }

    public int getFP(){
        return this.fp;
    }

    /**
     * Sets/change the actual fp to a new valor
     * @param newFP the new valor of fp
     */
    private void setFP(int newFP){
        this.fp = newFP;
    }

    public int getMaxFP(){
        return maxFP;
    }

    /**
     * Change the maximum fighting points value
     * @param newMaxFP the new maxFP value
     */
    private void setMaxFP(int newMaxFP){
        maxFP = newMaxFP;
    }

    public void lvlUp(){
        this.setMaxHP((int) Math.round(this.getMaxHP()*1.15));
        this.setMaxFP((int) Math.round(this.getMaxFP()*1.15));
        this.setAtk((int) Math.round(this.getAtk()*1.15));
        this.setDef((int) Math.round(this.getDef()*1.15));
        this.lvl += 1;
    }

    /**
     * Recovers the main character's health giving him some health points
     * (considering that the new HP can't be greater than the max HP)
     * @param hpAdded are the health points that are added to the main character
     */
    protected void recoverHP(int hpAdded) {
        int newHP = Math.min(this.getHP() + hpAdded, this.getMaxHP());
        this.setHP(newHP);
    }

    /**
     * Recovers the fight points of the main character in a determined quantity
     * (considering that the new HP can´t be greater than the max HP)
     * @param fpAdded are the fight points to be recovered
     */
    private void recoverFP(int fpAdded){
        int newFP = Math.min(this.getFP() + fpAdded, this.getMaxFP());
        this.setFP(newFP);
    }

    public boolean selectAnAttack(Attack atk){
        if(getFP() < atk.getCostFP()){
            return false;
        }
        this.selectedAttack = atk;
        return true;
    }

    public boolean selectAnEnemy(Enemy enemy){
        if(this.canAttack(enemy)){
            this.selectedEnemy = enemy;
            return true;
        }
        return false;
    }

    /**
     * Makes null the selected attack and enemy, in order to deselect them
     */
    private void deselectAttackAndEnemy(){
        this.selectedAttack = null;
        this.selectedEnemy = null;
    }

    /**
     * Does a successfully attack from the main character to an enemy. (considering the FP consuming)
     * If the enemy is immune to the attack, it makes a negative effect to the main character.
     * Else, the main character can attack to the enemy and deal damage to it.
     * @param enemy the enemy to attack
     * @param attack is the attack type
     * @return a message that represents what happens with the attack that the main character did to the enemy
     */
    private String attackEnemySuccessfully(Enemy enemy, Attack attack){
        this.setFP(this.getFP()-attack.getCostFP());
        return enemy.receiveMainCharacterAttack(this, this.getAtk() ,this.getLvl() , attack);
    }

    public String attackSelectedEnemy(double randDouble) {
        if (randDouble < selectedAttack.getHitProbability()){
            String message = this.attackEnemySuccessfully(selectedEnemy, selectedAttack);
            deselectAttackAndEnemy();
            return message;
        }
        else{
            deselectAttackAndEnemy();
            return getName() + " ha fallado el ataque debido a la probabilidad";
        }
    }

    public void receiveEnemyAttack(int atk, int lvl){
        int damage = calculateDamage(0.75, atk, lvl);
        super.receiveDamage(damage);
        this.setKO();
    }

    public void healingEffect(){
        this.recoverHP((int)(this.getMaxHP() * 0.1));
    }

    public void fightingEffect(){
        this.recoverFP(3);
    }

    public void recoverHPAndFP(){
        setHP(getMaxHP());
        setFP(getMaxFP());
    }

}
