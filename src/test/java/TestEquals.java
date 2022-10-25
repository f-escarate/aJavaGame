import com.tomandmax.enemies.Ghost;
import com.tomandmax.enemies.Slime;
import com.tomandmax.enemies.Thorny;
import com.tomandmax.items.Chest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestEquals {
    private final Chest items = new Chest();
    private final Chest items2 = new Chest();
    private final Slime testSlime = new Slime(10, 10, 20, 3);
    private final Slime testSlime2 = new Slime(10, 10, 20, 4);
    private final Thorny testThorny = new Thorny(10, 10, 20, 3);
    private final Thorny testThorny2 = new Thorny(10, 10, 20, 3);
    private final Ghost testGhost = new Ghost(10, 10, 20, 3);

    @Test
    public void testChests(){
        //llenamos el primer cofre
        items.addItems("HealingPotion",2);
        items.addItems("FightingPotion",1);

        //llenamos el segundo cofre
        items2.addItems("HealingPotion",1);
        items2.addItems("FightingPotion",1);

        assertNotEquals(items, items2);
        assertNotEquals(items.hashCode(), items2.hashCode());

        assertEquals(items, items);
        assertEquals(items.hashCode(), items.hashCode());

    }

    @Test
    public void testEnemies(){
        assertEquals(testGhost, testGhost);
        assertNotEquals(testSlime, testThorny);

        testSlime.setNameIndex(1);
        testSlime2.setNameIndex(2);
        assertNotEquals(testSlime, testSlime2);

        testThorny.setNameIndex(1);
        testThorny2.setNameIndex(2);
        assertNotEquals(testThorny, testThorny2);
        assertNotEquals(testThorny.hashCode(), testThorny2.hashCode());
    }




}
