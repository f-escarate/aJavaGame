package com.controller;

import com.controller.phases.NextTurnChoosingPhase;
import com.tomandmax.attacks.Attack;
import com.tomandmax.attacks.AttackGroup;
import com.controller.exceptions.InvalidTransitionException;
import com.controller.exceptions.NullAttackException;
import com.controller.exceptions.NullEnemyException;
import com.controller.exceptions.WrongStateException;
import com.controller.phases.Phase;
import com.tomandmax.enemies.Enemy;
import com.tomandmax.items.Chest;
import com.tomandmax.items.Item;
import com.tomandmax.items.ItemQuantity;
import com.tomandmax.mainCharacters.Max;
import com.tomandmax.mainCharacters.MainCharacter;
import com.tomandmax.mainCharacters.Tom;
import javafx.util.Pair;
import org.jetbrains.annotations.Unmodifiable;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Is the controller of the Game
 * Has methods to:
 * Create the Main Characters
 * Create the Enemies
 * Create the items
 * Create the chest where the main characters put out the items
 * Implements the turns
 * Allows a main character to use an item of the chest
 * Obtains the chest's items
 * Obtains all the characters of the turn
 * Erases a character when he's KO
 * Let know when the main characters win or lose
 * Obtains the character that has the current turn
 * Obtains the character that has the next turn
 * Ends the turn of the current character
 */
public class GameController {

    private final AttackGroup attacks;
    private ArrayList<MainCharacter> mcList;
    private ArrayList<Enemy> enemiesList;
    private int currentIndex;
    private Chest chest;
    private final RandomEnemyGenerator enemyGenerator;
    private Phase actualPhase;
    private int attackedEnemyIndex;
    private Item selectedItem;
    private MainCharacter selectedMC;
    private final Random attackProbRand;
    private final Random enemyAttackSelection;
    private int lvl;
    private TurnSelector currentSelector;
    private String controllerMessage;

    /**
     * Creates the game controller
     */
    public GameController(){
        this.enemyGenerator = new RandomEnemyGenerator();
        this.attackProbRand= new Random();
        this.enemyAttackSelection= new Random();
        this.attacks = new AttackGroup();
    }

    /**
     * Sets up initial conditions of the controller
     */
    public void setUp(){
        restartCurrentIndex();
        currentSelector = new MCSelector(this);
        actualPhase = new NextTurnChoosingPhase(this, true);
        mcList = new ArrayList<>();
        enemiesList = new ArrayList<>();
        Tom tom = Tom.create(20, 5, 35, 15, 1);
        Max max = Max.create(25, 5, 30, 15, 1);
        mcList.add(tom);
        mcList.add(max);
        lvl = 1;
        createEnemies(3);
        createChest();
        createItems(3,3);
    }

    /**
     * Creates the enemies and adds them to the list of enemies
     * @param quantity the quantity of the enemies
     */
    public void createEnemies(int quantity){
        enemyGenerator.setUp(lvl);
        for(int i = 0; i < quantity; i++){
            enemiesList.add(enemyGenerator.createRandomEnemy());
        }
    }

    /**
     * Gives the index of the turn owner
     * @return the index of the current turn owner
     */
    public int getCurrentTurn(){
        return currentIndex;
    }

    /**
     * Gives the index of the next turn owner
     * @return the index of the list depending on if is a Main Character, or an Enemy
     */
    public int getNextTurn(){
        if(actualPhase.isPlayerTurn()){
            return (currentIndex + 1)%mcList.size();
        }
        return (currentIndex + 1)%enemiesList.size();
    }

    /**
     * Sets the current turn index in the next turn
     */
    public void endTurn(){
        currentIndex = getNextTurn();
    }

    /**
     * Gets the list of main characters
     * @return the list with the main characters
     */
    public @Unmodifiable List<MainCharacter> getMcList(){
        return List.copyOf(mcList);
    }

    /**
     * Gives the list of enemies
     * @return the list with the enemies
     */
    public @Unmodifiable List<Enemy> getEnemiesList(){
        return List.copyOf(enemiesList);
    }

    /**
     * Gives the name of the main character of the current turn
     * @param index the list index of the main character
     * @return the name of the main character that has that index in the list
     */
    public String getMCName(int index){
        return mcList.get(index).getName();
    }

    /**
     * Gives the name of the enemy of the current turn
     * @param index the list index of the enemy
     * @return the name of the enemy that has that index in the list
     */
    public String getEnemyName(int index){
        return enemiesList.get(index).getName();
    }

    /**
     * Gives the name of the current character depending on if is an enemy or a main character
     * @return the name of the character
     */
    public String getTurnOwnerName(){
        return actualPhase.getOwnerName();
    }

