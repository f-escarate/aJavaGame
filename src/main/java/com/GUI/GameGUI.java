package com.GUI;

import com.controller.GameController;
import com.tomandmax.attacks.Attack;
import com.tomandmax.enemies.Enemy;
import com.tomandmax.items.Item;
import com.tomandmax.items.ItemQuantity;
import com.tomandmax.mainCharacters.MainCharacter;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * The GUI of the game
 * @author Felipe Escárate
 */
public class GameGUI extends Application {
    private GameController controller;
    private final Text bottomTextField = new Text();
    private final Text bottomTextField2 = new Text();
    private final Text topTextField = new Text();
    private static final int xSize = 900;
    private static final int ySize = 700;
    private static final String RESOURCE_PATH = "src/main/resources/";
    private static final Font font = new Font("Tahoma", 20);
    private final ImageView background = new ImageView();
    private final Group root = new Group();
    private final Group currentButtons = new Group();
    private Button retryButton;
    private Button nextLvlButton;
    private Group actionButtons;
    private Group attackButtons;
    private Group enemiesButtons;
    private Group itemButtons;
    private Group mcButtons;
    private Group mcsInfo = new Group();
    private Group enemiesInfo = new Group();
    private static final int BUTTON_PANEL_Y_POS = 580;
    private static final int X_POS = 50;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tom y Max");
        controller = new GameController();
        controller.setUp();

        bottomTextField.setLayoutX(X_POS+10);
        bottomTextField.setLayoutY(670);
        bottomTextField.setFont(font);

        bottomTextField2.setLayoutX(X_POS+10);
        bottomTextField2.setLayoutY(635);
        bottomTextField2.setFont(font);

        topTextField.setLayoutX(X_POS);
        topTextField.setLayoutY(570);
        topTextField.setFont(font);

        Button startButton = createStartButton("Iniciar juego");
        this.retryButton = createStartButton("Reintentar");
        this.nextLvlButton = createStartButton("Pasar al siguiente nivel");
        this.actionButtons = makeActionButtons();
        this.attackButtons = makeAttackButtons();
        root.getChildren().add(background);
        root.getChildren().add(currentButtons);

        prepareBeginScene(startButton);

        startAnimation();

        Scene scene = new Scene(root, xSize, ySize);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds nodes to the root node that is used in the scene
     */
    private void addChildren() {
        root.getChildren().add(topTextField);
        root.getChildren().add(bottomTextField);
        root.getChildren().add(bottomTextField2);
        root.getChildren().add(mcsInfo);
        root.getChildren().add(enemiesInfo);
    }

    /**
     * Removes some nodes of the root node that is used in the scene
     */
    private void removeChildren() {
        root.getChildren().remove(topTextField);
        root.getChildren().remove(bottomTextField);
        root.getChildren().remove(bottomTextField2);
        root.getChildren().remove(mcsInfo);
        root.getChildren().remove(enemiesInfo);
    }

