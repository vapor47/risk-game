import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author phill
 */
public class TestCard {
    
    public TestCard() {
    }
    
    /**
     * Test of setType method, of class Card.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        Type cardType = null;
        Card instance = new Card();
        instance.setType(cardType);
    }

    /**
     * Test of getType method, of class Card.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Card instance = new Card();
        Type expResult = null;
        Type result = instance.getType();
        assertEquals(expResult, result);       
    }

    /**
     * Test of setTerritory method, of class Card.
     */
    @Test
    public void testSetTerritory() {
        System.out.println("setTerritory");
        String TerritoryName = "";
        Card instance = new Card();
        instance.setTerritory(TerritoryName);
    }

    /**
     * Test of getTerritory method, of class Card.
     */
    @Test
    public void testGetTerritory() {
        System.out.println("getTerritory");
        Card instance = new Card();
        String expResult = "";
        String result = instance.getTerritory();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Card.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Card instance = new Card();
        instance.setType(Type.CAVALRY);
        String expResult = instance.getTerritory() + ", " + instance.getType().toString();
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