    /**
     * Checks if the main characters were defeated
     * @return true if the list of main characters is empty and false if not
     */
    public boolean mcsDefeated(){
        return mcList.size()==0;
    }

    /**
     * Checks if the enemies were defeated
     * @return true if the list of enemies is empty and false if not
     */
    public boolean enemiesDefeated(){
        return enemiesList.size()==0;
    }

    /**
     * Creates the chest where the items are stored
     * and set it as the chest of the game controller
     */
    void createChest(){
        this.chest = new Chest();
    }

    /**
     * Creates the Healing Potions and FightingPotions
     * @param q1 the quantity of Healing Potions that are created
     * @param q2 the quantity of Fighting Potions that are created
     */
    void createItems(int q1, int q2){
        this.chest.addItems("HealingPotion", q1);
        this.chest.addItems("FightingPotion", q2);
    }

    /**
     * Adds one item to the chest
     * @param itemName the name of the item
     */
    public void addItem(String itemName) {
        chest.addItems(itemName, 1);
    }

    /**
     * Gives the Items of the chest
     * @return a Hashtable with items
     */
    public @Unmodifiable Map<String, Pair<Item, ItemQuantity>> getChestItems(){
        return Map.copyOf(this.chest.getItems());
    }

    /**
     * Gives a hashtable with the attacks
     * @return a hashtable with the attacks names as key and the attacks itself as values
     */
    public @Unmodifiable Map<String, Attack> getAttacks(){
        return Map.copyOf(this.attacks.getAttacks());
    }

    /**
     * Erases a main character that is KO using his index in the list
     * @param index the index of the main character in the list
     */
    public void eraseKOMC(int index) {
        if(mcList.get(index).getKO()){
            mcList.remove(index);
        }
    }

    /**
     * Erases an enemy that is KO using his index in the list
     * @param index the index of the enemy in the list
     */
    public void eraseKOEnemy(int index) {
        if(enemiesList.get(index).getKO()){
            enemiesList.remove(index);
        }
    }

    /**
     * Uses an item in a main character
     * @param item the item that applies the effect
     * @param mc the main character that is affected
     */
    void useAnItem(Item item, MainCharacter mc) {
        item.effect(mc);
    }


    /**
     * Gives the main character that is the turn owner
     * @return the main character that is the turn owner
     */
    public MainCharacter getCurrentMC() {
        return mcList.get(getCurrentTurn());
    }

    /**
     * Tries to give the main character that is the turn owner
     * @return the main character that is the turn owner
     * @param out manages if the output is printed or not
     */
    public MainCharacter tryGetCurrentMC(OutputStream out) {
        try {
            return actualPhase.getCurrentMC();
        } catch (WrongStateException e) {
            e.printStackTrace(new PrintStream(out));
            return null;
        }
    }

    /**
     * Gives the enemy that is the turn owner
     * @return the enemy that is the turn owner
     */
    public Enemy getCurrentEnemy() {
        return enemiesList.get(getCurrentTurn());
    }

    /**
     * Tries to give the enemy that is the turn owner
     * @return the enemy that is the turn owner
     * @param out manages if the output is printed or not
     */
    public Enemy tryGetCurrentEnemy(OutputStream out) {
        try {
            return actualPhase.getCurrentEnemy();
        } catch (WrongStateException e) {
            e.printStackTrace(new PrintStream(out));
            return null;
        }
    }

    /**
     * Change the phase for the another one
     * @param phase the phase that is set
     */
    public void setPhase(Phase phase) {
        actualPhase = phase;
    }

    /**
     * Tries to change the actual phase to a WaitActionPhase
     * @param out manages if the output is printed or not
     */
    public void tryToWaitForAnAction(OutputStream out){
        try {
            actualPhase.toWaitActionPhase();
        } catch (InvalidTransitionException e) {
            e.printStackTrace(new PrintStream(out));
        }
    }

    /**
     * Tries to change the current phase to the AttackSelectionPhase
     * @param out manages if the output is printed or not
     */
    public void tryToWaitAttackSelection(OutputStream out){
        try{
            actualPhase.toAttackSelectionPhase();
            setControllerMessage(null);
        } catch (InvalidTransitionException e) {
            e.printStackTrace(new PrintStream(out));
        }
    }

    /**
     * Tries to set the selected attack to a new attack
     * @param attackName is the name of the selected attack
     * @param out manages if the output is printed or not
     */
    public void tryToSelectAnAttack(String attackName, OutputStream out){
        try{
            actualPhase.selectAttack(getCurrentMC(), attacks.get(attackName));
            setControllerMessage("Se ha seleccionado el " + attackName);
        } catch (InvalidTransitionException e) {
            e.printStackTrace(new PrintStream(out));
        } catch (NullAttackException e) {
            setControllerMessage(e.getMessage());
        }
    }

