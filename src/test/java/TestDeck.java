import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author phill
 */
public class TestDeck {
    
    public TestDeck() {
    }

    /**
     * Test of addCards method, of class Deck.
     */
    @Test
    public void testAddCards() {
        System.out.println("addCards");
        Card newCard = new Card();
        Deck instance = new Deck();
        instance.addCards(newCard);        
    }

    /**
     * Test of draw method, of class Deck.
     */
    @Test
    public void testDraw() {
        System.out.println("draw");
        Deck instance = new Deck();
        Card expResult = null;
        Card result = instance.draw();
        assertEquals(expResult, result);        
    }

    /**
     * Test of discard method, of class Deck.
     */
    @Test
    public void testDiscard() {
        System.out.println("discard");
        Card thrownCard = null;
        Deck instance = new Deck();
        instance.discard(thrownCard);        
    }

    /**
     * Test of printDeck method, of class Deck.
     */
    @Test
    public void testPrintDeck() {
        System.out.println("printDeck");
        Deck instance = new Deck();
        instance.printDeck();        
    }
    
}
