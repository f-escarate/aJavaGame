package com.controller.phases;

import com.controller.GameController;
import com.controller.exceptions.InvalidTransitionException;
import com.controller.exceptions.NullAttackException;
import com.controller.exceptions.NullEnemyException;
import com.controller.exceptions.WrongStateException;
import com.tomandmax.attacks.Attack;
import com.tomandmax.enemies.Enemy;
import com.tomandmax.mainCharacters.MainCharacter;

import java.util.List;

/**
 * Class that represents a phase in the game, it has subclasses and each one
 * represents one phase in particular
 * @author Felipe Escárate
 */
public class Phase {
    protected final GameController controller;
    private String message;

    /**
     * Creates a Phase
     * @param aController the controller of the phase
     */
    public Phase(GameController aController){
        controller = aController;
    }

    /**
     * Method that allows to change the controller phase
     * @param phase the phase that will be set in the controller
     */
    protected void changePhase(Phase phase){
        controller.setPhase(phase);
    }

    /**
     * Checks if in that phase is turn of the player
     * @return true if is player turn and false if not
     */
    public boolean isPlayerTurn() {
        return true;
    }

    /**
     * Checks if that phase is able to select an action
     * @return true if is able and false if not
     */
    public boolean isWaitingForAction() {
        return false;
    }

    /**
     * Checks if that phase is able to select an attack
     * @return true if is able and false if not
     */
    public boolean isWaitingForAttack() {
        return false;
    }

    /**
     * Checks if that phase is able to select an enemy
     * @return true if is able and false if not
     */
    public boolean isSelectingEnemy() {
        return false;
    }

    /**
     * Checks if in that phase is selecting a main character to use an item of the chest
     * @return true if is selecting a main character and false if not
     */
    public boolean isSelectingMC() {
        return false;
    }

    /**
     * Checks if in that phase choosing the character that will be the next turn owner
     * @return ture if is choosing the next turn and false if not
     */
    public boolean isChoosingNextTurn() {
        return false;
    }

    /**
     * Checks if the player has won a battle
     * @return true if is player has won a battle and false if not
     */
    public boolean won(){
        return false;
    }

    /**
     * Checks if the player has lost a battle
     * @return true if is player has lost a battle and false if not
     */
    public boolean lost(){
        return false;
    }

    /**
     * Throws a InvalidTransitionException
     * @throws InvalidTransitionException if is called
     */
    private void transitionException() throws InvalidTransitionException {
        throw new InvalidTransitionException("No puedes hacer esta transición");
    }

    /**
     * Throws a WrongStateException
     * @throws WrongStateException if is called
     */
    private void wrongStateException() throws WrongStateException {
        throw new WrongStateException("No se puedes hacer eso ahora");
    }

    /**
     * Makes the controller to goes to the NextTurnChoosingPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toNextTurnChoosingPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the WaitActionPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toWaitActionPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the AttackSelectionPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toAttackSelectionPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the EnemySelectionPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toEnemySelectionPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the AttackPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toAttackPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the ItemSelectionPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toItemSelectionPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the MCSelectionPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toMCSelectionPhase(String itemName) throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the UseItemPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toUseItemPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * Makes the controller to goes to the EnemiesPhase
     * @throws InvalidTransitionException if in that phase we can´t do that
     */
    public void toEnemiesPhase() throws InvalidTransitionException {
        transitionException();
    }

    /**
     * That method works only in the NextTurnChoosingPhase
     * Checks if the main characters win or lose
     * If one of those things happens, then, we go to the appropriate phase
     * else we select the next character that will be the turn owner
     */
    public void chooseNextTurn() throws WrongStateException {
        wrongStateException();
    }

    /**
     * Gives the message associated at the phase
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message associated at the phase
     * @param aMessage the message that is set
     */
    protected void setMessage(String aMessage){
        message = aMessage;
    }

    /**
     * Gives the current Main Character
     * @return the current main character (if is the turn owner)
     * @throws WrongStateException if it isn't the player turn
     */
    public MainCharacter getCurrentMC() throws WrongStateException {
        return controller.getCurrentMC();
    }

    /**
     * Gives the current Enemy
     * @return the current enemy (if is the turn owner)
     * @throws WrongStateException if it isn't the enemy turn
     */
    public Enemy getCurrentEnemy() throws WrongStateException {
        throw new WrongStateException("No es turno del enemigo");
    }

    /**
     * Gives the name of the character that is the current turn owner
     * @return the turn owner name
     */
    public String getOwnerName() {
        return controller.getMCName(controller.getCurrentTurn());
    }

    /**
     * Makes the current main character to select an attack
     * @param mc the main character who selects the attack
     * @param attack the attack that is selected
     * @throws InvalidTransitionException if the method cannot be used in that phase
     * @throws NullAttackException if that attack cannot be selected by the main character because he hasn´t too many FP
     */
    public void selectAttack(MainCharacter mc, Attack attack) throws InvalidTransitionException, NullAttackException {
        transitionException();
    }

    /**
     * Makes the current main character to select an enemy to attack
     * @param mc            the main character who selects the enemy
     * @param enemyIndex    the index of the enemy in the enemies list of the controller
     * @param enemiesList   the enemies list of the controller
     * @throws InvalidTransitionException if the method cannot be used in that phase
     * @throws NullEnemyException if the enemy cannot be selected by the main character, because he can't attack it
     */
    public void selectEnemy(MainCharacter mc, int enemyIndex, List<Enemy> enemiesList) throws InvalidTransitionException, NullEnemyException {
        transitionException();
    }
}
