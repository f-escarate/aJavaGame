import com.tomandmax.attacks.AttackGroup;
import com.tomandmax.enemies.Ghost;
import com.tomandmax.enemies.Slime;
import com.tomandmax.mainCharacters.Max;
import com.tomandmax.mainCharacters.Tom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestAttackTypes {
    private Tom testTom;
    private Max testMax;
    private Slime testSlime;
    private Ghost testGhost;
    private AttackGroup attacks;
    private Random rand;

    @BeforeEach
    public void setUp() {
        testTom = Tom.create(1, 1, 30, 2000, 2);
        testMax = Max.create(1, 1, 30, 2000, 3);
        testSlime = new Slime(20, 3, 1000, 1);
        testGhost = new Ghost(20, 3, 100, 1);
        this.rand = new Random();
        rand.setSeed(3);
        attacks = new AttackGroup();
    }

    @Test
    public void testHammerProbability() {
        /*
        Los stats están hechos de tal forma que cuando Tom
        usa el martillo para atacar, hace a solo 1 de daño a Slime
         */

        /*
        Hacemos 1000 ataques con martillo a Slime para ver
        cuantos de estos ataques son acertados
         */
        for(int i = 0; i < 1000; i++){
            double randDouble = rand.nextDouble();
            testTom.selectAnAttack(attacks.get("Martillo"));
            testTom.selectAnEnemy(testSlime);
            testTom.attackSelectedEnemy(randDouble);
        }

        /*
        Con esto podemos obtener la proporción entre ataques acertados y totales
         */
        double ratio = (1000 - testSlime.getHP())/1000.0;

        /*
        Comprobamos que el ratio de ataques sea similar a la probabilidad
        de acertar un ataque con martillo.
        Tomamos en cuenta un error del 3.5%.
         */
        assertEquals(0.75, ratio, 0.035);

    }

    @Test
    public void testGhostImmunityToHammer() {
        //atacamos a Ghost 100 veces y vemos que su vida no baja
        assertEquals(100, testGhost.getHP());
        for(int i = 0; i < 100; i++){
            double randDouble = rand.nextDouble();
            testTom.selectAnAttack(attacks.get("Martillo"));
            testTom.selectAnEnemy(testGhost);
            testTom.attackSelectedEnemy(randDouble);
        }
        assertEquals(100, testGhost.getHP());

    }

    @Test
    public void testJumpProbability() {
        //Atacamos con salto 1000 veces y vemos que todos los golpes son acertados
        //Notamos que Max le hace 1 de daño a Slime por cada salto.
        assertEquals(1000, testSlime.getHP());
        for(int i = 0; i < 1000; i++){
            double randDouble = rand.nextDouble();
            testMax.selectAnAttack(attacks.get("Martillo"));
            testMax.selectAnEnemy(testSlime);
            testMax.attackSelectedEnemy(randDouble);
        }
        assertEquals(0, testSlime.getHP());
    }

}
