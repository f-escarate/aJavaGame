import com.tomandmax.attacks.Martillo;
import com.tomandmax.attacks.Salto;
import com.tomandmax.enemies.Ghost;
import com.tomandmax.enemies.Slime;
import com.tomandmax.enemies.Thorny;
import com.tomandmax.items.Chest;
import com.tomandmax.mainCharacters.Max;
import com.tomandmax.mainCharacters.Tom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnemy {

    private Slime testSlime;
    private Ghost testGhost;
    private Thorny testThorny;

    private final Chest items = new Chest();
    private Tom testTom;
    private Max testMax;
    private final Salto salto = new Salto();
    private final Martillo martillo = new Martillo();

    @BeforeEach
    public void setUp() {
        testSlime = new Slime(2,4,15,8);
        testGhost = new Ghost(5,9,3,8);
        testThorny = new Thorny(5,9,3,8);

        testTom = Tom.create(10, 2, 30, 10, 2);
        testMax = Max.create(15, 1, 20, 15, 2);

    }

    @Test
    public void canAttackTest(){

        //Vemos quién puede atacar a quién
        assertTrue(testTom.canAttack(testGhost));
        assertTrue(testTom.canAttack(testSlime));
        assertTrue(testTom.canAttack(testThorny));

        assertFalse(testMax.canAttack(testGhost));
        assertTrue(testMax.canAttack(testSlime));
        assertTrue(testMax.canAttack(testThorny));


        assertFalse(testTom.canBeAttackedBy(testGhost));
        assertTrue(testTom.canBeAttackedBy(testSlime));
        assertTrue(testTom.canBeAttackedBy(testThorny));

        assertTrue(testMax.canBeAttackedBy(testGhost));
        assertTrue(testMax.canBeAttackedBy(testSlime));
        assertTrue(testMax.canBeAttackedBy(testThorny));
    }

    @Test
    public void testNotImmune() {
        int ghostHp = testGhost.getHP();
        int thornyHp = testThorny.getHP();

        //los atacamos y vemos que les baja la vida
        // (por lo tanto no son inmunes al tipo de ataque)
        testTom.selectAnAttack(salto);
        testTom.selectAnEnemy(testGhost);
        testTom.attackSelectedEnemy(0);
        testTom.selectAnAttack(martillo);
        testTom.selectAnEnemy(testThorny);
        testTom.attackSelectedEnemy(0);

        assertNotEquals(ghostHp, testGhost.getHP());
        assertNotEquals(thornyHp, testThorny.getHP());

    }
}
