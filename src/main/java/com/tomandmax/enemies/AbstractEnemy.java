package com.tomandmax.enemies;

import com.tomandmax.AbstractCharacter;
import com.tomandmax.attacks.Attack;
import com.tomandmax.mainCharacters.MainCharacter;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class that represents an enemy in the game
 * @author Felipe Escárate
 */
public abstract class AbstractEnemy extends AbstractCharacter implements Enemy{

    /**
     * Creates an enemy
     * @param ATK the enemy's attack points
     * @param DEF the enemy's defense points
     * @param HP  the enemy's health points
     * @param LVL the enemy's level
     */
    protected AbstractEnemy(int ATK, int DEF, int HP, int LVL) {
        super(ATK, DEF, HP, LVL);
    }

    public void attackMainCharacter(@NotNull MainCharacter mainCharacter) {
        mainCharacter.receiveEnemyAttack(this.getAtk(), this.getLvl());
    }

    public String receiveMainCharacterAttack(MainCharacter character, int mcAtk, int mcLvl, Attack attack){
        int damage = this.calculateDamage(attack.getK(), mcAtk, mcLvl);
        this.receiveDamage(damage);
        this.setKO();
        return character.getName() + " le ha infringido " + damage + " de daño a " + this.getName();
    }

    public void setNameIndex(int index) {
        this.setName(getName() + index);
    }

}