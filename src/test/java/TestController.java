import com.tomandmax.attacks.Attack;
import com.tomandmax.enemies.Enemy;
import com.tomandmax.items.Item;
import com.tomandmax.items.ItemQuantity;
import com.tomandmax.mainCharacters.MainCharacter;
import com.controller.GameController;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestController {

    private GameController controller;
    private final NullOutputStream nullOutputStream = new NullOutputStream();

    @BeforeEach
    public void setUp(){
        this.controller = new GameController();
        this.controller.setAttackProbSeed(3);
        this.controller.setEnemiesGenerationSeed(2);
        this.controller.setEnemyAttackSeed(5);
        this.controller.setUp();
    }

    @Test
    public void testAGame(){
        MainCharacter tom = controller.getCurrentMC();
        MainCharacter max = controller.getMcList().get(1);
        List<Enemy> enemies = controller.getEnemiesList();
        Enemy ghost = enemies.get(0);
        Enemy slime = enemies.get(1);
        Enemy thorny = enemies.get(2);

        assertEquals("Tom", tom.getName());
        assertEquals("Max", max.getName());

        controller.tryToWaitForAnAction(nullOutputStream);
        assertEquals("Elija entre: atacar, usar un item o pasar el turno", controller.getCurrentMessage());

        //Turno de Tom
        controller.tryToWaitAttackSelection(nullOutputStream);
        assertEquals("Elige un ataque", controller.getCurrentMessage());
        controller.tryToSelectAnAttack("Salto", nullOutputStream);
        controller.tryToSelectAnEnemy(0); // Se selecciona usando el índice 0 (Ghost1)
        assertEquals("Tom le ha infringido 7 de daño a Ghost1", controller.getControllerMessage());
        controller.tryToChooseNextTurn(); //en esta parte se elige de quien será el siguiente turno
        assertTrue(controller.isPlayerTurn()); //y se elige que le toca a los personajes principales

        //Turno de Max
        controller.tryToWaitAttackSelection(nullOutputStream);
        controller.tryToSelectAnAttack("Salto", nullOutputStream);
        controller.tryToSelectAnEnemy(1); // Se selecciona usando el índice 1 (Slime1)
        assertEquals("Max le ha infringido 5 de daño a Slime1", controller.getControllerMessage());
        controller.tryToChooseNextTurn(); //nuevamente se elige de quien será el siguiente turno
        assertFalse(controller.isPlayerTurn()); //pero ahora le toca a los enemigos

        //Turno de Ghost1
        controller.tryToAttackMC();
        assertEquals("Ghost1 ha atacado a Max", controller.getControllerMessage());
        controller.tryToChooseNextTurn();
        //Turno de Slime1
        controller.tryToAttackMC();
        assertEquals("Slime1 ha atacado a Tom", controller.getControllerMessage());
        controller.tryToChooseNextTurn();
        //Turno de Thorny1
        controller.tryToAttackMC();
        assertEquals("Thorny1 ha atacado a Max", controller.getControllerMessage());
        controller.tryToChooseNextTurn();

        //Probamos que bajó la vida de los enemigos tal como se espera
        assertEquals(3, ghost.getHP());
        assertEquals(5, slime.getHP());

        //Turnos de Tom, Max, Thorny1
        controller.tryToWaitAttackSelection(nullOutputStream);
        controller.tryToSelectAnAttack("Salto", nullOutputStream);
        controller.tryToSelectAnEnemy(0); // Se selecciona usando el índice 0 (Ghost1)
        controller.tryToChooseNextTurn();

        assertEquals(0, ghost.getHP()); //después de atacar a ghost este es derrotado
        assertEquals("Slime1", controller.getEnemyName(0));

        controller.tryToWaitAttackSelection(nullOutputStream);
        controller.tryToSelectAnAttack("Salto", nullOutputStream);
        controller.tryToSelectAnEnemy(0); // Se selecciona usando el índice 0 (Slime1)
        controller.tryToChooseNextTurn();

        assertEquals(0, slime.getHP()); //después de atacar a slime este es derrotado

        controller.tryToAttackMC();
        controller.tryToChooseNextTurn();

        //Turnos de Tom, Max, Thorny1
        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToChooseNextTurn();

        controller.tryToWaitAttackSelection(nullOutputStream);
        controller.tryToSelectAnAttack("Martillo", nullOutputStream);
        controller.tryToSelectAnEnemy(0); // Se selecciona usando el índice 0 (Thorny1)
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();
        controller.tryToChooseNextTurn();
        //Turnos de Tom, Max
        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToChooseNextTurn();

        controller.tryToWaitAttackSelection(nullOutputStream);
        controller.tryToSelectAnAttack("Martillo", nullOutputStream);
        controller.tryToSelectAnEnemy(0); // Se selecciona usando el índice 0 (Thorny1)
        controller.tryToChooseNextTurn();

        assertEquals(0, thorny.getHP()); //después de atacar a thorny este es derrotado

        //Como los personajes principales, le ganaron a todos los enemigos
        //Debe pasar que al chequear won() debe entregar true
        assertTrue(controller.won());

        //Como los personajes principales ganan la batalla, internamente
        // se llamó al método levelUP para preparar la siguiente batalla
        controller.tryToGoToNextTurn(nullOutputStream);
        //vemos que luego de esto, se puede es posible seleccionar el siguiente turno
        assertTrue(controller.isChoosingNextTurn());

        assertEquals(2, controller.getLvl());
        controller.tryToWaitForAnAction(nullOutputStream);
        //y al hacer esto, se pasó al turno de los personajes principales
        assertTrue(controller.isPlayerTurn());

        /** ------------------------------Segunda batalla------------------------------------*/


        //Pasamos los turnos hasta que sea seguro que todos los MC fueron derrotados
        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToChooseNextTurn();

        //Vemos que como es el turno del enemigo, no deberíamos poder obtener un currentEnemy
        assertNull(controller.tryGetCurrentEnemy(nullOutputStream));

        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Ghost2 ataca a Max
        controller.tryToChooseNextTurn();

        //Ahora, vemos que como es el turno del enemigo, no deberíamos poder obtener un currentMC
        assertNull(controller.tryGetCurrentMC(nullOutputStream));

        controller.tryToAttackMC();         //Slime2 ataca a Max
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime3 ataca a Tom
        controller.tryToChooseNextTurn();

        //Si chequeamos si el jugador ha perdido o ganado al medio de la batalla, ambas deben
        //entregar falso, porque la batalla sigue en curso
        assertFalse(controller.won());
        assertFalse(controller.lost());
        //Tom consume un item
        controller.tryToWaitItemSelection(nullOutputStream);
        controller.tryToSelectItem("FightingPotion");
        controller.tryToSelectMC(controller.getCurrentMC());
        controller.tryToUseItem();
        controller.tryToChooseNextTurn();

        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Ghost2 ataca a Max
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime2 ataca a Max
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime3 ataca a Tom
        controller.tryToChooseNextTurn();

        //Turno de Tom: Consume un item

        controller.tryToWaitItemSelection(nullOutputStream);
        controller.tryToSelectItem("FightingPotion");
        controller.tryToSelectMC(controller.getCurrentMC());
        controller.tryToUseItem();
        controller.tryToChooseNextTurn();
        assertEquals("Tom", controller.getMCName(0));

        controller.tryToGoToNextTurn(nullOutputStream);
        assertEquals("Max", controller.getTurnOwnerName());
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Ghost2 ataca a Max
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime2 ataca a Max
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime3 ataca a Tom
        controller.tryToChooseNextTurn();

        //Turno de Tom, selecciona item
        controller.tryToWaitItemSelection(nullOutputStream);
        controller.tryToSelectItem("FightingPotion");
        controller.tryToSelectMC(controller.getCurrentMC());
        controller.tryToUseItem();
        controller.tryToChooseNextTurn();

        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Ghost2 ataca a Max y Max es derrotado
        controller.tryToChooseNextTurn();

        assertEquals(0, max.getHP());

        controller.tryToAttackMC();         //Slime2 ataca a Tom
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime3 ataca a Tom
        controller.tryToChooseNextTurn();

        //Como Max fue derrotado, ahora solo juega Tom
        controller.tryToWaitItemSelection(nullOutputStream);
        controller.tryToSelectItem("FightingPotion");
        controller.tryToSelectMC(controller.getCurrentMC());
        controller.tryToUseItem();
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Ghost2 no puede atacar a nadie
        controller.tryToChooseNextTurn();

        assertEquals("Slime2", controller.getTurnOwnerName());
        controller.tryToAttackMC();         //Slime2 ataca a Tom
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime3 ataca a Tom
        controller.tryToChooseNextTurn();

        //Como Max fue derrotado, ahora solo juega Tom
        //Intenta seleccionar un item y no puede porque no quedan
        controller.tryToWaitItemSelection(nullOutputStream);
        controller.tryToSelectItem("FightingPotion");
        assertEquals("No quedan FightingPotions", controller.getControllerMessage());
        controller.tryToWaitForAnAction(nullOutputStream);
        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Ghost2 no puede atacar a nadie
        controller.tryToChooseNextTurn();

        controller.tryToAttackMC();         //Slime2 ataca a Tom y lo derrota
        controller.tryToChooseNextTurn();

        assertEquals(0, tom.getHP());

        //una vez que los dos personajes principales fueron derrotados, al chequear usando lost()
        //este debería entregar true
        assertTrue(controller.lost());

        //Y ahora podemos probar reintentar
        controller.tryToGoToNextTurn(nullOutputStream);
        controller.tryToWaitForAnAction(nullOutputStream);
        max = controller.getMcList().get(1);
        assertNotEquals(0, max.getHP());
        // vemos que como la vida de Max es distinta de 0, es porque está vivo (se volvió a crear)

    }

    @Test
    public void testGetters(){
        Map<String, Pair<Item, ItemQuantity>> items =  controller.getChestItems();
        assertNotNull(items.get("FightingPotion"));
        assertNotNull(items.get("HealingPotion"));

        Map<String, Attack> attacks = controller.getAttacks();
        assertNotNull(attacks.get("Martillo"));
        assertNotNull(attacks.get("Salto"));

    }


}
