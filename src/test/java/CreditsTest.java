import java.io.ByteArrayInputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreditsTest {
    
    public CreditsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getCredits method, of class Credits.
     */
    @Test
    public void testGetCredits() {
        System.out.println("getCredits");
        Credits instance = new Credits();
        int expected = 0;
        int actual = instance.getCredits();
        
        assertEquals(expected, actual);        
    }

    /**
     * Test of addCredits method, of class Credits.
     */
    @Test
    public void testAddCredits() {
        System.out.println("addCredits");        
        int cred = 10;
        final int expected = cred;
        Credits instance = new Credits();
        instance.addCredits(cred);

        int actual = instance.getCredits();

        assertEquals(expected, actual);                
    }

    /**
     * Test of removeCredits method, of class Credits.
     */
    @Test
    public void testRemoveCredits() {
        System.out.println("removeCredits");
        int cred = 15;
        final int expected = 5;
        Credits instance = new Credits();
        instance.addCredits(20);
        instance.removeCredits(cred);
        
        int actual = instance.getCredits();
        
        assertEquals(expected, actual);
    }

    /**
     * Test of giveCredits method, of class Credits.
     */
    @Test
    public void testGiveCredits() {
        System.out.println("giveCredits");
        
        // Arrange
        Credits otherCredits = new Credits();
        Credits instance = new Credits();
        ByteArrayInputStream in;
        final int expected = 15; 
        
        // Act        
        in = new ByteArrayInputStream("15".getBytes()); // Give 15 credits away
        System.setIn(in);    
        instance.addCredits(20);
        instance.giveCredits(otherCredits);                  
        
        final int actual = otherCredits.getCredits();
        
        // Assert
        assertEquals(expected, actual);
    }
    
}
