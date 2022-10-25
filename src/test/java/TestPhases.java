import com.controller.phases.Phase;
import com.tomandmax.attacks.Martillo;
import com.tomandmax.enemies.Ghost;
import com.tomandmax.enemies.Enemy;
import com.tomandmax.mainCharacters.Tom;
import com.controller.GameController;
import com.controller.exceptions.InvalidTransitionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPhases {
    private final NullOutputStream nullOutputStream = new NullOutputStream();

    @Test
    public void testBackToPreviousPhase(){
        GameController controller = new GameController();
        controller.setUp();
        assertTrue(controller.isChoosingNextTurn()); //sigue en la misma fase
        controller.tryToWaitForAnAction(nullOutputStream);
        //como inicialmente, estamos en la fase de elegir turno, no deberíamos poder elegir acción
        controller.tryToWaitAttackSelection(nullOutputStream);
        assertTrue(controller.isWaitingForAttack());            //seleccionando ataque

        controller.tryToSelectAnAttack("Salto", nullOutputStream);
        assertTrue(controller.isSelectingEnemy());            //seleccionando enemigo

        //Ahora vemos que podemos devolvernos:
        controller.tryToWaitForAnAction(nullOutputStream);
        assertTrue(controller.isSelectingEnemy());            //si nos intentamos devolver directamente, no podemos
        controller.tryToWaitAttackSelection(nullOutputStream);
        assertTrue(controller.isWaitingForAttack());            //seleccionando ataque

        controller.tryToWaitForAnAction(nullOutputStream);
        assertTrue(controller.isWaitingForAction());            //seleccionando acción

        //Ahora vamos a seleccionar item
        controller.tryToWaitItemSelection(nullOutputStream);

        controller.tryToWaitForAnAction(nullOutputStream); //nos devolvemos
        assertTrue(controller.isWaitingForAction());       //seleccionando acción

        controller.tryToWaitItemSelection(nullOutputStream);
        controller.tryToSelectItem("FightingPotion");
        assertTrue(controller.isSelectingMC());

        controller.tryToWaitItemSelection(nullOutputStream);

    }

    @Test
    public void testOriginalPhase(){
        GameController controller = new GameController();
        Phase phase = new Phase(controller);

        assertFalse(phase.isWaitingForAction());
        assertFalse(phase.isWaitingForAttack());
        assertFalse(phase.isChoosingNextTurn());
        assertFalse(phase.isSelectingEnemy());
        assertFalse(phase.isSelectingMC());
        assertFalse(phase.won());
        assertFalse(phase.lost());

        InvalidTransitionException invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toNextTurnChoosingPhase();
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toAttackSelectionPhase();
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toEnemySelectionPhase();
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toAttackPhase();
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toMCSelectionPhase("FightingPotion");
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toItemSelectionPhase();
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toEnemiesPhase();
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.toUseItemPhase();
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        Tom tom = Tom.create(1,1,1,1,1);

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.selectAttack(tom, new Martillo());
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());

        //creamos una lista que contiene a Ghost, ya que Max no puede atacarlo
        ArrayList<Enemy> listaGhost = new ArrayList<>();
        listaGhost.add(new Ghost(1,1,1,1));

        invalidTransitionException = Assertions.assertThrows(InvalidTransitionException.class, ()->{
            phase.selectEnemy(tom, 0, listaGhost);
        });
        Assertions.assertEquals("No puedes hacer esta transición", invalidTransitionException.getMessage());


    }
}