    /**
     * Refreshes the information of the game
     */
    private void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                topTextField.setText("Nivel: " + controller.getLvl()+ " | Dueño del turno: " + controller.getTurnOwnerName());
                bottomTextField.setText(controller.getCurrentMessage());
                bottomTextField2.setText(controller.getControllerMessage());
            }
        };
        timer.start();
    }

    /**
     * Tries to set the background
     * @param aBackgroundName the name of the background file
     */
    private void trySetBackground(String aBackgroundName) {
        try {
            FileInputStream fileStream = new FileInputStream(RESOURCE_PATH + aBackgroundName);
            background.setImage(new Image(fileStream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the buttons that are showed in screen
     * @param buttonGroup the group of buttons that is set
     */
    private void setCurrentButtons(Node buttonGroup){
        currentButtons.getChildren().clear();
        currentButtons.getChildren().add(buttonGroup);
    }

    /**
     * Calls the method makeItemButtons to re-create the items buttons
     */
    private void updateItemButtons(){
        itemButtons = makeItemButtons();
    }

    /**
     * Calls the methods makeEnemiesButtons and makeEnemiesInfo to re-create the
     * things associated to the enemies
     */
    private void updateEnemiesGUI(){
        enemiesButtons = makeEnemiesButtons();
        enemiesInfo = makeEnemiesInfo();
    }

    /**
     * Calls the methods makeMCButtons and makeMCsInfo to re-create the
     * things associated to the main characters
     */
    private void updateMCsGUI(){
        mcButtons = makeMCButtons();
        mcsInfo = makeMCsInfo();
    }

    /**
     * Prepares the beginning scene
     * @param button the button that will be showed in the beginning scene
     */
    private void prepareBeginScene(Button button) {
        updateItemButtons();
        updateMCsGUI();
        updateEnemiesGUI();
        trySetBackground("mainTitle.jpg");
        setCurrentButtons(button);
    }

    /**
     * Creates a button
     * @param name the name of the button
     * @param x the position of the button in the horizontal axis
     * @param y the position of the button in the vertical axis
     * @param ancho the width of the button
     * @return the button
     */
    private Button createButton(String name, int x, int y, int ancho){
        Button button = new Button(name);
        button.setPrefSize(ancho, 25);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

    /**
     * Creates the button that starts a game
     * @param msg the msg that will show the button
     * @return the button
     */
    private Button createStartButton(String msg) {
        Button selectTurnTypeButton = createButton(msg, 375, 300, 150);
        selectTurnTypeButton.setOnAction(event -> { controller.tryToWaitForAnAction(System.out);
            setCurrentButtons(actionButtons);
            trySetBackground("fondo.jpg");
            addChildren();
        });
        return selectTurnTypeButton;
    }

    /**
     * Creates the button that goes to the WaitForActionPhase
     * @return the button
     */
    private Button createSelectTurnTypeButton() {
        Button selectTurnTypeButton = createButton("Back to select turn type", 640, 50, 150);
        selectTurnTypeButton.setOnAction(event -> { controller.tryToWaitForAnAction(System.out);
            setCurrentButtons(actionButtons);
        });
        return selectTurnTypeButton;
    }

    /**
     * Selects the buttons that will be showed in the next turn
     */
    private void selectButtons() {
        controller.tryToChooseNextTurn();
        if(controller.won()){
            controller.tryToGoToNextTurn(System.out);
            removeChildren();
            prepareBeginScene(nextLvlButton);
        }
        else if (controller.lost()){
            controller.tryToGoToNextTurn(System.out);
            removeChildren();
            prepareBeginScene(retryButton);
        }
        else{
            if(controller.isPlayerTurn()){
                setCurrentButtons(actionButtons);
            }
            else{
                setCurrentButtons(makeCurrentEnemyTurnButton());
            }
        }
    }

    /**
     * Makes the buttons that allows to choose an action
     * @return the button group
     */
    private Group makeActionButtons() {
        Button attack = createButton("Atacar", 0, 0, 140);
        attack.setOnAction(event -> { controller.tryToWaitAttackSelection(System.out); setCurrentButtons(attackButtons);});
        Button item = createButton("Usar item", 150, 0, 140);
        item.setOnAction(event -> { controller.tryToWaitItemSelection(System.out); setCurrentButtons(itemButtons);});
        Button pass = createButton("Pasar", 300, 0, 140);
        pass.setOnAction(event -> { controller.tryToGoToNextTurn(System.out); selectButtons();});
        Group actionButtons = new Group();
        actionButtons.getChildren().add(attack);
        actionButtons.getChildren().add(item);
        actionButtons.getChildren().add(pass);
        actionButtons.setLayoutY(BUTTON_PANEL_Y_POS);
        actionButtons.setLayoutX(X_POS);
        return actionButtons;
    }

    /**
     * Makes the buttons that allows to choose an attack
     * @return the button group
     */
    private Group makeAttackButtons() {
        Map<String, Attack> attacks = controller.getAttacks();
        Group attackButtons = new Group();

        int i = 0;
        for(String attackName :attacks.keySet()){
            Button button = createButton(attackName + " (" + attacks.get(attackName).getCostFP() + " FP)", 150*i, 0, 140);
            button.setOnAction(event -> { controller.tryToSelectAnAttack(attackName, System.out); setCurrentButtons(enemiesButtons);});
            attackButtons.getChildren().add(button);
            i++;
        }

        Button back = createSelectTurnTypeButton();
        attackButtons.getChildren().add(back);
        attackButtons.setLayoutY(BUTTON_PANEL_Y_POS);
        attackButtons.setLayoutX(X_POS);
        return attackButtons;
    }

    /**
     * Makes the buttons that allows to choose an enemy
     * @return the button group
     */
    private Group makeEnemiesButtons() {
        List<Enemy> enemies = controller.getEnemiesList();
        Group enemiesButtons = new Group();

        int i = 0;
        for(Enemy enemy: enemies){
            Button button = createButton(enemy.getName(), 110*i, 0, 100);
            int enemyIndex = i;
            button.setOnAction(event -> {
                controller.tryToSelectAnEnemy(enemyIndex);
                if(controller.isChoosingNextTurn()){
                    updateMCsGUI();
                    updateEnemiesGUI();
                    selectButtons();
                }
            });
            enemiesButtons.getChildren().add(button);
            i++;
        }

        Button attack = createButton("Back to attack selection", 640, 50, 150);
        attack.setOnAction(event -> { controller.tryToWaitAttackSelection(System.out); setCurrentButtons(attackButtons);});
        enemiesButtons.getChildren().add(attack);
        enemiesButtons.setLayoutY(BUTTON_PANEL_Y_POS);
        enemiesButtons.setLayoutX(X_POS);
        return enemiesButtons;
    }

    /**
     * Makes the buttons that allow to choose an item
     * @return the button group
     */
    private Group makeItemButtons() {
        Map<String, Pair<Item, ItemQuantity>> items = controller.getChestItems();
        Group itemButtons = new Group();

        int i = 0;
        for(String itemName : items.keySet()){
            int quantity = items.get(itemName).getValue().getQuantity();
            Button button = createButton(itemName + " x" +quantity, 160*i, 0, 150);
            button.setOnAction(event -> {
                controller.tryToSelectItem(itemName);
                if(controller.isSelectingMC()){
                    setCurrentButtons(mcButtons);
                }
            });
            itemButtons.getChildren().add(button);
            i++;
        }
        Button back = createSelectTurnTypeButton();
        itemButtons.getChildren().add(back);
        itemButtons.setLayoutY(BUTTON_PANEL_Y_POS);
        itemButtons.setLayoutX(X_POS);
        return itemButtons;
    }

    /**
     * Makes the buttons that allow to choose the main character that will use the item
     * @return the button group
     */
    private Group makeMCButtons() {
        List<MainCharacter> mainCharacters = controller.getMcList();
        Group mcButtons = new Group();

        int i = 0;
        for(MainCharacter mc: mainCharacters){
            Button button = createButton(mc.getName(), 110*i, 0, 100);
            button.setOnAction(event -> {
                controller.tryToSelectMC(mc);
                controller.tryToUseItem();
                updateMCsGUI();
                updateItemButtons();
                selectButtons();
            });
            mcButtons.getChildren().add(button);
            i++;
        }

        Button item = createButton("Back to Item selection", 640, 50, 150);
        item.setOnAction(event -> { controller.tryToWaitItemSelection(System.out);  setCurrentButtons(itemButtons);});
        mcButtons.getChildren().add(item);
        mcButtons.setLayoutY(BUTTON_PANEL_Y_POS);
        mcButtons.setLayoutX(X_POS);
        return mcButtons;
    }

    /**
     * Make the button that allows to do the current enemy turn
     * @return the button group
     */
    private Group makeCurrentEnemyTurnButton() {
        Enemy enemy = controller.tryGetCurrentEnemy(System.out);
        Group enemyButtonGroup = new Group();

        Button enemyButton = createButton("Ataque de: " + enemy.getName(), 0,0, 150);
        enemyButton.setOnAction(event -> { controller.tryToAttackMC(); updateMCsGUI(); selectButtons(); });
        enemyButtonGroup.getChildren().add(enemyButton);
        enemyButtonGroup.setLayoutY(BUTTON_PANEL_Y_POS);
        enemyButtonGroup.setLayoutX(X_POS);
        return enemyButtonGroup;
    }

    /**
     * Makes a group with all the MainCharacterInfo's
     * @return the group
     */
    private Group makeMCsInfo() {
        List<MainCharacter> mainCharacters = controller.getMcList();
        mcsInfo.getChildren().clear();

        int i = 0;
        for(MainCharacter mc: mainCharacters){
            MainCharacterInfo mcInfo = new MainCharacterInfo(mc, 0, i*80);
            mcsInfo.getChildren().add(mcInfo);
            i++;
        }

        mcsInfo.setLayoutY(150);
        mcsInfo.setLayoutX(X_POS+50);
        return mcsInfo;
    }

    /**
     * Makes a group with all the EnemyInfo's
     * @return the group
     */
    private Group makeEnemiesInfo() {
        List<Enemy> enemies = controller.getEnemiesList();
        enemiesInfo.getChildren().clear();

        int i = 0;
        for(Enemy enemy: enemies){
            EnemyInfo enemyInfo = new EnemyInfo(enemy, 0, i*60);
            enemiesInfo.getChildren().add(enemyInfo);
            i++;
        }

        enemiesInfo.setLayoutY(150);
        enemiesInfo.setLayoutX(X_POS + 400);
        return enemiesInfo;
    }

}
