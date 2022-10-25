package com.GUI;

import com.tomandmax.mainCharacters.MainCharacter;
import javafx.scene.text.Text;

/**
 * Allows to show the information of a Main Character in the game
 * @author Felipe Escárate
 */
public class MainCharacterInfo extends AbstractCharacterInfo {
    /**
     * Create a MainCharacterInfo
     * @param character the main character that will be showed
     * @param x the position of the group in the horizontal axis
     * @param y the position of the group in the vertical axis
     */
    MainCharacterInfo(MainCharacter character, int x, int y) {
        super(character, x, y);
        setSprite(character.getName()+".png");
        Text fp = new Text();
        fp.setFont(font);
        fp.setLayoutY(65);
        fp.setText("FP: " + character.getFP() + "/" + character.getMaxFP());
        this.getChildren().add(fp);
    }
}
