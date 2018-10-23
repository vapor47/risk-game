import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DieTest {
    
    public DieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of roll method, of class Die.
     */
    @Test
    public void testRoll() {        
        System.out.println("roll");
        Die instance = new Die();       
        int result = instance.roll();
        switch(result) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;    
            default:
                fail();
                break;
        }            
    }

    /**
     * Test of setFaceValue method, of class Die.
     */
    @Test
    public void testSetFaceValue() {
        System.out.println("setFaceValue");
        int value = 0;
        Die instance = new Die();
        instance.setFaceValue(value);        
        assertEquals(instance.getFaceValue(), value);
    }

    /**
     * Test of getFaceValue method, of class Die.
     */
    @Test
    public void testGetFaceValue() {
        System.out.println("getFaceValue");
        Die instance = new Die();   //Constructor ssets face value to 1
        int expResult = 1;      
        int result = instance.getFaceValue();   
        assertEquals(expResult, result);                
    }

    /**
     * Test of toString method, of class Die.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Die instance = new Die();
        String expResult = "1";
        String result = instance.toString();
        assertEquals(expResult, result);        
    }
    
}
