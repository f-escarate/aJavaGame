import com.tomandmax.attacks.Martillo;
import com.tomandmax.attacks.Salto;
import com.tomandmax.enemies.Ghost;
import com.tomandmax.enemies.Slime;
import com.tomandmax.enemies.Thorny;
import com.tomandmax.items.Chest;
import com.tomandmax.mainCharacters.Tom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestMainCharacter {
    private Chest items = new Chest();
    private Tom testTom;
    private Thorny testThorny;
    private Slime testSlime;
    private Ghost testGhost;
    private final Salto salto = new Salto();
    private final Martillo martillo = new Martillo();

    @BeforeEach
    public void setUp() {
        testTom = Tom.create(10, 3, 30, 10, 1);
        items.addItems("HealingPotion", 1);
        items.addItems("FightingPotion", 1);
        testThorny = new Thorny(2, 1, 15, 4);
        testSlime = new Slime(20, 1, 30, 3);
        testGhost = new Ghost(21, 1, 30, 1);
    }


    @Test
    public void testPlayerItems() {
        //Primero probamos el hongo
        assertEquals(30, testTom.getHP());
        //le quitamos 2 de vida a Tom
        testThorny.attackMainCharacter(testTom);
        assertEquals(28, testTom.getHP());
        //el usar el hongo, le restauraría 10% de su vida máxima (3)
        (items.takeOutItem("HealingPotion")).effect(testTom);

        //vemos que la vida debe quedar en 30 y no en 31
        assertEquals(testTom.getMaxHP(), testTom.getHP());


        //Ahora probamos el jarabe de miel
        assertEquals(10, testTom.getFP());
        //luego, si bajamos sus puntos de pelea al atacar
        testTom.selectAnAttack(martillo);
        testTom.selectAnEnemy(testThorny);
        testTom.attackSelectedEnemy(0);
        assertEquals(8, testTom.getFP());
        //con lo cual si usamos el objeto, se recuperarían 3 FP.
        //pero como solo se perdieron 2, solo se restaurarían esos.
        (items.takeOutItem("FightingPotion")).effect(testTom);
        assertEquals(10, testTom.getFP());

    }

    @Test
    public void testNegativeEffects(){
        //ahora vemos que los jugadores reciban los efectos adversos de los enemigos
        //Primero vemos con Ghost
        assertEquals(30, testGhost.getHP());
        testTom.selectAnAttack(martillo);
        testTom.selectAnEnemy(testGhost);
        testTom.attackSelectedEnemy(0);
        //Ghost esquiva los ataques con Martillo, por lo que su vida debería seguir igual
        assertEquals(30, testGhost.getHP());

        //Ahora vemos a Thorny
        testTom.selectAnAttack(salto);
        testTom.selectAnEnemy(testThorny);
        testTom.attackSelectedEnemy(0);
        //Thorny hace daño basado en un 5% de la vida máxima, esto es 30*0.05 = 1.5 = 1
        //Entonces Tom quedaría con 30 - 1 = 29 de vida
        assertEquals(29, testTom.getHP());

    }

    @Test
    public void testFightPointsConsume() {
        //Vemos que al inicio, Tom no ha atacado, por lo que tendría todos sus FP
        assertEquals(10, testTom.getFP());

        //luego, si usamos SALTO para atacar a Slime, se consume 1 FP
        testTom.selectAnAttack(salto);
        testTom.selectAnEnemy(testSlime);
        testTom.attackSelectedEnemy(0);
        assertEquals(9, testTom.getFP());

        //Además, si usamos MARTILLO para atacar a Slime, se consume 2 FP
        testTom.selectAnAttack(martillo);
        testTom.selectAnEnemy(testSlime);
        testTom.attackSelectedEnemy(0);
        assertEquals(7, testTom.getFP());
    }

    @Test
    public void testKnockOut(){
        assertEquals(30, testTom.getHP());
        //Tom inicialmente, no está K.O. por lo que el check debería retornar false
        assertFalse(testTom.getKO());
        //hacemos que Slime ataque a Tom para ver como baja su vida
        /*Notamos que debido a los stats de Tom y Slime, son necesarios
          2 ataques de Slime para dejar K.O. a Tom */

        //1er ataque
        testSlime.attackMainCharacter(testTom);
        assertEquals(15, testTom.getHP());
        //En este punto, Tom aún no está K.O.
        assertFalse(testTom.getKO());

        //2do ataque
        testSlime.attackMainCharacter(testTom);
        assertEquals(0, testTom.getHP());
        //Acá, como su vida ya es 0, Tom quedaría K.O.
        assertTrue(testTom.getKO());

    }

    @Test
    public void testLvlUpAndRecover() {
        //inicialmente el ataque es 10, la defensa es 3
        // la vida es 30, los puntos de pelea son 10 y el nivel es 1
        testTom.lvlUp();
        //si subimos el nivel, el ataque sería 12, la defensa 3
        //la vida 35, los puntos de pelea 12 y el nivel 2

        assertEquals(35, testTom.getMaxHP());
        assertEquals(12, testTom.getMaxFP());

        //Como no podemos probar los demás atributos con getters (porque son privados)
        //lo probamos usando otros métodos

        Slime slimeDummy = new Slime(20, 2, 30, 3);

        //si Tom ataca a Slime con salto, este le debería hacer 12 de daño
            //con lo cual, su vida quedaría en 18
        testTom.selectAnAttack(salto);
        testTom.selectAnEnemy(slimeDummy);
        testTom.attackSelectedEnemy(0);
        assertEquals(18, slimeDummy.getHP());
        //con esto se testeó indirectamente, el ataque y nivel de Tom

        //si Slime ataca a Tom, este le debería hacer 15 de daño
        slimeDummy.attackMainCharacter(testTom);
        //con lo cual la vida de Tom sería 30 -15 = 20
        assertEquals(15, testTom.getHP());
        //con esto se testeó indirectamente, la defensa de Tom

        //ahora vemos que pasa si recuperamos sus stats
        testTom.recoverHPAndFP();
        assertEquals(35, testTom.getHP());
        assertEquals(12, testTom.getFP());

    }

}