    /**
     * Tries to set the attacked enemy using its index in the list
     * @param enemyIndex the list index of the enemy that will be attacked
     */
    public void tryToSelectAnEnemy(int enemyIndex){
        try{
            actualPhase.selectEnemy(getCurrentMC(), enemyIndex, enemiesList);
            attackedEnemyIndex = enemyIndex;
            tryToAttackAnEnemy();
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        } catch (NullEnemyException e) {
            setControllerMessage(e.getMessage());
        }
    }

    /**
     * Tries to do an attack and tries to change the current phase by the NextTurnChoosingPhase
     */
    void tryToAttackAnEnemy() {
        try {
            setControllerMessage(attackAnEnemy());
            actualPhase.toNextTurnChoosingPhase();
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attacks the selected enemy using the selected attack
     */
    private String attackAnEnemy() {
        double randDouble = attackProbRand.nextDouble();
        String message = tryGetCurrentMC(System.out).attackSelectedEnemy(randDouble);
        eraseKOEnemy(attackedEnemyIndex);
        return message;
    }

    /**
     * Tries to change the actual phase to the item selection phase.
     * @param out manages if the output is printed or not
     */
    public void tryToWaitItemSelection(OutputStream out){
        try{
            actualPhase.toItemSelectionPhase();
            setControllerMessage(null);
        } catch (InvalidTransitionException e) {
            e.printStackTrace(new PrintStream(out));
        }
    }

    /**
     * Tries to set the selected item to another one, taking out it from the chest.
     * Also tries to change the current phase to a MCSelectionPhase
     * @param itemName the name of the item that is selected
     */
    public void tryToSelectItem(String itemName) {
        try{
            this.selectedItem = chest.takeOutItem(itemName);
            actualPhase.toMCSelectionPhase(itemName);
            setControllerMessage("Se ha sacado un " + itemName + " del baúl");
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            setControllerMessage("No quedan " + itemName +"s");
        }
    }

    /**
     * Tries to set the selectedMC to another one
     * @param mc the Main Character that is selected
     */
    public void tryToSelectMC(MainCharacter mc){
        try {
            actualPhase.toUseItemPhase();
            setControllerMessage(mc.getName() + " ha usado el objeto");
            this.selectedMC = mc;
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to give the effect of the selected item to the selected Main Character
     * and tries to change the current phase to the NextTurnChoosingPhase
     */
    public void tryToUseItem(){
        try {
            actualPhase.toNextTurnChoosingPhase();
            useAnItem(selectedItem, selectedMC);
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to pass the turn or go to the next turn
     * @param out manages if the output is printed or not
     */
    public void tryToGoToNextTurn(OutputStream out){
        try{
            actualPhase.toNextTurnChoosingPhase();
        } catch (InvalidTransitionException e) {
            e.printStackTrace(new PrintStream(out));
        }
    }

    /**
     * Selects the main character that the current enemy will attack
     * @param enemy the enemy who attacks
     * @return the index of the selected main character
     * @throws NullPointerException if the enemy can't attack any of the available main characters
     */
    private int selectMCToAttack(Enemy enemy) throws NullPointerException {
        boolean canAttackMC1 = mcList.get(0).canBeAttackedBy(enemy);
        if(mcList.size()==1){
            if(canAttackMC1){
                return 0;
            }
            throw new NullPointerException(enemy.getName() + " no puede atacar a ningún personaje");
        }
        boolean canAttackMC2 = mcList.get(1).canBeAttackedBy(enemy);

        if(canAttackMC1 && canAttackMC2){
            if(enemyAttackSelection.nextDouble() > 0.5){
                return 0;
            }
            return 1;
        }
        if(canAttackMC1){
            return 0;
        }
        if(canAttackMC2){
            return 1;
        }
        throw new NullPointerException(enemy.getName() + " no puede atacar a ningún personaje");
    }

    /**
     * Tries to do an enemy attack to a Main Character
     */
    public void tryToAttackMC() {
        Enemy currEnemy = tryGetCurrentEnemy(System.out);
        try {
            int mcIndex = selectMCToAttack(currEnemy);
            MainCharacter attackedMC = mcList.get(mcIndex);
            currEnemy.attackMainCharacter(attackedMC);
            eraseKOMC(mcIndex);
            actualPhase.toNextTurnChoosingPhase();
            setControllerMessage(currEnemy.getName() + " ha atacado a " + attackedMC.getName());
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            setControllerMessage(e.getMessage());
            tryToGoToNextTurn(System.out);
        }
    }

    /**
     * Tries to choose the next turn
     */
    public void tryToChooseNextTurn() {
        try {
            actualPhase.chooseNextTurn();
        } catch (WrongStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes de current turn selector
     * That method is called by the NextTurnChoosingPhase when we change the list of characters that we are iterating
     */
    public void changeTurnSelector() {
        if(isPlayerTurn()){
            currentSelector = new MCSelector(this);
            return;
        }
        currentSelector = new EnemySelector(this);
    }

    /**
     * Uses the turn selector to choose between the WaitPhase or the EnemyPhase and goes to the selected phase
     * i.e. selects the next turn depending on if is a MainCharacter or an Enemy
     */
    public void runTurnSelector() {
        currentSelector.select();
    }

    /**
     * Tries to change the current phase to the enemies phase
     */
    void tryToGoToEnemyTurn(){
        try {
            actualPhase.toEnemiesPhase();
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a seed for the attack turn type
     * That is used by the test
     * @param seed the seed that is set
     */
    public void setAttackProbSeed(int seed){
        attackProbRand.setSeed(seed);
    }

    /**
     * Sets a seed for the generation of enemies
     * That is used by the test
     * @param seed the seed that is set
     */
    public void setEnemiesGenerationSeed(int seed){
        enemyGenerator.setSeed(seed);
    }

    /**
     * Sets a seed for the enemies attack selection
     * That is used by the test
     * @param seed the seed that is set
     */
    public void setEnemyAttackSeed(int seed){
        enemyAttackSelection.setSeed(seed);
    }

    /**
     * Increase the level to the main characters and enemies
     * Also adds items to the chest and creates new enemies for the next stage
     */
    public void levelUP(){
        restartCurrentIndex();
        this.lvl += 1;
        for (MainCharacter mainCharacter : mcList) {
            mainCharacter.lvlUp();
            mainCharacter.recoverHPAndFP();
        }
        this.createItems(1,1);
        if(lvl == 2){
            createEnemies(3);
        }
        else if(lvl == 3 || lvl == 4){
            createEnemies(5);
        }
        else{
            createEnemies(6);
        }
        this.currentSelector = new MCSelector(this);
    }

    /**
     * Sets the current index to 0 (is used when is needed that the first MC do the first move)
     */
    public void restartCurrentIndex(){
        this.currentIndex = 0;
    }

    /**
     * Checks if the current turn is the player turn or not
     * @return true if the in the current phase is in a player turn and false if not
     */
    public boolean isPlayerTurn() {
        return actualPhase.isPlayerTurn();
    }

    /**
     * Checks if in the current phase is allowed to select a main character to use an item
     * @return true if is allowed to select a main character to use an item and false if not
     */
    public boolean isSelectingMC() {
        return actualPhase.isSelectingMC();
    }

    /**
     * Checks if in the current phase is allowed to choose the next turn
     * @return true if is allowed and false if not
     */
    public boolean isChoosingNextTurn() {
        return actualPhase.isChoosingNextTurn();
    }

    /**
     * Checks if in the current phase is able to select an action
     * @return true if is allowed and false if not
     */
    public boolean isWaitingForAction() {
        return actualPhase.isWaitingForAction();
    }

    /**
     * Checks if in the current phase is able to select an attack
     * @return true if is allowed and false if not
     */
    public boolean isWaitingForAttack() {
        return actualPhase.isWaitingForAttack();
    }

    /**
     * Checks if in the current phase is able to select an enemy
     * @return true if is allowed and false if not
     */
    public boolean isSelectingEnemy() {
        return actualPhase.isSelectingEnemy();
    }

    /**
     * Checks if the player has won the battle
     * @return true if the player won and false if not
     */
    public boolean won() {
        return actualPhase.won();
    }

    /**
     * Checks if the player has lost the battle
     * @return true if the player won and false if not
     */
    public boolean lost() {
        return actualPhase.lost();
    }

    /**
     * Sets the current message to a new one
     * @param newMessage the new message
     */
    private void setControllerMessage(String newMessage){
        controllerMessage = newMessage;
    }

    /**
     * Gives the current message that the current phase would show to the user
     * @return the current message
     */
    public String getCurrentMessage(){
        return actualPhase.getMessage();
    }

    /**
     * Gives the message that the game controller would show to the user
     * @return the game controller message
     */
    public String getControllerMessage(){
        return controllerMessage;
    }

    /**
     * Gives the current level in the game
     * @return the level
     */
    public int getLvl() {
        return this.lvl;
    }

}
