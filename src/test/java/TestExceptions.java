import com.controller.phases.AttackSelectionPhase;
import com.controller.phases.EnemySelectionPhase;
import com.controller.phases.ItemSelectionPhase;
import com.controller.phases.MCSelectionPhase;
import com.tomandmax.attacks.AttackGroup;
import com.tomandmax.enemies.Ghost;
import com.tomandmax.enemies.Enemy;
import com.tomandmax.mainCharacters.Max;
import com.controller.GameController;
import com.controller.exceptions.InvalidTransitionException;
import com.controller.exceptions.NullAttackException;
import com.controller.exceptions.NullEnemyException;
import com.controller.exceptions.WrongStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestExceptions {
    GameController controller = new GameController();
    private final NullOutputStream nullOutputStream = new NullOutputStream();
    @Test
    public void testSelectionExceptions(){
        AttackSelectionPhase atkSelectPhase = new AttackSelectionPhase(controller);
        AttackGroup attacks = new AttackGroup();

        //creamos un Max que solo tiene 1 Fight Point
        Max max = Max.create(10,10,10,1,10);

        //intentamos seleccionar un el martillo y vemos que no le alcanzan los FP
        NullAttackException atkExcp = Assertions.assertThrows(NullAttackException.class, ()->{
            atkSelectPhase.selectAttack(max, attacks.get("Martillo"));
        });
        Assertions.assertEquals("No tienes suficientes FP para seleccionar ese ataque", atkExcp.getMessage());

        //ahora probamos seleccionar un enemigo,
        //creamos una lista que contiene a Ghost, ya que Max no puede atacarlo
        ArrayList<Enemy> listaGhost = new ArrayList<>();
        listaGhost.add(new Ghost(1,1,1,1));

        EnemySelectionPhase enemySelectPhase = new EnemySelectionPhase(controller);
        NullEnemyException enmyExcp = Assertions.assertThrows(NullEnemyException.class, ()->{
            enemySelectPhase.selectEnemy(max, 0 , listaGhost);
        });
        Assertions.assertEquals("No puedes seleccionar a ese enemigo", enmyExcp.getMessage());

    }

    @Test
    public void testPhaseExceptions(){
        //se espera que cuando esté seleccionando un item se pueda volver a la fase de selección de acción,
        //pero cuando se esté en la fase de selección del MC que usará el item, no se puede volver a la fase de
        // selección de acción

        ItemSelectionPhase itemSelectionPhase = new ItemSelectionPhase(controller);
        itemSelectionPhase.toWaitActionPhase(); //aquí se puede y no tira excepción

        MCSelectionPhase mcSelectionPhase = new MCSelectionPhase(controller, "Martillo");
        InvalidTransitionException invalidTransition =
                Assertions.assertThrows(InvalidTransitionException.class, mcSelectionPhase::toWaitActionPhase);

        Assertions.assertEquals("No puedes hacer esta transición", invalidTransition.getMessage());


        //además, si intento elegir el siguiente turno en la fase de selección de item, debería tirar una excepción

        WrongStateException chooseNextTurn =
                Assertions.assertThrows(WrongStateException.class, itemSelectionPhase::chooseNextTurn);
        Assertions.assertEquals("No se puedes hacer eso ahora", chooseNextTurn.getMessage());

    }

    @Test
    public void testNullEnemyCatch(){
        GameController controller = new GameController();
        controller.setAttackProbSeed(3);
        controller.setEnemiesGenerationSeed(2);
        controller.setEnemyAttackSeed(5);
        controller.setUp();

        controller.tryToWaitForAnAction(nullOutputStream);

        //Turno de Tom
        controller.tryToWaitAttackSelection(nullOutputStream);
        controller.tryToSelectAnAttack("Salto", nullOutputStream);
        controller.tryToSelectAnEnemy(1);
        controller.tryToChooseNextTurn(); //en esta parte se elige de quien será el siguiente turno
        assertTrue(controller.isPlayerTurn()); //y se elige que le toca a los personajes principales

        //Turno de Max
        controller.tryToWaitAttackSelection(nullOutputStream);
        controller.tryToSelectAnAttack("Salto", nullOutputStream);
        //acá intentamos elegir a Ghost
        controller.tryToSelectAnEnemy(0);

        assertEquals("No puedes seleccionar a ese enemigo", controller.getControllerMessage());

    }

}
