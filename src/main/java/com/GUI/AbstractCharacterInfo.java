package com.GUI;

import com.tomandmax.Character;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A JavaFX Group subclass that allows to show the info of the characters in the game
 * @author Felipe Escárate
 */
public abstract class AbstractCharacterInfo extends Group {
    private final ImageView sprite = new ImageView();
    protected static final Font font = new Font("Verdana", 16);
    private static final String SPRITES_PATH = "src/main/resources/sprites/";

    /**
     * Creates a AbstractCharacterInfo
     * @param character the character that will be showed
     * @param x the position of the group in the horizontal axis
     * @param y the position of the group in the vertical axis
     */
    AbstractCharacterInfo(Character character, int x, int y) {
        super();
        Text name = new Text();
        name.setText(character.getName());
        Text hp = new Text();
        hp.setText("HP: " + character.getHP() + "/" + character.getMaxHP());
        name.setLayoutY(25);
        name.setFont(font);
        hp.setLayoutY(45);
        hp.setFont(font);
        this.getChildren().add(name);
        this.getChildren().add(hp);
        this.getChildren().add(sprite);
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    /**
     * Sets the sprite of the group
     * @param spriteName the name of the sprite in the folder
     */
    protected void setSprite(String spriteName){
        FileInputStream fileStream = tryToOpen(spriteName);
        assert fileStream != null;
        sprite.setImage(new Image(fileStream));
        sprite.setLayoutX(85);
    }

    /**
     * Tries to open the sprite
     * @param spriteName the name of the sprite
     * @return a FileInputStream of the sprite and returns null if there isn't a sprite with that name
     */
    private FileInputStream tryToOpen(String spriteName) {
        try {
            return new FileInputStream(SPRITES_PATH + spriteName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
